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
package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.loot.LostItemsSavedData;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	public ItemEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Override
	protected void onBelowWorld() {
		if ((Object) this instanceof ItemEntity self && this.level() instanceof ServerLevel) {
			LostItemsSavedData.AddLostItem(self.getItem());
		}
		this.discard();
	}
}
