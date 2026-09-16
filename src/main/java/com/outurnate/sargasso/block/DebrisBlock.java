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
package com.outurnate.sargasso.block;

import com.mojang.serialization.MapCodec;
import com.outurnate.sargasso.registry.LocalBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class DebrisBlock extends Block {
	public static final MapCodec<DebrisBlock> CODEC = simpleCodec(DebrisBlock::new);
	private static final VoxelShape SHAPE = Block.column(8.0, 0.0, 4.0);

	public DebrisBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected boolean canSurvive(@NonNull BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(LocalBlocks.FLOTSAM);
	}

	@Override
	public @NonNull MapCodec<? extends DebrisBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NonNull VoxelShape getShape(
			@NonNull BlockState state,
			@NonNull BlockGetter level,
			@NonNull BlockPos pos,
			@NonNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected @NonNull BlockState updateShape(
			BlockState state,
			@NonNull LevelReader level,
			@NonNull ScheduledTickAccess ticks,
			@NonNull BlockPos pos,
			@NonNull Direction directionToNeighbour,
			@NonNull BlockPos neighbourPos,
			@NonNull BlockState neighbourState,
			@NonNull RandomSource random) {
		return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(
						state,
						level,
						ticks,
						pos,
						directionToNeighbour,
						neighbourPos,
						neighbourState,
						random);
	}
}
