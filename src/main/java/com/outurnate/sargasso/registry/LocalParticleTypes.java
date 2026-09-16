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
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister
			.create(Registries.PARTICLE_TYPE, SuperSargassoSea.MODID);

	public static final Supplier<SimpleParticleType> SPARK = REGISTRY.register(
			"spark",
			() -> new SimpleParticleType(false));

	public static final Supplier<SimpleParticleType> BEAM = REGISTRY.register(
			"beam",
			() -> new SimpleParticleType(true));

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}
}
