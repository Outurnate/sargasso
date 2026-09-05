package com.outurnate.sargasso.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.NativeImage;
import com.outurnate.sargasso.client.OverlayTextureMetadataSection;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureContents.class)
public abstract class TextureContentsMixin {
    @Inject(method = "load", at = @At("TAIL"), cancellable = true)
    public static void sargasso$load(
        ResourceManager resourceManager,
        Identifier location,
        @Local Resource resource,
        CallbackInfoReturnable<TextureContents> callbackInfo) throws IOException {
        if (resource.metadata().getSection(OverlayTextureMetadataSection.TYPE)
            .orElse(null) instanceof OverlayTextureMetadataSection overlayMetadata) {
            Resource overlayResource = resourceManager.getResourceOrThrow(overlayMetadata.identifier());
            NativeImage overlayImage;
            try (InputStream is = overlayResource.open()) {
                overlayImage = NativeImage.read(is);
            }
            TextureContents original = callbackInfo.getReturnValue();
            callbackInfo.setReturnValue(
                new TextureContents(sargasso$overlay(original.image(), overlayImage), original.metadata()));
        }
    }

    private static NativeImage sargasso$overlay(NativeImage base, NativeImage overlay) {
        return base;
    }
}
