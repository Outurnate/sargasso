package com.outurnate.sargasso.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
            placeIsland(level, random, origin, size);

            int spikes = random.nextInt(0, 4);
            for (int j = 0; j < spikes; ++j) {
                double angle = random.nextDouble() * Math.PI * 2.0;
                double x = Math.cos(angle) * size;
                double z = Math.sin(angle) * size;
                origin = origin.offset((int) Math.ceil(x), 0, (int) Math.ceil(z));
                placeSpike(level, random, origin);
            }
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
                        setBlock(level, random, origin, x, y, z);
                    }
                }
            }

            size -= random.nextInt(2) + 0.5F;
        }
    }

    private void placeSpike(
        WorldGenLevel level,
        RandomSource random,
        BlockPos origin) {
        Direction direction = switch (random.nextInt(4)) {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.EAST;
            case 3 -> Direction.WEST;
            default -> throw new IllegalStateException();
        };

        int thickLength = random.nextInt(5, 10);
        int totalLength = thickLength + random.nextInt(10);

        for (int i = 0; i < totalLength; i++) {
            int y = -i;

            setBlock(level, random, origin, 0, y, 0);
            if (i < thickLength) {
                setBlock(level, random, origin, direction.getStepX(), y, direction.getStepZ());
            }
        }
    }

    private void setBlock(WorldGenLevel level, RandomSource random, BlockPos origin, int x, int y, int z) {
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
