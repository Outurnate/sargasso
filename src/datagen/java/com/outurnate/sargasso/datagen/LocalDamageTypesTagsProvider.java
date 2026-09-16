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
import com.outurnate.sargasso.repository.LocalDamageTypes;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import org.jspecify.annotations.NonNull;

public class LocalDamageTypesTagsProvider extends DamageTypeTagsProvider {
	public LocalDamageTypesTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, SuperSargassoSea.MODID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider lookupProvider) {
		this.tag(DamageTypeTags.BYPASSES_ARMOR).add(LocalDamageTypes.HEAD_EXPLOSION).replace(false);
		this.tag(DamageTypeTags.BYPASSES_COOLDOWN).add(LocalDamageTypes.HEAD_EXPLOSION).replace(false);
		this.tag(DamageTypeTags.BYPASSES_SHIELD).add(LocalDamageTypes.HEAD_EXPLOSION).replace(false);
		this.tag(DamageTypeTags.IS_EXPLOSION).add(LocalDamageTypes.HEAD_EXPLOSION).replace(false);
		this.tag(DamageTypeTags.NO_KNOCKBACK)
				.add(LocalDamageTypes.HEAD_EXPLOSION)
				.add(LocalDamageTypes.ELECTRIC_SHOCK)
				.replace(false);
		this.tag(DamageTypeTags.IS_LIGHTNING).add(LocalDamageTypes.ELECTRIC_SHOCK).replace(false);
		this.tag(DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES).add(LocalDamageTypes.ELECTRIC_SHOCK)
				.replace(false);
		this.tag(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS).add(LocalDamageTypes.HAMMER).replace(false);
		this.tag(DamageTypeTags.IS_PROJECTILE).add(LocalDamageTypes.HAMMER).replace(false);
		this.tag(DamageTypeTags.PANIC_CAUSES).add(LocalDamageTypes.HAMMER).replace(false);
	}
}
