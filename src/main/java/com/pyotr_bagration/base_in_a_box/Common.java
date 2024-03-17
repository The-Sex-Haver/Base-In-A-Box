/*
 * Repository: Base In A Box
 * Module: Common
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Commonly used functions that don't fit anywhere else.
 */

package com.pyotr_bagration.base_in_a_box;

import com.pyotr_bagration.base_in_a_box.blocks.base_box_block.BaseBoxBlock;
import com.pyotr_bagration.base_in_a_box.blocks.base_box_block.BaseBoxBlockEntity;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Common {

    public static final String ROOT = "base_in_a_box";

    private static final Logger LOGGER = LoggerFactory.getLogger(ROOT);

    private Common() throws InstantiationException {
        throw new InstantiationException("Class \"Common\" cannot be instantiated.");
    }

    public static Identifier CreateID(String path) {
        return new Identifier(ROOT, path);
    }

    public static void Log(String message) {
        LOGGER.info(message);
    }

    public static void EnderParticles(World world, BlockPos pos, Random random) {
        for (int i = 0; i < 5; ++i) {
            int j = random.nextInt(2) * 2 - 1;
            int k = random.nextInt(2) * 2 - 1;
            double d = (double) pos.getX() + 0.5 + 0.25 * (double) j;
            double e = (float) pos.getY() + random.nextFloat();
            double f = (double) pos.getZ() + 0.5 + 0.25 * (double) k;
            double g = random.nextFloat() * (float) j;
            double h = ((double) random.nextFloat() - 0.5) * 0.125;
            double l = random.nextFloat() * (float) k;
            world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, l);
        }
    }

    public static void Detonate(World world, LivingEntity user) {
        Direction facing = user.getHorizontalFacing();
        double x = user.getX() + facing.getOffsetX();
        double y = user.getY();
        double z = user.getZ() + facing.getOffsetZ();
        world.createExplosion(null, x, y, z, 5.0f, true, World.ExplosionSourceType.TNT);
    }

    @NotNull
    public static ActionResult BreakBaseBlock(World world, BlockPos pos, BaseBoxBlockEntity entity) {
        ItemStack itemStack = new ItemStack(BaseBoxBlock.GetItem(entity.DYE_COLOR));
        if (entity.hasCustomName()) {
            itemStack.setCustomName(entity.getCustomName());
        }
        Vec3d center = pos.toCenterPos();
        ItemEntity itemEntity = new ItemEntity(world, center.x, center.y, center.z, itemStack);
        itemEntity.setToDefaultPickupDelay();
        world.breakBlock(pos, false);
        world.spawnEntity(itemEntity);
        return ActionResult.SUCCESS;
    }

}
