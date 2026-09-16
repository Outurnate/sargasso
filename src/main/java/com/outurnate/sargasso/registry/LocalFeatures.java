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
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.worldgen.FloatingIslandFeature;
import com.outurnate.sargasso.worldgen.PortalFeature;
import com.outurnate.sargasso.worldgen.RuinsFeature;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister
			.create(Registries.FEATURE, SuperSargassoSea.MODID);
	public static final DeferredHolder<Feature<?>, FloatingIslandFeature> FLOATING_ISLAND = REGISTRY
			.register(
					"floating_island",
					FloatingIslandFeature::new);
	public static final DeferredHolder<Feature<?>, PortalFeature> PORTAL = REGISTRY
			.register(
					"portal",
					PortalFeature::new);
	public static final DeferredHolder<Feature<?>, RuinsFeature> RUINS = REGISTRY
			.register(
					"ruins",
					RuinsFeature::new);

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}
}
