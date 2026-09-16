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
package com.outurnate.sargasso.repository;

import com.outurnate.sargasso.SuperSargassoSea;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;

public class LocalTradeSets {
	public static final Object2ObjectArrayMap<String, Int2ObjectMap<ResourceKey<TradeSet>>> ALL_TRADESETS = new Object2ObjectArrayMap<>();

	static {
		ALL_TRADESETS.put("scavenger_circuits", generateTradeSet("scavenger_circuits"));
		ALL_TRADESETS.put("scavenger_clocksprings", generateTradeSet("scavenger_clocksprings"));
		ALL_TRADESETS.put("scavenger_bolts", generateTradeSet("scavenger_bolts"));
		ALL_TRADESETS.put("scavenger_buckets", generateTradeSet("scavenger_buckets"));
		ALL_TRADESETS.put("scavenger_cogs", generateTradeSet("scavenger_cogs"));
		ALL_TRADESETS.put("scavenger_wires", generateTradeSet("scavenger_wires"));
	}

	private static Int2ObjectMap<ResourceKey<TradeSet>> generateTradeSet(String profession) {
		return Int2ObjectMap.ofEntries(
				Int2ObjectMap
						.entry(
								1,
								ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID(profession + "/level_1"))),
				Int2ObjectMap
						.entry(
								2,
								ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID(profession + "/level_2"))),
				Int2ObjectMap
						.entry(
								3,
								ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID(profession + "/level_3"))),
				Int2ObjectMap
						.entry(
								4,
								ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID(profession + "/level_4"))),
				Int2ObjectMap
						.entry(
								5,
								ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID(profession + "/level_5"))));
	}
}
