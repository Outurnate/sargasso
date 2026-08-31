package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.repository.LocalDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    public void sargasso$spawnGolemIfNeeded(
        ServerLevel level,
        long timestamp,
        int villagersNeededToAgree,
        CallbackInfo callbackInfo) {
        if (level.dimension().equals(LocalDimensions.SEA)) {
            callbackInfo.cancel();
        }
    }
}
