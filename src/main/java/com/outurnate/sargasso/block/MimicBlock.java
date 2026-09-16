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

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class MimicBlock extends Block {
	private final Block copyBlock;

	public MimicBlock(Block copyBlock, Properties properties) {
		super(properties);
		this.copyBlock = copyBlock;
	}

	@Override
	public @NonNull ItemStack getCloneItemStack(
			@NonNull LevelReader level,
			@NonNull BlockPos pos,
			@NonNull BlockState state,
			boolean includeData,
			@NonNull Player player) {
		return copyBlock.asItem().getDefaultInstance();
	}
}
