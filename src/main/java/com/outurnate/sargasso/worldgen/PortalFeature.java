/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.worldgen;

import com.outurnate.sargasso.registry.LocalBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
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
		int height = 30;
		for (int x = 0; x < size; ++x) {
			for (int z = 0; z < size; ++z) {
				BlockPos pos = origin.offset(x, height, z);
				int surface = level.getHeight(Types.MOTION_BLOCKING, pos);
				this.setBlock(
						level,
						new BlockPos(pos.getX(), surface, pos.getZ()),
						LocalBlocks.DEBRIS.get().defaultBlockState());
				if (x == 0 || z == 0 || x == (size - 1) || z == (size - 1)) {
					this.setBlock(
							level,
							pos,
							LocalBlocks.REINFORCED_STARMETAL_BLOCK.get().defaultBlockState());
					this.setBlock(
							level,
							pos.above(),
							LocalBlocks.REINFORCED_STARMETAL_BLOCK.get().defaultBlockState());
				} else {
					this.setBlock(level, pos, LocalBlocks.GLITCH.get().defaultBlockState());
					this.setBlock(
							level,
							pos.above(),
							LocalBlocks.REINFORCED_STARMETAL_BLOCK.get().defaultBlockState());
				}
			}
		}

		return true;
	}
}
