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

import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import com.outurnate.sargasso.repository.LocalTags;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Fox.class)
public abstract class FoxMixin {
	@Inject(method = "trusts", at = @At("HEAD"), cancellable = true)
	private void sargasso$trusts(LivingEntity entity, CallbackInfoReturnable<Boolean> callbackInfo) {
		ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.HEAD);
		if (itemStack.is(LocalTags.FOX_TRUST_HAT)
				|| itemStack.get(LocalDataComponentTypes.COSMETIC_ITEM) instanceof ItemStackTemplate cosmetic
						&& cosmetic.is(LocalTags.FOX_TRUST_HAT)) {
			callbackInfo.setReturnValue(true);
		}
	}
}
