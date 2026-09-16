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

import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class SnowBootsItem extends Item {
	public SnowBootsItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canWalkOnPowderedSnow(ItemStack stack, @NonNull LivingEntity wearer) {
		return stack.is(LocalItems.STUDDED_LEATHER_BOOTS);
	}
}
