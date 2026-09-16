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
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.clock.WorldClock;

public class LocalWorldClocksProvider {
	public static final ResourceKey<WorldClock> SEA = ResourceKey
			.create(Registries.WORLD_CLOCK, SuperSargassoSea.ID("sea"));

	public static void provide(BootstrapContext<WorldClock> bootstrap) {
		bootstrap.register(SEA, new WorldClock());
	}
}
