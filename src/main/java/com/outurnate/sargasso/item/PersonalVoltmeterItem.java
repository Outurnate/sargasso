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

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class PersonalVoltmeterItem extends Item {
	public PersonalVoltmeterItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
		if (player instanceof ServerPlayer serverPlayer) {
			serverPlayer.sendSystemMessage(
					Component.translatable(
							"chat." + SuperSargassoSea.MODID + ".voltmeter",
							serverPlayer
									.getData(LocalAttachmentTypes.GENERATOR_COUNT).toComponent("0.##")));
		}
		return InteractionResult.SUCCESS;
	}
}
