/*
 * Repository: Base In A Box
 * Module: ReturnButtonBlock
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: A button that serves the sole purpose of teleporting the player out of the Box Dimension.
 */

package com.pyotr_bagration.base_in_a_box.blocks;

import com.pyotr_bagration.base_in_a_box.Common;
import com.pyotr_bagration.base_in_a_box.box_dimension.BoxDimensionState;
import com.pyotr_bagration.base_in_a_box.box_dimension.ReturnInfo;

import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Objects;

public final class ReturnButtonBlock extends ButtonBlock {

    public static final Identifier ID = Common.CreateID("return_button_block");

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 4.0, 4.0, 3.0, 12.0, 12.0);

    private static final VoxelShape PRESSED_SHAPE = Block.createCuboidShape(0, 4.0, 4.0, 1.0, 12.0, 12.0);

    private static final int PRESS_TICKS = 20;

    private static final AbstractBlock.Settings SETTINGS = AbstractBlock.Settings.create()
        .strength(-1.0f, 3600000.0f)
        .pistonBehavior(PistonBehavior.BLOCK)
        .noBlockBreakParticles()
        .mapColor(MapColor.GREEN)
        .instrument(Instrument.BASEDRUM)
        .sounds(BlockSoundGroup.GLASS)
        .luminance(state -> 1)
        .dropsNothing();

    public static final ReturnButtonBlock INSTANCE = new ReturnButtonBlock(BlockSetType.STONE, PRESS_TICKS, SETTINGS);

    private TeleportOut teleport = null;

    private ReturnButtonBlock(BlockSetType blockSetType, int pressTicks, Settings settings) {
        super(blockSetType, pressTicks, settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(POWERED) ? PRESSED_SHAPE : SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (!state.get(POWERED)) {

            player.playSound(SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.BLOCKS, 1, 1);

            teleport = () -> {
                MinecraftServer server = world.getServer();
                BoxDimensionState boxDimensionState = BoxDimensionState.Get(Objects.requireNonNull(server));
                ReturnInfo info = boxDimensionState.GetStoredReturnInfo(player.getUuid());
                ServerWorld returnWorld = Objects.requireNonNull(server).getWorld(info.GetWorld());
                player.teleport(returnWorld, info.GetX(), info.GetY(), info.GetZ(), null, info.GetYaw() + 180, 0);
            };

        }

        return super.onUse(state, world, pos, player, hand, hit);

    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Common.EnderParticles(world, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        if (teleport != null) {
            teleport.Execute();
            teleport = null;
        }
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) { return 0; }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return false;
    }

    @FunctionalInterface
    private interface TeleportOut {
        void Execute();

    }

}
