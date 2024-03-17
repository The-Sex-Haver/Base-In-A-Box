/*
 * Repository: Base In A Box
 * Module: ChorusFruitItemMixins
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Mixins that enforce the inadvisability of attempts to clip through bounds within the Box
 * Dimension through the use of Chorus Fruit.
 */

package com.pyotr_bagration.base_in_a_box.mixins;

import com.pyotr_bagration.base_in_a_box.Common;
import com.pyotr_bagration.base_in_a_box.box_dimension.BoxDimension;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ChorusFruitItem.class)
public final class ChorusFruitItemMixins {

    private ChorusFruitItemMixins() throws InstantiationException {
        throw new InstantiationException("Class \"ChorusFruitItemMixins\" cannot be instantiated.");
    }

    @Overwrite
    @SuppressWarnings("OverwriteAuthorRequired")
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = user.eatFood(world, stack);
        if (!world.isClient) {
            if (world.getRegistryKey() != BoxDimension.WORLD_KEY) {
                for (int i = 0; i < 16; ++i) {
                    SoundCategory soundCategory;
                    SoundEvent soundEvent;
                    double d = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                    double e = MathHelper.clamp(user.getY() + (double) (user.getRandom().nextInt(16) - 8), world.getBottomY(), (world.getBottomY() + ((ServerWorld) world).getLogicalHeight() - 1));
                    double f = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                    if (user.hasVehicle()) {
                        user.stopRiding();
                    }
                    Vec3d vec3d = user.getPos();
                    if (!user.teleport(d, e, f, true)) continue;
                    world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                    if (user instanceof FoxEntity) {
                        soundEvent = SoundEvents.ENTITY_FOX_TELEPORT;
                        soundCategory = SoundCategory.NEUTRAL;
                    } else {
                        soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                        soundCategory = SoundCategory.PLAYERS;
                    }
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), soundEvent, soundCategory);
                    user.onLanding();
                    break;
                }
            } else {
                Common.Detonate(world, user);
            }
            if (user instanceof PlayerEntity playerEntity) {
                playerEntity.getItemCooldownManager().set((ChorusFruitItem) (Object) this, 20);
            }
        }
        return itemStack;
    }

}
