/* (C)2026 */
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
