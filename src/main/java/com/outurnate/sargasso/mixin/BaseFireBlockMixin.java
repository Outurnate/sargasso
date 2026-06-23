/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.SuperSargassoSea;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
    private static final ResourceKey<Level> SARGASSO = ResourceKey
        .create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "sea"));

    @Inject(method = "inPortalDimension", at = @At("TAIL"), cancellable = true)
    private static void sargasso$inPortalDimension(
        Level level,
        CallbackInfoReturnable<Boolean> callbackInfo) {
        if (level.dimension() == SARGASSO) {
            callbackInfo.setReturnValue(true);
        }
    }
}
