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
package com.outurnate.sargasso.effects;

import com.mojang.serialization.MapCodec;
import com.outurnate.sargasso.Config;
import com.outurnate.sargasso.Rational;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;
import com.outurnate.sargasso.registry.LocalConsumeEffects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandlerUtil;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import org.jspecify.annotations.NonNull;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public record AddGeneratorConsumeEffect() implements ConsumeEffect {
	private static final AddGeneratorConsumeEffect INSTANCE = new AddGeneratorConsumeEffect();

	public static final MapCodec<AddGeneratorConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, AddGeneratorConsumeEffect> STREAM_CODEC = StreamCodec
			.unit(INSTANCE);

	@Override
	public ConsumeEffect.@NonNull Type<AddGeneratorConsumeEffect> getType() {
		return LocalConsumeEffects.ADD_GENERATOR.get();
	}

	@Override
	public boolean apply(@NonNull Level level, @NonNull ItemStack stack, LivingEntity user) {
		Rational newGenerator = user
				.getData(LocalAttachmentTypes.GENERATOR_COUNT)
				.add(new Rational(1, 5))
				.clamp(0, Config.MAX_POTATO_FET.getAsInt());
		user.setData(LocalAttachmentTypes.GENERATOR_COUNT, newGenerator);
		return true;
	}

	@SubscribeEvent
	public static void onPostPlayerTickEvent(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer serverPlayer) {
			if (serverPlayer.hasData(LocalAttachmentTypes.GENERATOR_COUNT)) {
				Rational generatorRatio = serverPlayer.getData(LocalAttachmentTypes.GENERATOR_COUNT);
				if (generatorRatio.numerator() == 0
						&& (serverPlayer.level().getGameTime() % generatorRatio.denominator()) != 0) {
					return;
				}
				int generatedAmount = generatorRatio.numerator();

				// transfer power into inventory items
				// this dupes items in creative mode only
				// oh well
				EnergyHandler generatedPower = new SimpleEnergyHandler(
						generatedAmount,
						0,
						generatedAmount,
						generatedAmount);
				Inventory inventory = serverPlayer.getInventory();
				for (ItemStack itemStack : inventory) {
					if (!itemStack.isEmpty()) {
						ItemAccess slot = ItemAccess.forStack(itemStack);
						EnergyHandler chargableItem = slot.getCapability(Capabilities.Energy.ITEM);
						EnergyHandlerUtil.move(generatedPower, chargableItem, generatedAmount, null);
						if (generatedPower.getAmountAsInt() == 0) {
							break;
						}
					}
				}
			}
		}
	}
}
