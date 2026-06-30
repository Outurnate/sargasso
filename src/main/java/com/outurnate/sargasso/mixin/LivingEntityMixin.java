/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.registry.LocalDimensions;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    private static final Logger LOGGER = LogUtils.getLogger();

    // The injection site for this is strange for a reason
    // ItemEntity doesn't override onBelowWord, so we
    // must inject at the parent
    @Inject(method = "onBelowWorld", at = @At("HEAD"))
    private void sargasso$onBelowWorld(CallbackInfo callbackInfo) {
        LOGGER.debug("ALIVE_PLAYER");
        if ((Object) this instanceof ServerPlayer self) {
            ServerLevel sea = self.level().getServer().getLevel(LocalDimensions.SEA);
            self.teleportTo(sea, 0, 256, 0, Set.of(), 0, 0, false);
        }
    }
}
