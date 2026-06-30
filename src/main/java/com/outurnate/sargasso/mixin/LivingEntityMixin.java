/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.Config;
import com.outurnate.sargasso.registry.LocalDimensions;
import java.util.Set;

import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Heightmap.Types;

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
            ServerLevel sea = self.level().getServer().getLevel(LocalDimensions.SEA);
            RandomSource random = sea.getRandom();

            float radius = 100.0F;
            float theta = random.nextFloat() * Mth.TWO_PI;
            int x = (int) (radius * Mth.cos(theta));
            int z = (int) (radius * Mth.sin(theta));
            int y = sea.getChunkSource()
                .getChunkNow(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z))
                .getHeight(Types.WORLD_SURFACE, x, z);

            self.teleportTo(sea, x, y, z, Set.of(), 0, 0, false);
        }
    }
}
