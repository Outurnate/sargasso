package com.outurnate.sargasso.repository;

import com.outurnate.sargasso.SuperSargassoSea;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;

public class LocalTradeSets {
    public static final Int2ObjectMap<ResourceKey<TradeSet>> SCAVENGER = Int2ObjectMap.ofEntries(
        Int2ObjectMap
            .entry(1, ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_1"))),
        Int2ObjectMap
            .entry(2, ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_2"))),
        Int2ObjectMap
            .entry(3, ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_3"))),
        Int2ObjectMap
            .entry(4, ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_4"))),
        Int2ObjectMap
            .entry(5, ResourceKey.create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_5"))));
}
