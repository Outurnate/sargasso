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
package com.outurnate.sargasso.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import java.util.Map;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.DyedItemColor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record FromCosmeticItemTintSource(int defaultColor, Map<Item, Integer> overrides)
		implements
			ItemTintSource {
	@SuppressWarnings("null")
	public static final MapCodec<FromCosmeticItemTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance
					.group(
							Codec.INT.fieldOf("default").forGetter(FromCosmeticItemTintSource::defaultColor),
							Codec.unboundedMap(BuiltInRegistries.ITEM.byNameCodec(), ExtraCodecs.RGB_COLOR_CODEC)
									.optionalFieldOf("overrides", Map.of()).forGetter(FromCosmeticItemTintSource::overrides))
					.apply(instance, FromCosmeticItemTintSource::new));

	@Override
	public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
		if (itemStack.get(LocalDataComponentTypes.COSMETIC_ITEM) instanceof ItemStackTemplate cosmetic) {
			Item item = cosmetic.item().value();
			if (overrides.containsKey(item)) {
				return overrides.get(item);
			}
		}
		return DyedItemColor.getOrDefault(itemStack, this.defaultColor);
	}

	@Override
	public @NonNull MapCodec<FromCosmeticItemTintSource> type() {
		return MAP_CODEC;
	}
}
