package com.outurnate.sargasso.worldgen;

import com.outurnate.sargasso.registry.LocalBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PortalFeature extends Feature<NoneFeatureConfiguration> {
    public PortalFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        int size = 10;
        int y = 30;
        for (int x = 0; x < size; ++x) {
            for (int z = 0; z < size; ++z) {
                BlockPos pos = origin.offset(x, y, z);
                if (x == 0 || z == 0 || x == (size - 1) || z == (size - 1)) {
                    this.setBlock(level, pos, Blocks.REINFORCED_DEEPSLATE.defaultBlockState());
                    this.setBlock(level, pos.above(), Blocks.REINFORCED_DEEPSLATE.defaultBlockState());
                } else {
                    this.setBlock(level, pos, LocalBlocks.GLITCH.get().defaultBlockState());
                    this.setBlock(level, pos.above(), Blocks.BEDROCK.defaultBlockState());
                }
            }
        }

        return true;
    }
}
