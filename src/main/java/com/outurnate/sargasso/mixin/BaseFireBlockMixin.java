/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.datagen.DataGenerators.LocalDimensions;

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
