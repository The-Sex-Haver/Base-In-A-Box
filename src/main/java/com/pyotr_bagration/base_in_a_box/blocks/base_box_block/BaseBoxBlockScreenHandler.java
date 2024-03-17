/*
 * Repository: Base In A Box
 * Module: BaseBoxBlockScreenHandler
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Boilerplate. There is no screen, but having this here ensures other things work correctly. Yes, it's a
 * bit dirty.
 */

package com.pyotr_bagration.base_in_a_box.blocks.base_box_block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.DyeColor;

public final class BaseBoxBlockScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public BaseBoxBlockScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(null, syncId, playerInventory, new SimpleInventory(27));
    }

    public BaseBoxBlockScreenHandler(DyeColor color, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(BaseBoxBlock.GetInstance(color).SCREEN_HANDLER_TYPE, syncId);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

}
