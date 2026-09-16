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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
	@Inject(method = "inPortalDimension", at = @At("TAIL"), cancellable = true)
	private static void sargasso$inPortalDimension(
			Level level,
			CallbackInfoReturnable<Boolean> callbackInfo) {
		if (level.dimension() == LocalDimensions.SEA) {
			callbackInfo.setReturnValue(true);
		}
	}
}
