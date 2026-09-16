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
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.worldgen.LocalBiomesProvider;
import com.outurnate.sargasso.repository.LocalTags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jspecify.annotations.NonNull;

public class LocalBiomeTagsProvider extends BiomeTagsProvider {
	public static final TagKey<Biome> HAS_FOSSIL = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/fossil"));
	public static final TagKey<Biome> HAS_FORTRESS = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/fortress"));
	public static final TagKey<Biome> HAS_APOTHECARY = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/apothecary"));
	public static final TagKey<Biome> HAS_OFFICE = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/office"));
	public static final TagKey<Biome> HAS_ESCHER = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/escher"));
	public static final TagKey<Biome> HAS_CASTLE = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/castle"));
	public static final TagKey<Biome> HAS_VILLAGE = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/village"));
	public static final TagKey<Biome> HAS_STARTING_HOUSE = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/starting_house"));
	public static final TagKey<Biome> HAS_SHIPWRECK = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("has_structure/shipwreck"));
	public static final TagKey<Biome> SEA = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("sea"));

	public LocalBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, SuperSargassoSea.MODID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider registries) {
		this.tag(HAS_FOSSIL)
				.addTag(SEA);
		this.tag(HAS_FORTRESS)
				.add(LocalBiomesProvider.PEAKS);
		this.tag(HAS_APOTHECARY)
				.add(Biomes.SWAMP);
		this.tag(HAS_OFFICE)
				.add(LocalBiomesProvider.LOWLANDS);
		this.tag(HAS_ESCHER)
				.add(LocalBiomesProvider.RARE);
		this.tag(HAS_CASTLE)
				.add(LocalBiomesProvider.LOWLANDS);
		this.tag(HAS_VILLAGE)
				.add(LocalBiomesProvider.LOWLANDS);
		this.tag(HAS_STARTING_HOUSE)
				.add(LocalBiomesProvider.LOWLANDS);
		this.tag(HAS_SHIPWRECK)
				.add(LocalBiomesProvider.LOWLANDS);
		this.tag(SEA)
				.add(LocalBiomesProvider.HILLS)
				.add(LocalBiomesProvider.LOWLANDS)
				.add(LocalBiomesProvider.PEAKS)
				.add(LocalBiomesProvider.RARE);
		this.tag(BiomeTags.HAS_PILLAGER_OUTPOST)
				.addTag(SEA)
				.replace(false);
		this.tag(BiomeTags.HAS_RUINED_PORTAL_DESERT)
				.addTag(SEA)
				.replace(false);
		this.tag(LocalTags.LOST_EQUIPMENT)
				.add(LocalBiomesProvider.RARE);
		this.tag(LocalTags.LOST_BLOCKS)
				.add(LocalBiomesProvider.PEAKS);
		this.tag(LocalTags.LOST_ITEMS)
				.add(LocalBiomesProvider.HILLS)
				.add(LocalBiomesProvider.LOWLANDS);
	}
}
