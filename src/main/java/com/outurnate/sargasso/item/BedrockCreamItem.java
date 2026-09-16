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
package com.outurnate.sargasso.item;

import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalSoundEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

public class BedrockCreamItem extends Item {
	public BedrockCreamItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NonNull InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos clickedPos = context.getClickedPos();
		if (level.getBlockState(clickedPos).getBlock() == Blocks.BEDROCK) {
			Player player = context.getPlayer();
			context.getItemInHand().consume(1, player);
			assert player != null;
			level.playSound(
					null,
					player.getX(),
					player.getY(),
					player.getZ(),
					LocalSoundEvents.CREAM_APPLY,
					SoundSource.PLAYERS,
					0.5F,
					0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
			level.setBlock(
					clickedPos,
					LocalBlocks.CREAMY_BEDROCK.get().defaultBlockState(),
					0);
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
