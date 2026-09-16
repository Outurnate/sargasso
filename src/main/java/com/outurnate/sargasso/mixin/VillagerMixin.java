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

import com.outurnate.sargasso.repository.LocalDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin {
	@Unique public void sargasso$spawnGolemIfNeeded(
			ServerLevel level,
			long timestamp,
			int villagersNeededToAgree,
			CallbackInfo callbackInfo) {
		if (level.dimension().equals(LocalDimensions.SEA)) {
			callbackInfo.cancel();
		}
	}
}
