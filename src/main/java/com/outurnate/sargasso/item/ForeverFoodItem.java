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

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class ForeverFoodItem extends Item {
	private static final int TARGET_HUNGY = 17;

	public ForeverFoodItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isFoil(@NonNull ItemStack stack) {
		return true;
	}

	@Override
	public void onUseTick(@NonNull Level level, @NonNull LivingEntity livingEntity, @NonNull ItemStack itemStack, int ticksRemaining) {
		super.onUseTick(level, livingEntity, itemStack, ticksRemaining);
		if (itemStack.get(DataComponents.CONSUMABLE) instanceof Consumable self) {
			// n.b. bastardized version of shouldEmitParticlesAndSounds
			// does NOT take into account any effects that slow consumption
			// food item bobbing has an 8 tick period
			// this SHOULD loop it, but also wait until the food item is
			// in front of the mouth before looping
			// by default the full animation takes 32 ticks
			int waitTicksBeforeUseEffects = (int) (self.consumeTicks() * (14.0F / 32.0F));
			if ((self.consumeTicks() - ticksRemaining) > waitTicksBeforeUseEffects
					&& ticksRemaining % 8 == 0) {
				if (livingEntity instanceof Player player) {
					FoodData foodData = player.getFoodData();
					foodData.setSaturation(foodData.getSaturationLevel() - 1.0F);
					int originalFoodLevel = foodData.getFoodLevel();
					if (originalFoodLevel > TARGET_HUNGY) {
						foodData.setFoodLevel(originalFoodLevel - 1);
					} else if (originalFoodLevel < TARGET_HUNGY) {
						foodData.setFoodLevel(originalFoodLevel + 1);
					} else {
						foodData.setFoodLevel(originalFoodLevel);
						foodData.addExhaustion(0.1F);
					}
				}
				livingEntity.useItemRemaining += 8;
			}
		}
	}
}
