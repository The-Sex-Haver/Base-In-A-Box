/*
/*
 * Repository: Base In A Box
 * Module: BaseBoxBlockEntity
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Handles the teleportation of players to the box dimension and the storage of the coordinates players
 * will be returned to upon exiting.
 *
 */

package com.pyotr_bagration.base_in_a_box.blocks.base_box_block;

import com.pyotr_bagration.base_in_a_box.Common;
import com.pyotr_bagration.base_in_a_box.box_dimension.BoxDimension;
import com.pyotr_bagration.base_in_a_box.box_dimension.BoxDimensionState;
import com.pyotr_bagration.base_in_a_box.box_dimension.ReturnInfo;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public final class BaseBoxBlockEntity extends ShulkerBoxBlockEntity {

    public static final BlockEntityType<BaseBoxBlockEntity> BLOCK_ENTITY_TYPE
        = FabricBlockEntityTypeBuilder.create(BaseBoxBlockEntity::new, BaseBoxBlock.INSTANCES).build();

    private static final Identifier ID = Common.CreateID("base_block_entity");

    private static final String OWNER_UUID_KEY = "OWNER_UUID";

    private static final float X_MOD = 2.0F;

    private static final float Y_MOD = 1.0F;

    private static final float Z_MOD = 8.5F;

    private static final float YAW = -90F;

    private static final float PITCH = 0.0F;

    private UUID ownerUUID = null;

    private TeleportToBase teleport = null;

    public final DyeColor DYE_COLOR;

    public BaseBoxBlockEntity(@Nullable DyeColor color, BlockPos pos, BlockState state) {
        super(color, pos, state);
        DYE_COLOR = color;
        this.setStack(0, ItemStack.EMPTY);
    }

    private BaseBoxBlockEntity(BlockPos pos, BlockState state) {
        this(null, pos, state);
    }

    public static void Initialize() {

        Registry.register(Registries.BLOCK_ENTITY_TYPE, ID, BLOCK_ENTITY_TYPE);

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {

            if (world.getBlockEntity(pos) instanceof BaseBoxBlockEntity entity) {
                if (player.isSpectator() || world.isClient) {
                    return ActionResult.CONSUME;
                } else if (entity.GetOwnerUUID().equals(player.getUuid())) {
                    return Common.BreakBaseBlock(world, pos, entity);
                } else {
                    return ActionResult.CONSUME;
                }
            } else {
                return ActionResult.PASS;
            }
        });

    }

    public static void Tick(World world, BlockPos pos, BlockState state, BaseBoxBlockEntity blockEntity) {

        ShulkerBoxBlockEntity.tick(world, pos, state, blockEntity);

        if (blockEntity.getAnimationStage() == AnimationStage.OPENED) {
            if (blockEntity.teleport != null) {
                blockEntity.teleport.Execute();
                blockEntity.teleport = null;
                world.addSyncedBlockEvent(pos, state.getBlock(), 1, 0); //fuck you
            }
        }

    }

    public boolean CanQueueTeleport() {
        return teleport == null;
    }

    public void QueueTeleport(PlayerEntity player) {

        player.openHandledScreen(this);

        teleport = () -> {
            MinecraftServer server = Objects.requireNonNull(world).getServer();
            BoxDimensionState state = BoxDimensionState.Get(Objects.requireNonNull(server));
            ReturnInfo info = new ReturnInfo(player.getX(), player.getY(), player.getZ(), player.getYaw(), world.getRegistryKey());
            state.SetStoredReturnInfo(ownerUUID, info);
            ServerWorld boxDimension = Objects.requireNonNull(server).getWorld(BoxDimension.WORLD_KEY);
            ChunkPos pos = state.GetBaseMap(player.getUuid()).GetByColor(DYE_COLOR);
            player.teleport(boxDimension, pos.getStartX() + X_MOD, Y_MOD, pos.getStartZ() + Z_MOD, null, YAW, PITCH);
        };

    }

    public void SetOwnerUUID(UUID uuid) {
        ownerUUID = uuid;
        markDirty();
    }

    public UUID GetOwnerUUID() {
        return ownerUUID;
    }

    @Override
    public BlockEntityType<?> getType() {
        return BLOCK_ENTITY_TYPE;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        ownerUUID = nbt.getUuid(OWNER_UUID_KEY);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putUuid(OWNER_UUID_KEY, ownerUUID);
        super.writeNbt(nbt);
    }

    @Override
    public @NotNull Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new BaseBoxBlockScreenHandler(DYE_COLOR, syncId, playerInventory, this);
    }

    @FunctionalInterface
    private interface TeleportToBase {
        void Execute();

    }

}
