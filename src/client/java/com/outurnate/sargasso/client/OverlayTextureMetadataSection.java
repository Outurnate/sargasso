package com.outurnate.sargasso.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.metadata.MetadataSectionType;

public record OverlayTextureMetadataSection(Identifier identifier) {
    public static final Codec<OverlayTextureMetadataSection> CODEC = RecordCodecBuilder.create(
        i -> i
            .group(
                Identifier.CODEC.fieldOf("identifier").forGetter(OverlayTextureMetadataSection::identifier))
            .apply(i, OverlayTextureMetadataSection::new));
    public static final MetadataSectionType<OverlayTextureMetadataSection> TYPE = new MetadataSectionType<>(
        "overlay",
        CODEC);
}
