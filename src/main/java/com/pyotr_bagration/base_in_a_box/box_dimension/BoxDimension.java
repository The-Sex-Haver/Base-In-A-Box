/*
 * Repository: Base In A Box
 * Module: BoxDimension
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Boilerplate related to the Box Dimension.
 */

package com.pyotr_bagration.base_in_a_box.box_dimension;

import com.pyotr_bagration.base_in_a_box.Common;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public final class BoxDimension {

    private static final Identifier ID = Common.CreateID("box_dimension");

    public static final RegistryKey<World> WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, ID);

    private BoxDimension() throws InstantiationException {
        throw new InstantiationException("Class \"BoxDimension\" cannot be instantiated.");
    }

    public static void Initialize() {
        Registry.register(Registries.CHUNK_GENERATOR, BoxDimensionChunkGenerator.ID, BoxDimensionChunkGenerator.CODEC);
    }

}
