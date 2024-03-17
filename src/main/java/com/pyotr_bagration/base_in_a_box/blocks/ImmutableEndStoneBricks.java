/*
 * Repository: Base In A Box
 * Module: ImmutableEndStoneBricks
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: This block makes up the walls, floors, and ceilings of the Box Dimension. It is indestructible and
 * unmovable.
 */

package com.pyotr_bagration.base_in_a_box.blocks;

import com.pyotr_bagration.base_in_a_box.Common;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.Identifier;

public final class ImmutableEndStoneBricks extends Block {

    public static final Identifier ID = Common.CreateID("immutable_end_stone_bricks");

    private static final Settings SETTINGS = AbstractBlock.Settings.create()
        .strength(-1.0F, 3600000.0F)
        .pistonBehavior(PistonBehavior.BLOCK)
        .noBlockBreakParticles()
        .mapColor(MapColor.PALE_YELLOW)
        .instrument(Instrument.BASEDRUM)
        .dropsNothing();

    public static final ImmutableEndStoneBricks INSTANCE = new ImmutableEndStoneBricks(SETTINGS);

    private ImmutableEndStoneBricks(Settings settings) {
        super(settings);
    }

}
