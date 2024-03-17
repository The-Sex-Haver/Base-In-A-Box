/*
 * Repository: Base In A Box
 * Module: BaseBoxBlockEntityRenderer
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Boilerplate to ensure that the base box blocks render correctly.
 */

package com.pyotr_bagration.base_in_a_box.blocks.base_box_block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class BaseBoxBlockEntityRenderer extends ShulkerBoxBlockEntityRenderer {

    public BaseBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

}
