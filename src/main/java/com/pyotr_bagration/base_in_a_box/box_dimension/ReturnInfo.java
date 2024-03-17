/*
 * Repository: Base In A Box
 * Module: ReturnInfo
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: The meat and potatoes.
 */

package com.pyotr_bagration.base_in_a_box.box_dimension;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import org.apache.commons.lang3.NotImplementedException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class ReturnInfo implements Serializable {

    private static final String OVERWORLD = "OVERWORLD";

    private static final String NETHER = "NETHER";

    private static final String END = "END";

    private static final Map<String, RegistryKey<World>> WORLDS = new HashMap<>() {{
        put(OVERWORLD, World.OVERWORLD);
        put(NETHER, World.NETHER);
        put(END, World.END);
    }};

    private final double x;

    private final double y;

    private final double z;

    private final float yaw;

    private final String world;

    public ReturnInfo(double x, double y, double z, float yaw, RegistryKey<World> worldKey) {

        this.x = x;
        this.y = y;
        this.z = z;

        this.yaw = yaw;

        if (worldKey == World.OVERWORLD) {
            this.world = OVERWORLD;
        } else if (worldKey == World.NETHER) {
            this.world = NETHER;
        } else if (worldKey == World.END) {
            this.world = END;
        } else {
            throw new NotImplementedException();
        }

    }

    public double GetX() {
        return x;
    }

    public double GetY() {
        return y;
    }

    public double GetZ() {
        return z;
    }

    public float GetYaw() {
        return yaw;
    }

    public RegistryKey<World> GetWorld() {
        return WORLDS.get(world);
    }

}
