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

import com.mojang.serialization.MapCodec;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.loot.FlimFlamLoreFunction;
import com.outurnate.sargasso.loot.LostItemFunction;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalLootItemFunctions {
	public static final DeferredRegister<MapCodec<? extends LootItemFunction>> REGISTRY = DeferredRegister
			.create(Registries.LOOT_FUNCTION_TYPE, SuperSargassoSea.MODID);
	public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<FlimFlamLoreFunction>> FLIM_FLAM_LORE_FUNCTION = REGISTRY
			.register("flimflamlore", () -> FlimFlamLoreFunction.MAP_CODEC);
	public static final DeferredHolder<MapCodec<? extends LootItemFunction>, MapCodec<LostItemFunction>> LOST_ITEM_FUNCTION = REGISTRY
			.register("lostitem", () -> LostItemFunction.MAP_CODEC);

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}
}
