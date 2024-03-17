/*
 * Repository: Base In A Box
 * Module: BaseInABoxClient
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Boilerplate to ensure that the base box blocks render correctly.
 */

package com.pyotr_bagration.base_in_a_box;

import com.pyotr_bagration.base_in_a_box.blocks.base_box_block.BaseBoxBlockEntity;
import com.pyotr_bagration.base_in_a_box.blocks.base_box_block.BaseBoxBlockEntityRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public final class BaseInABoxClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(BaseBoxBlockEntity.BLOCK_ENTITY_TYPE, BaseBoxBlockEntityRenderer::new);
    }

}
