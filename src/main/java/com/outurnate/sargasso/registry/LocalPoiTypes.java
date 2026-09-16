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

import com.google.common.collect.ImmutableSet;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.SortingBinBlock;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalPoiTypes {
	public static final DeferredRegister<PoiType> REGISTRY = DeferredRegister
			.create(Registries.POINT_OF_INTEREST_TYPE, SuperSargassoSea.MODID);

	public static final DeferredHolder<PoiType, PoiType> SCAVENGER_CIRCUITS = REGISTRY.register(
			"scavenger_circuits",
			() -> new PoiType(getSortingBin(SortingBinBlock.Type.CIRCUITS), 1, 1));
	public static final DeferredHolder<PoiType, PoiType> SCAVENGER_CLOCKSPRINGS = REGISTRY.register(
			"scavenger_clocksprings",
			() -> new PoiType(getSortingBin(SortingBinBlock.Type.CLOCKSPRINGS), 1, 1));
	public static final DeferredHolder<PoiType, PoiType> SCAVENGER_BOLTS = REGISTRY.register(
			"scavenger_bolts",
			() -> new PoiType(getSortingBin(SortingBinBlock.Type.BOLTS), 1, 1));
	public static final DeferredHolder<PoiType, PoiType> SCAVENGER_BUCKETS = REGISTRY.register(
			"scavenger_buckets",
			() -> new PoiType(getSortingBin(SortingBinBlock.Type.BUCKETS), 1, 1));
	public static final DeferredHolder<PoiType, PoiType> SCAVENGER_COGS = REGISTRY.register(
			"scavenger_cogs",
			() -> new PoiType(getSortingBin(SortingBinBlock.Type.COGS), 1, 1));
	public static final DeferredHolder<PoiType, PoiType> SCAVENGER_WIRES = REGISTRY.register(
			"scavenger_wires",
			() -> new PoiType(getSortingBin(SortingBinBlock.Type.WIRES), 1, 1));

	private static Set<BlockState> getSortingBin(SortingBinBlock.Type type) {
		return ImmutableSet.of(
				LocalBlocks.SORTING_BIN.get().defaultBlockState()
						.setValue(SortingBinBlock.TYPE, type));
	}

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}
}
