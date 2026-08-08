package com.outurnate.sargasso.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FloatingIslandFeature extends Feature<NoneFeatureConfiguration> {
    public FloatingIslandFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        ChunkPos genChunk = ChunkPos.containing(context.origin());
        int minX = genChunk.getMinBlockX();
        int targetY = context.origin().getY();
        int minZ = genChunk.getMinBlockZ();

        int blobs = random.nextInt(2, 5);
        for (int i = 0; i < blobs; ++i) {
            int size = random.nextInt(5, 7);
            BlockPos origin = new BlockPos(minX + random.nextInt(16), targetY, minZ + random.nextInt(16));
            placeIsland(level, random, origin, size, 1);

            int spikes = random.nextInt(0, 4);
            for (int j = 0; j < spikes; ++j) {
                double angle = random.nextDouble() * Math.PI * 2.0;
                double x = Math.cos(angle) * size;
                double z = Math.sin(angle) * size;
                origin = origin.offset((int) Math.ceil(x), 0, (int) Math.ceil(z));
                placeIsland(level, random, origin, 1, 3);
            }
        }

        return true;
    }

    private void placeIsland(
        WorldGenLevel level,
        RandomSource random,
        BlockPos origin,
        float size,
        int decay) {

        for (int y = 0; size > 0.5F; y--) {
            for (int x = Mth.floor(-size); x <= Mth.ceil(size); x++) {
                for (int z = Mth.floor(-size); z <= Mth.ceil(size); z++) {
                    if (x * x + z * z <= (size + 1.0F) * (size + 1.0F)) {
                        Block block;
                        if (y == 0) {
                            block = Blocks.GRASS_BLOCK;
                        } else if (y < 5 && random.nextInt(Math.abs(y)) == 0) {
                            block = Blocks.DIRT;
                        } else {
                            block = Blocks.STONE;
                        }
                        this.setBlock(level, origin.offset(x, y, z), block.defaultBlockState());
                    }
                }
            }

            if (random.nextInt(decay) == 0) {
                size -= random.nextInt(2) + 0.5F;
            }
        }
    }
}
