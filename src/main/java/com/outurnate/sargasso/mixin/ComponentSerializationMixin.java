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

import com.mojang.serialization.MapCodec;
import com.outurnate.sargasso.network.chat.DurationContents;
import com.outurnate.sargasso.network.chat.RealContents;

import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ComponentSerialization.class)
public abstract class ComponentSerializationMixin {
	@Inject(method = "bootstrap", at = @At("TAIL"))
	private static void sargasso$bootstrap(
			ExtraCodecs.LateBoundIdMapper<String, MapCodec<? extends ComponentContents>> contentTypes,
			CallbackInfo callbackInfo) {
		contentTypes.put("instant", DurationContents.MAP_CODEC);
		contentTypes.put("real", RealContents.MAP_CODEC);
	}
}
