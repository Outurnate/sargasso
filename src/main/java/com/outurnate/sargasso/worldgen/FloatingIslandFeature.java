package com.outurnate.sargasso.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
        BlockPos origin = context.origin();

        int blobs = random.nextInt(2, 5);
        for (int i = 0; i < blobs; ++i) {
            int size = random.nextInt(5, 7);
            double angle = random.nextDouble() * Math.PI * 2.0;
            float x = Mth.cos(angle) * size;
            float y = Mth.sin(angle) * size;
            origin = origin.offset((int) Math.ceil(x), (int) Math.ceil(y), 0);
            placeIsland(level, random, origin, size);
        }

        return true;
    }

    private void placeIsland(
        WorldGenLevel level,
        RandomSource random,
        BlockPos origin,
        float size) {

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

            size -= random.nextInt(2) + 0.5F;
        }
    }
}
