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
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.repository.LocalTags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.NonNull;

public class LocalEntityTypeTagsProvider extends EntityTypeTagsProvider {
	public LocalEntityTypeTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, SuperSargassoSea.MODID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider registries) {
		this.tag(LocalTags.CAN_WEAR_PYLON)
				.add(EntityType.BOGGED)
				.add(EntityType.DROWNED)
				.add(EntityType.EVOKER)
				.add(EntityType.HUSK)
				.add(EntityType.ILLUSIONER)
				.add(EntityType.PARCHED)
				.add(EntityType.PILLAGER)
				.add(EntityType.PLAYER)
				.add(EntityType.SKELETON)
				.add(EntityType.STRAY)
				.add(EntityType.VILLAGER)
				.add(EntityType.VINDICATOR)
				.add(EntityType.WANDERING_TRADER)
				.add(EntityType.WITHER_SKELETON)
				.add(EntityType.ZOMBIE)
				.add(EntityType.ZOMBIE_VILLAGER)
				.add(EntityType.ZOMBIFIED_PIGLIN);
		this.tag(EntityTypeTags.IMPACT_PROJECTILES)
				.add(LocalEntities.HAMMER.get())
				.add(LocalEntities.REDSTONE_EMP.get())
				.replace(false);
	}
}
