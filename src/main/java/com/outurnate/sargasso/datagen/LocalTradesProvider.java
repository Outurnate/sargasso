package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;

public class LocalTradesProvider {
    public static final ResourceKey<VillagerTrade> TEST = ResourceKey
        .create(Registries.VILLAGER_TRADE, SuperSargassoSea.ID("scavenger/1/test"));

    public static void provide(BootstrapContext<VillagerTrade> bootstrap) {
        bootstrap.register(
            TEST,
            new VillagerTrade(
                new TradeCost(Items.PUFFERFISH, 4),
                new ItemStackTemplate(Items.EMERALD),
                12,
                30,
                0.05F,
                Optional.empty(),
                List.of()));
    }
}
