package com.outurnate.sargasso.worldgen;

import java.util.HashSet;

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
        HashSet<BlockPos> surface = new HashSet<>();

        int blobs = random.nextInt(2, 5);
        for (int i = 0; i < blobs; ++i) {
            int size = random.nextInt(5, 7);
            BlockPos origin = new BlockPos(minX + random.nextInt(16), targetY, minZ + random.nextInt(16));
            placeIsland(surface, level, random, origin, size);
        }
        int spikes = random.nextInt(0, 4);
        for (int j = 0; j < spikes; ++j) {
            BlockPos origin = surface.stream().skip(random.nextInt(surface.size())).findFirst()
                .orElse(context.origin());
            placeSpike(null, level, random, origin);
        }

        return true;
    }

    private void placeIsland(
        HashSet<BlockPos> surface,
        WorldGenLevel level,
        RandomSource random,
        BlockPos origin,
        float size) {

        for (int y = 0; size > 0.5F; y--) {
            for (int x = Mth.floor(-size); x <= Mth.ceil(size); x++) {
                for (int z = Mth.floor(-size); z <= Mth.ceil(size); z++) {
                    if (x * x + z * z <= (size + 1.0F) * (size + 1.0F)) {
                        setBlock(surface, level, random, origin, x, y, z);
                    }
                }
            }

            size -= random.nextInt(2) + 0.5F;
        }
    }

    private void placeSpike(
        HashSet<BlockPos> surface,
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
        int totalLength = (thickLength * 2) + random.nextInt(3);

        for (int i = 0; i < totalLength; i++) {
            int y = -i;

            setBlock(surface, level, random, origin, 0, y, 0);
            if (i < thickLength) {
                setBlock(surface, level, random, origin, direction.getStepX(), y, direction.getStepZ());
            }
        }
    }

    private void setBlock(
        HashSet<BlockPos> surface,
        WorldGenLevel level,
        RandomSource random,
        BlockPos origin,
        int x,
        int y,
        int z) {
        Block block;
        BlockPos pos = origin.offset(x, y, z);
        if (y == 0) {
            block = Blocks.GRASS_BLOCK;
            if (surface != null) {
                surface.add(pos);
            }
        } else if (y < 5 && random.nextInt(Math.abs(y)) == 0) {
            block = Blocks.DIRT;
        } else {
            block = Blocks.STONE;
        }
        this.setBlock(level, pos, block.defaultBlockState());
    }
}
