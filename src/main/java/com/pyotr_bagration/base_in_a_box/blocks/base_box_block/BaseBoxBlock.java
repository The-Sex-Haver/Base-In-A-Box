/*
 * Repository: Base In A Box
 * Module: BaseBoxBlock
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Defines the various base boxes and their properties.
 */

package com.pyotr_bagration.base_in_a_box.blocks.base_box_block;

import com.pyotr_bagration.base_in_a_box.Common;
import com.pyotr_bagration.base_in_a_box.box_dimension.BoxDimension;

import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class BaseBoxBlock extends ShulkerBoxBlock {

    private static final ContextPredicate BASE_BOX_SUFFOCATES_PREDICATE = (state, world, pos) -> {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BaseBoxBlockEntity baseBoxBlockEntity) {
            return baseBoxBlockEntity.suffocates();
        }
        return true;
    };

    private static final Item.Settings ITEM_SETTINGS = new Item.Settings()
        .fireproof()
        .maxCount(1)
        .rarity(Rarity.RARE);

    public static final BaseBoxBlock BASE_BOX_BLOCK_INSTANCE = Instantiate("base_box_block", null, MapColor.PURPLE);

    public static final Item BASE_BOX_BLOCK_ITEM = CreateItem(BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock WHITE_BASE_BOX_BLOCK_INSTANCE = Instantiate("white_base_box_block", DyeColor.WHITE, MapColor.WHITE);

    public static final Item WHITE_BASE_BOX_BLOCK_ITEM = CreateItem(WHITE_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock ORANGE_BASE_BOX_BLOCK_INSTANCE = Instantiate("orange_base_box_block", DyeColor.ORANGE, MapColor.ORANGE);

    public static final Item ORANGE_BASE_BOX_BLOCK_ITEM = CreateItem(ORANGE_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock MAGENTA_BASE_BOX_BLOCK_INSTANCE = Instantiate("magenta_base_box_block", DyeColor.MAGENTA, MapColor.MAGENTA);

    public static final Item MAGENTA_BASE_BOX_BLOCK_ITEM = CreateItem(MAGENTA_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock LIGHT_BLUE_BASE_BOX_BLOCK_INSTANCE = Instantiate("light_blue_base_box_block", DyeColor.LIGHT_BLUE, MapColor.LIGHT_BLUE);

    public static final Item LIGHT_BLUE_BASE_BOX_BLOCK_ITEM = CreateItem(LIGHT_BLUE_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock YELLOW_BASE_BOX_BLOCK_INSTANCE = Instantiate("yellow_base_box_block", DyeColor.YELLOW, MapColor.YELLOW);

    public static final Item YELLOW_BASE_BOX_BLOCK_ITEM = CreateItem(YELLOW_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock LIME_BASE_BOX_BLOCK_INSTANCE = Instantiate("lime_base_box_block", DyeColor.LIME, MapColor.LIME);

    public static final Item LIME_BASE_BOX_BLOCK_ITEM = CreateItem(LIME_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock PINK_BASE_BOX_BLOCK_INSTANCE = Instantiate("pink_base_box_block", DyeColor.PINK, MapColor.PINK);

    public static final Item PINK_BASE_BOX_BLOCK_ITEM = CreateItem(PINK_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock GRAY_BASE_BOX_BLOCK_INSTANCE = Instantiate("gray_base_box_block", DyeColor.GRAY, MapColor.GRAY);

    public static final Item GRAY_BASE_BOX_BLOCK_ITEM = CreateItem(GRAY_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock LIGHT_GRAY_BASE_BOX_BLOCK_INSTANCE = Instantiate("light_gray_base_box_block", DyeColor.LIGHT_GRAY, MapColor.LIGHT_GRAY);

    public static final Item LIGHT_GRAY_BASE_BOX_BLOCK_ITEM = CreateItem(LIGHT_GRAY_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock CYAN_BASE_BOX_BLOCK_INSTANCE = Instantiate("cyan_base_box_block", DyeColor.CYAN, MapColor.CYAN);

    public static final Item CYAN_BASE_BOX_BLOCK_ITEM = CreateItem(CYAN_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock PURPLE_BASE_BOX_BLOCK_INSTANCE = Instantiate("purple_base_box_block", DyeColor.PURPLE, MapColor.TERRACOTTA_PURPLE);

    public static final Item PURPLE_BASE_BOX_BLOCK_ITEM = CreateItem(PURPLE_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock BLUE_BASE_BOX_BLOCK_INSTANCE = Instantiate("blue_base_box_block", DyeColor.BLUE, MapColor.BLUE);

    public static final Item BLUE_BASE_BOX_BLOCK_ITEM = CreateItem(BLUE_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock BROWN_BASE_BOX_BLOCK_INSTANCE = Instantiate("brown_base_box_block", DyeColor.BROWN, MapColor.BROWN);

    public static final Item BROWN_BASE_BOX_BLOCK_ITEM = CreateItem(BROWN_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock GREEN_BASE_BOX_BLOCK_INSTANCE = Instantiate("green_base_box_block", DyeColor.GREEN, MapColor.GREEN);

    public static final Item GREEN_BASE_BOX_BLOCK_ITEM = CreateItem(GREEN_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock RED_BASE_BOX_BLOCK_INSTANCE = Instantiate("red_base_box_block", DyeColor.RED, MapColor.RED);

    public static final Item RED_BASE_BOX_BLOCK_ITEM = CreateItem(RED_BASE_BOX_BLOCK_INSTANCE);

    public static final BaseBoxBlock BLACK_BASE_BOX_BLOCK_INSTANCE = Instantiate("black_base_box_block", DyeColor.BLACK, MapColor.BLACK);

    public static final Item BLACK_BASE_BOX_BLOCK_ITEM = CreateItem(BLACK_BASE_BOX_BLOCK_INSTANCE);

    public static final Item[] ITEMS = {
        BASE_BOX_BLOCK_ITEM,
        WHITE_BASE_BOX_BLOCK_ITEM,
        ORANGE_BASE_BOX_BLOCK_ITEM,
        MAGENTA_BASE_BOX_BLOCK_ITEM,
        LIGHT_BLUE_BASE_BOX_BLOCK_ITEM,
        YELLOW_BASE_BOX_BLOCK_ITEM,
        LIME_BASE_BOX_BLOCK_ITEM,
        PINK_BASE_BOX_BLOCK_ITEM,
        GRAY_BASE_BOX_BLOCK_ITEM,
        LIGHT_GRAY_BASE_BOX_BLOCK_ITEM,
        CYAN_BASE_BOX_BLOCK_ITEM,
        PURPLE_BASE_BOX_BLOCK_ITEM,
        BLUE_BASE_BOX_BLOCK_ITEM,
        BROWN_BASE_BOX_BLOCK_ITEM,
        GREEN_BASE_BOX_BLOCK_ITEM,
        RED_BASE_BOX_BLOCK_ITEM,
        BLACK_BASE_BOX_BLOCK_ITEM
    };

    public static final BaseBoxBlock[] INSTANCES = {
        BASE_BOX_BLOCK_INSTANCE,
        WHITE_BASE_BOX_BLOCK_INSTANCE,
        ORANGE_BASE_BOX_BLOCK_INSTANCE,
        MAGENTA_BASE_BOX_BLOCK_INSTANCE,
        LIGHT_BLUE_BASE_BOX_BLOCK_INSTANCE,
        YELLOW_BASE_BOX_BLOCK_INSTANCE,
        LIME_BASE_BOX_BLOCK_INSTANCE,
        PINK_BASE_BOX_BLOCK_INSTANCE,
        GRAY_BASE_BOX_BLOCK_INSTANCE,
        LIGHT_GRAY_BASE_BOX_BLOCK_INSTANCE,
        CYAN_BASE_BOX_BLOCK_INSTANCE,
        PURPLE_BASE_BOX_BLOCK_INSTANCE,
        BLUE_BASE_BOX_BLOCK_INSTANCE,
        BROWN_BASE_BOX_BLOCK_INSTANCE,
        GREEN_BASE_BOX_BLOCK_INSTANCE,
        RED_BASE_BOX_BLOCK_INSTANCE,
        BLACK_BASE_BOX_BLOCK_INSTANCE
    };

    private static final HashMap<DyeColor, BaseBoxBlock> INSTANCE_COLOR_MAP = new HashMap<>() {{
        put(null, BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.WHITE, WHITE_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.ORANGE, ORANGE_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.MAGENTA, MAGENTA_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.LIGHT_BLUE, LIGHT_BLUE_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.YELLOW, YELLOW_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.LIME, LIME_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.PINK, PINK_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.GRAY, GRAY_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.LIGHT_GRAY, LIGHT_GRAY_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.CYAN, CYAN_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.PURPLE, PURPLE_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.BLUE, BLUE_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.BROWN, BROWN_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.GREEN, GREEN_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.RED, RED_BASE_BOX_BLOCK_INSTANCE);
        put(DyeColor.BLACK, BLACK_BASE_BOX_BLOCK_INSTANCE);
    }};

    private static final HashMap<DyeColor, Item> ITEM_COLOR_MAP = new HashMap<>() {{
        put(null, BASE_BOX_BLOCK_ITEM);
        put(DyeColor.WHITE, WHITE_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.ORANGE, ORANGE_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.MAGENTA, MAGENTA_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.LIGHT_BLUE, LIGHT_BLUE_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.YELLOW, YELLOW_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.LIME, LIME_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.PINK, PINK_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.GRAY, GRAY_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.LIGHT_GRAY, LIGHT_GRAY_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.CYAN, CYAN_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.PURPLE, PURPLE_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.BLUE, BLUE_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.BROWN, BROWN_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.GREEN, GREEN_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.RED, RED_BASE_BOX_BLOCK_ITEM);
        put(DyeColor.BLACK, BLACK_BASE_BOX_BLOCK_ITEM);
    }};

    public final ScreenHandlerType<BaseBoxBlockScreenHandler> SCREEN_HANDLER_TYPE
        = new ScreenHandlerType<>(BaseBoxBlockScreenHandler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES);

    public final Identifier ID;

    public final DyeColor DYE_COLOR;

    private BaseBoxBlock(String id, DyeColor color, Settings settings) {
        super(color, settings);
        DYE_COLOR = color;
        ID = Common.CreateID(id);
    }

    @Nullable
    @Override
    public DyeColor getColor() {
        return DYE_COLOR;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BaseBoxBlockEntity(DYE_COLOR, pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return BaseBoxBlock.validateTicker(type, BaseBoxBlockEntity.BLOCK_ENTITY_TYPE, BaseBoxBlockEntity::Tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (player.isSpectator() || world.isClient) {
            return ActionResult.CONSUME;
        }

        if (world.getBlockEntity(pos) instanceof BaseBoxBlockEntity entity) {
            if (entity.GetOwnerUUID().equals(player.getUuid())) {
                if (world.getRegistryKey() == BoxDimension.WORLD_KEY) {
                    return Common.BreakBaseBlock(world, pos, entity);
                } else if (CanOpen(state, world, pos, entity) && entity.CanQueueTeleport()) {
                    player.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 1, 1);
                    entity.QueueTeleport(player);
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.CONSUME;
                }
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return ActionResult.PASS;
        }

    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Common.EnderParticles(world, pos, random);
    }

    public static BaseBoxBlock GetInstance(DyeColor color) {
        return INSTANCE_COLOR_MAP.get(color);
    }

    public static Item GetItem(DyeColor color) {
        return ITEM_COLOR_MAP.get(color);
    }

    private static Settings CreateBlockSettings(MapColor mapColor) {
        return Settings.create()
            .mapColor(mapColor)
            .solid()
            .strength(-1.0F, 3600000.0F)
            .pistonBehavior(PistonBehavior.BLOCK)
            .noBlockBreakParticles()
            .dynamicBounds()
            .nonOpaque()
            .suffocates(BASE_BOX_SUFFOCATES_PREDICATE)
            .blockVision(BASE_BOX_SUFFOCATES_PREDICATE);
    }

    private static BaseBoxBlock Instantiate(String id, DyeColor dyeColor, MapColor mapColor) {
        return new BaseBoxBlock(id, dyeColor, CreateBlockSettings(mapColor));
    }

    private static BlockItem CreateItem(BaseBoxBlock block) {
        return new BlockItem(block, ITEM_SETTINGS);
    }

    private static boolean CanOpen(BlockState state, World world, BlockPos pos, BaseBoxBlockEntity entity) {
        if (entity.getAnimationStage() != ShulkerBoxBlockEntity.AnimationStage.CLOSED) {
            return true;
        }
        Box box = ShulkerEntity.calculateBoundingBox(state.get(FACING), 0.0f, 0.5f).offset(pos).contract(1.0E-6);
        return world.isSpaceEmpty(box);
    }

}
