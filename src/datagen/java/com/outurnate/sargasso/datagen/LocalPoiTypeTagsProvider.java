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
import com.outurnate.sargasso.registry.LocalPoiTypes;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import org.jspecify.annotations.NonNull;

public class LocalPoiTypeTagsProvider extends PoiTypeTagsProvider {
	public LocalPoiTypeTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, SuperSargassoSea.MODID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider registries) {
		this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
				.add(LocalPoiTypes.SCAVENGER_WIRES.getKey())
				.add(LocalPoiTypes.SCAVENGER_COGS.getKey())
				.add(LocalPoiTypes.SCAVENGER_BUCKETS.getKey())
				.add(LocalPoiTypes.SCAVENGER_BOLTS.getKey())
				.add(LocalPoiTypes.SCAVENGER_CLOCKSPRINGS.getKey())
				.add(LocalPoiTypes.SCAVENGER_CIRCUITS.getKey())
				.replace(false);
	}
}
