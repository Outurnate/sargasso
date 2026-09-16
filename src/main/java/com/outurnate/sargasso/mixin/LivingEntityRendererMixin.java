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
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
	@Final
	@Shadow
	protected ItemModelResolver itemModelResolver;

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	public void sargasso$extractRenderState(
			LivingEntity entity,
			LivingEntityRenderState state,
			float partialTicks,
			CallbackInfo callbackInfo) {
		ItemStack headItem = entity.getItemBySlot(EquipmentSlot.HEAD);
		if (headItem.get(LocalDataComponentTypes.COSMETIC_ITEM) instanceof ItemStackTemplate cosmetic) {
			ItemStack innerHeadItem = cosmetic.create();
			state.wornHeadType = null;
			state.wornHeadProfile = null;
			if (!HumanoidArmorLayer.shouldRender(innerHeadItem, EquipmentSlot.HEAD)) {
				this.itemModelResolver
						.updateForLiving(state.headItem, innerHeadItem, ItemDisplayContext.HEAD, entity);
			} else {
				state.headItem.clear();
			}
		}
	}
}
