package com.outurnate.sargasso.mixin;

import com.mojang.logging.LogUtils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeleeAttackGoal.class)
public class MeleeAttackGoalMixin {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Shadow
    protected PathfinderMob mob;

    @Inject(method = "tick", at = @At("HEAD"))
    private void sargasso$tick(CallbackInfo callbackInfo) {
        if ((Object) this instanceof MeleeAttackGoal self) {
            LOGGER.debug(self.getClass().descriptorString() + this.mob.hasItemInSlot(EquipmentSlot.HEAD));
        }
    }
}
