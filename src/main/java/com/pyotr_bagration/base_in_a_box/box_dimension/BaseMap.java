/*
 * Repository: Base In A Box
 * Module: BaseMap
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Provides a way to store and retrieve the coordinates of a players assigned chunks within the Box
 * Dimension.
 */

package com.pyotr_bagration.base_in_a_box.box_dimension;

import net.minecraft.util.DyeColor;
import net.minecraft.util.math.ChunkPos;

import java.io.Serializable;
import java.util.HashMap;

public final class BaseMap implements Serializable {

    private final HashMap<Integer, Long> map = new HashMap<>();

    public BaseMap(BoxDimensionState state) {
        map.put(Integer.MAX_VALUE, state.Lateralus().toLong());
        for (DyeColor color : DyeColor.values()) {
            map.put(color.getId(), state.Lateralus().toLong());
        }
    }

    public ChunkPos GetByColor(DyeColor color) {
        return new ChunkPos(map.get(color == null ? Integer.MAX_VALUE : color.getId()));
    }

}
