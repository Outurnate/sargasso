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

import com.mojang.serialization.Codec;
import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalDataComponentTypes {
	public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister
			.createDataComponents(Registries.DATA_COMPONENT_TYPE, SuperSargassoSea.MODID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY = REGISTRY
			.registerComponentType(
					"energy",
					builder -> builder
							.persistent(Codec.INT)
							.networkSynchronized(ByteBufCodecs.INT));

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemStackTemplate>> COSMETIC_ITEM = REGISTRY
			.registerComponentType(
					"cosmetic_item",
					builder -> builder
							.persistent(ItemStackTemplate.CODEC)
							.networkSynchronized(ItemStackTemplate.STREAM_CODEC));

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}
}
