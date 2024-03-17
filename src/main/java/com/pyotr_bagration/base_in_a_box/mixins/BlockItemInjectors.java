/*
 * Repository: Base In A Box
 * Module: BlockItemInjectors
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Handles the assignment of newly placed Base Boxes to the players that placed them. Other modules use
 * this information to determine the results of interactions with placed Base Boxes.
 */

package com.pyotr_bagration.base_in_a_box.mixins;

import com.pyotr_bagration.base_in_a_box.blocks.base_box_block.BaseBoxBlockEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(BlockItem.class)
public final class BlockItemInjectors {

    private BlockItemInjectors() throws InstantiationException {
        throw new InstantiationException("Class \"BlockItemInjectors\" cannot be instantiated.");
    }

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At("TAIL"))
    private void SetBaseOwner(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (cir.getReturnValue() != ActionResult.FAIL) {
            World world = context.getWorld();
            if (!world.isClient && world.getBlockEntity(context.getBlockPos()) instanceof BaseBoxBlockEntity entity) {
                PlayerEntity player = Objects.requireNonNull(context.getPlayer());
                entity.SetOwnerUUID(player.getUuid());
            }
        }
    }

}
