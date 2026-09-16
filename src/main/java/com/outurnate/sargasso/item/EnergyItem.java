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

import com.outurnate.sargasso.Config;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class EnergyItem extends Item {
	public EnergyItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getBarColor(ItemStack stack) {
		int energy = stack.getOrDefault(LocalDataComponentTypes.ENERGY, 0);
		int capacity = Config.BATTERY_CAPACITY.getAsInt();

		float pct = energy / (float) capacity;
		return Mth.hsvToRgb(pct / 3.0f, 1.0f, 1.0f);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		int energy = stack.getOrDefault(LocalDataComponentTypes.ENERGY, 0);
		int capacity = Config.BATTERY_CAPACITY.getAsInt();

		return Math.round(13.0f * energy / capacity);
	}

	@Override
	public boolean isBarVisible(@NonNull ItemStack stack) {
		return true;
	}
}
