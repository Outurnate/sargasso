package com.outurnate.sargasso.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.NativeImage;
import com.outurnate.sargasso.client.CompositeTextureMetadataSection;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.ARGB;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureContents.class)
public abstract class TextureContentsMixin {
    @Inject(method = "load", at = @At("TAIL"), cancellable = true)
    private static void sargasso$load(
        ResourceManager resourceManager,
        Identifier location,
        CallbackInfoReturnable<TextureContents> callbackInfo,
        @Local Resource resource) throws IOException {
        if (resource.metadata().getSection(CompositeTextureMetadataSection.TYPE)
            .orElse(null) instanceof CompositeTextureMetadataSection overlayMetadata) {

            List<Identifier> ids = Stream.concat(
                Stream.concat(overlayMetadata.below().stream(), Stream.of(location)),
                overlayMetadata.above().stream()).toList();
            ArrayList<NativeImage> images = new ArrayList<>();
            for (Identifier id : ids) {
                Resource currentResource = resourceManager.getResourceOrThrow(id);
                NativeImage currentImage;
                try (InputStream is = currentResource.open()) {
                    currentImage = NativeImage.read(is);
                }
                images.add(currentImage);
            }

            NativeImage result = images.stream().reduce(TextureContentsMixin::sargasso$overlay).orElseThrow();
            TextureMetadataSection metadata = resource.metadata().getSection(TextureMetadataSection.TYPE)
                .orElse(null);
            callbackInfo.setReturnValue(new TextureContents(result, metadata));
        }
    }

    private static NativeImage sargasso$overlay(NativeImage base, NativeImage overlay) {
        int width = Math.max(base.getWidth(), overlay.getWidth());
        int height = Math.max(base.getHeight(), overlay.getHeight());

        NativeImage result = new NativeImage(width, height, false);

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int basePixel;
                if (x < base.getWidth() && y < base.getHeight()) {
                    basePixel = base.getPixel(x, y);
                } else {
                    basePixel = 0;
                }

                int overlayPixel;
                if (x < overlay.getWidth() && y < overlay.getHeight()) {
                    overlayPixel = overlay.getPixel(x, y);
                } else {
                    overlayPixel = 0;
                }

                result.setPixel(x, y, ARGB.alphaBlend(overlayPixel, basePixel));
            }
        }

        return result;
    }
}
