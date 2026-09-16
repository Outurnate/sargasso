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
import com.outurnate.sargasso.effects.AddGeneratorConsumeEffect;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalConsumeEffects {
	public static final DeferredRegister<ConsumeEffect.Type<?>> REGISTRY = DeferredRegister
			.create(Registries.CONSUME_EFFECT_TYPE, SuperSargassoSea.MODID);

	public static final Supplier<ConsumeEffect.Type<AddGeneratorConsumeEffect>> ADD_GENERATOR = REGISTRY
			.register(
					"add_generator",
					() -> new ConsumeEffect.Type<>(
							AddGeneratorConsumeEffect.CODEC,
							AddGeneratorConsumeEffect.STREAM_CODEC));

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}
}
