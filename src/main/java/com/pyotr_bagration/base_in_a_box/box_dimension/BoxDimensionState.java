/*
 * Repository: Base In A Box
 * Module: BoxDimensionState
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: Handles the storage of everything related to the chunks assigned to each player in the Box Dimension, as
 * well as the coordinates players will be returned to upon exiting.
 */

package com.pyotr_bagration.base_in_a_box.box_dimension;

import com.pyotr_bagration.base_in_a_box.Common;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import org.apache.commons.lang3.SerializationUtils;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class BoxDimensionState extends PersistentState {

    private static final String ASSIGNED_BASES_KEY = "ASSIGNED_BASES";

    private static final String BASE_MAPS_KEY = "BASE_MAPS";

    private static final String RETURN_INFO_KEY = "RETURN_INFO";

    private static final Type<BoxDimensionState> TYPE = new Type<>(BoxDimensionState::new, BoxDimensionState::Create, null);

    private int assignedBases = 0;

    private HashMap<UUID, BaseMap> baseMaps = new HashMap<>();

    private HashMap<UUID, ReturnInfo> storedReturnInfo = new HashMap<>();

    private BoxDimensionState() { }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt(ASSIGNED_BASES_KEY, assignedBases);
        nbt.putByteArray(BASE_MAPS_KEY, SerializationUtils.serialize(baseMaps));
        nbt.putByteArray(RETURN_INFO_KEY, SerializationUtils.serialize(storedReturnInfo));
        return nbt;
    }

    public BaseMap GetBaseMap(UUID uuid) {
        if (!baseMaps.containsKey(uuid)) {
            baseMaps.put(uuid, new BaseMap(this));
            markDirty();
        }
        return baseMaps.get(uuid);
    }

    public ReturnInfo GetStoredReturnInfo(UUID uuid) {
        return storedReturnInfo.get(uuid);
    }

    public void SetStoredReturnInfo(UUID uuid, ReturnInfo info) {
        storedReturnInfo.remove(uuid);
        storedReturnInfo.put(uuid, info);
        markDirty();
    }

    public ChunkPos Lateralus() {
        return _Lateralus(++assignedBases);
    }

    public static BoxDimensionState Get(MinecraftServer server) {
        PersistentStateManager stateManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();
        BoxDimensionState state = stateManager.getOrCreate(TYPE, Common.ROOT);
        state.markDirty();
        return state;
    }

    private static BoxDimensionState Create(NbtCompound nbt) {
        BoxDimensionState state = new BoxDimensionState();
        state.assignedBases = nbt.getInt(ASSIGNED_BASES_KEY);
        state.baseMaps = SerializationUtils.deserialize(nbt.getByteArray(BASE_MAPS_KEY));
        state.storedReturnInfo = SerializationUtils.deserialize(nbt.getByteArray(RETURN_INFO_KEY));
        state.markDirty();
        return state;
    }

    private static ChunkPos _Lateralus(int n) {
        if (n == 1) {
            return new ChunkPos(0, 0);
        } else {
            ChunkPos c = _Lateralus(n - 1); //holy jesus...
            double d = Math.floor(Math.sqrt(4 * n - 7)) * Math.PI / 2; //what is that?
            return new ChunkPos((int) (c.x + Math.sin(d)), (int) (c.z + -Math.cos(d))); //what the FUCK is that?
        }
    }

}
