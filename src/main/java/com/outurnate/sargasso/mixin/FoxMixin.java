package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.registry.LocalTags;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.fox.Fox;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Fox.class)
public class FoxMixin {
    @Inject(method = "trusts", at = @At("HEAD"), cancellable = true)
    private void sargasso$trusts(LivingEntity entity, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(LocalTags.FOX_TRUST_HAT)) {
            callbackInfo.setReturnValue(true);
        }
    }
}
