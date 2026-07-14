package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeleeAttackGoal.class)
public class MeleeAttackGoalMixin {
    @Shadow
    protected PathfinderMob mob;

    @Inject(method = "tick", at = @At("HEAD"))
    private void sargasso$tick(CallbackInfo callbackInfo) {
        SuperSargassoSea.LOGGER.error(this.getClass().descriptorString());
        SuperSargassoSea.LOGGER.error(String.valueOf(this.mob.hasItemInSlot(EquipmentSlot.HEAD)));
        if ((Object) this instanceof MeleeAttackGoal self) {
            SuperSargassoSea.LOGGER
                .error(self.getClass().descriptorString() + this.mob.hasItemInSlot(EquipmentSlot.HEAD));
        }
    }
}
