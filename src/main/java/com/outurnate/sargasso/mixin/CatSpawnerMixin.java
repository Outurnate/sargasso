package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.repository.LocalDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatSpawner.class)
public abstract class CatSpawnerMixin {
    @Inject(method = "spawnInVillage", at = @At("HEAD"), cancellable = true)
    private void sargasso$spawnInVillage(
        ServerLevel serverLevel,
        BlockPos spawnPos,
        CallbackInfo callbackInfo) {
        if (serverLevel.dimension().equals(LocalDimensions.SEA)) {
            callbackInfo.cancel();
        }
    }
}
