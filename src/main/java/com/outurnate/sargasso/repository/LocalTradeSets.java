package com.outurnate.sargasso.repository;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;

public class LocalTradeSets {
    public static final ResourceKey<TradeSet> SCAVENGER_LEVEL_1 = ResourceKey
        .create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_1"));
    public static final ResourceKey<TradeSet> SCAVENGER_LEVEL_2 = ResourceKey
        .create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_2"));
    public static final ResourceKey<TradeSet> SCAVENGER_LEVEL_3 = ResourceKey
        .create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_3"));
    public static final ResourceKey<TradeSet> SCAVENGER_LEVEL_4 = ResourceKey
        .create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_4"));
    public static final ResourceKey<TradeSet> SCAVENGER_LEVEL_5 = ResourceKey
        .create(Registries.TRADE_SET, SuperSargassoSea.ID("scavenger/level_5"));
}
