/*
 * Repository: Base In A Box
 * Module: BoxDimensionChunkGenerator
 * Author: The Sex Haver
 * Date: 3/16/2024
 * License: GPL V3
 * Description: A simple chunk generator. Each chunk consists of a square space of unbreakable bricks and a button to
 * return the player to wherever he or she came from.
 */

package com.pyotr_bagration.base_in_a_box.box_dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import com.pyotr_bagration.base_in_a_box.Common;
import com.pyotr_bagration.base_in_a_box.blocks.ImmutableEndStoneBricks;
import com.pyotr_bagration.base_in_a_box.blocks.ReturnButtonBlock;

import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class BoxDimensionChunkGenerator extends ChunkGenerator {

    public static final Codec<BoxDimensionChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
        instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource))
            .apply(instance, instance.stable(BoxDimensionChunkGenerator::of))
    );

    public static final Identifier ID = Common.CreateID("base_generator_type");

    private static final BlockState SHELL_BLOCK_STATE = ImmutableEndStoneBricks.INSTANCE.getDefaultState();

    private static final BlockState RETURN_BUTTON_BLOCK_STATE = ReturnButtonBlock.INSTANCE.getDefaultState();

    private static final int BASE_WIDTH = 16;

    private static final BlockPos RETURN_BUTTON_BLOCK_POS = new BlockPos(1, 2, 8);

    public BoxDimensionChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    private static BoxDimensionChunkGenerator of(BiomeSource biomeSource) {
        return new BoxDimensionChunkGenerator(biomeSource);
    }

    private static void PlaceShellBlock(Chunk chunk, BlockPos pos) {
        chunk.setBlockState(pos, SHELL_BLOCK_STATE, false);
    }

    private static void PlaceReturnButtonBlock(Chunk chunk) {
        chunk.setBlockState(RETURN_BUTTON_BLOCK_POS, RETURN_BUTTON_BLOCK_STATE, false);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) { }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) { }

    @Override
    public void populateEntities(ChunkRegion region) { }

    @Override
    public int getWorldHeight() {
        return 15;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {

        for (int i = 0; i <= BASE_WIDTH; i++) {

            //walls
            for (int j = 0; j <= BASE_WIDTH; j++) {

                //-z wall
                PlaceShellBlock(chunk, new BlockPos(i, j, 0));

                //+z wall
                PlaceShellBlock(chunk, new BlockPos(i, j, BASE_WIDTH));

                //-x wall
                PlaceShellBlock(chunk, new BlockPos(0, j, i));

                //+x wall
                PlaceShellBlock(chunk, new BlockPos(BASE_WIDTH, j, i));

            }

            //floor and ceiling
            for (int k = 0; k <= BASE_WIDTH; k++) {

                //floor
                PlaceShellBlock(chunk, new BlockPos(i, 0, k));

                //ceiling
                PlaceShellBlock(chunk, new BlockPos(i, BASE_WIDTH, k));

            }

        }

        PlaceReturnButtonBlock(chunk);

        return CompletableFuture.completedFuture(chunk);

    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) { return 0; }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) { return null; }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) { }

}
