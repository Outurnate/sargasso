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

import com.outurnate.sargasso.block.entity.BetaChestBlockEntity;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class BetaChestBlock extends ChestBlock {
	public BetaChestBlock(
			Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityType,
			SoundEvent openSound,
			SoundEvent closeSound,
			Properties properties) {
		super(blockEntityType, openSound, closeSound, properties);
	}

	@Override
	public @NonNull ItemStack getCloneItemStack(
			@NonNull LevelReader level,
			@NonNull BlockPos pos,
			@NonNull BlockState state,
			boolean includeData,
			@NonNull Player player) {
		return Blocks.CHEST.asItem().getDefaultInstance();
	}

	@Override
	protected @NonNull VoxelShape getShape(
			@NonNull BlockState state,
			@NonNull BlockGetter level,
			@NonNull BlockPos pos,
			@NonNull CollisionContext context) {
		return Shapes.block();
	}

	@Override
	public @NonNull BlockEntity newBlockEntity(@NonNull BlockPos worldPosition, @NonNull BlockState blockState) {
		return new BetaChestBlockEntity(worldPosition, blockState);
	}
}
