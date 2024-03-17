/*
 * Repository: Base In A Box
 * Module: BaseInABox
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: The main initialization logic of the Base In A Box mod.
 */

package com.pyotr_bagration.base_in_a_box;

import com.pyotr_bagration.base_in_a_box.blocks.ImmutableEndStoneBricks;
import com.pyotr_bagration.base_in_a_box.blocks.ReturnButtonBlock;
import com.pyotr_bagration.base_in_a_box.blocks.base_box_block.BaseBoxBlock;
import com.pyotr_bagration.base_in_a_box.blocks.base_box_block.BaseBoxBlockEntity;
import com.pyotr_bagration.base_in_a_box.box_dimension.BoxDimension;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class BaseInABox implements ModInitializer {

    @Override
    public void onInitialize() {
        InitializeBlocks();
        BaseBoxBlockEntity.Initialize();
        BoxDimension.Initialize();
    }

    private void InitializeBlocks() {
        InitializeBaseBlocks();
        InitializeReturnButtonBlock();
        InitializeImmutableEndStoneBricks();
    }

    private void InitializeBaseBlocks() {
        for (int i = 0; i < BaseBoxBlock.INSTANCES.length; i++) {
            Registry.register(Registries.BLOCK, BaseBoxBlock.INSTANCES[i].ID, BaseBoxBlock.INSTANCES[i]);
            Registry.register(Registries.ITEM, BaseBoxBlock.INSTANCES[i].ID, BaseBoxBlock.ITEMS[i]);
            Registry.register(Registries.SCREEN_HANDLER, BaseBoxBlock.INSTANCES[i].ID, BaseBoxBlock.INSTANCES[i].SCREEN_HANDLER_TYPE);
        }
    }

    private void InitializeReturnButtonBlock() {
        Registry.register(Registries.BLOCK, ReturnButtonBlock.ID, ReturnButtonBlock.INSTANCE);
    }

    private void InitializeImmutableEndStoneBricks() {
        Registry.register(Registries.BLOCK, ImmutableEndStoneBricks.ID, ImmutableEndStoneBricks.INSTANCE);
    }

}
