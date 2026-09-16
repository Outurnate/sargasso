/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.metadata.MetadataSectionType;

public record CompositeTextureMetadataSection(List<Identifier> below, List<Identifier> above) {
	@SuppressWarnings("null")
	public static final Codec<CompositeTextureMetadataSection> CODEC = RecordCodecBuilder.create(
			i -> i
					.group(
							Codec.list(Identifier.CODEC).fieldOf("below")
									.forGetter(CompositeTextureMetadataSection::below),
							Codec.list(Identifier.CODEC).fieldOf("above")
									.forGetter(CompositeTextureMetadataSection::above))
					.apply(i, CompositeTextureMetadataSection::new));

	public static final MetadataSectionType<CompositeTextureMetadataSection> TYPE = new MetadataSectionType<>(
			"composite",
			CODEC);
}
