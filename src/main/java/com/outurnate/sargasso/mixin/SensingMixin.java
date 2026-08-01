package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sensing.class)
public abstract class SensingMixin {
    @Shadow
    private Mob mob;

    @Inject(method = "hasLineOfSight", at = @At("HEAD"), cancellable = true)
    private void sargasso$hasLineOfSight(Entity target, CallbackInfoReturnable<Boolean> callbackInfo) {
        ItemStack headItems = this.mob.getItemBySlot(EquipmentSlot.HEAD);
        if (headItems != null && headItems.is(LocalItems.PYLON)) {
            callbackInfo.setReturnValue(false);
        }
    }
}
