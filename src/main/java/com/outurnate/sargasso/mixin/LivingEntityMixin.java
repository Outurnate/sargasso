/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.Config;
import com.outurnate.sargasso.Utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    // The injection site for this is strange for a reason
    // ItemEntity doesn't override onBelowWord, so we
    // must inject at the parent
    @Inject(method = "onBelowWorld", at = @At("HEAD"))
    private void sargasso$onBelowWorld(CallbackInfo callbackInfo) {
        if (Config.VOID_SENDS_TO_SEA.getAsBoolean() && (Object) this instanceof ServerPlayer self) {
            Utils.sendToSea(self);
        }
    }
}
