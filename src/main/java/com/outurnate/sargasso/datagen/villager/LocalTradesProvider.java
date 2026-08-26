package com.outurnate.sargasso.datagen.villager;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.repository.LocalTradeSets;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LocalTradesProvider extends VillagerTradesTagsProvider {
    private static final Int2ObjectArrayMap<VillagerTrade[]> scavengerTrades = new Int2ObjectArrayMap<>();

    static {
        scavengerTrades.put(
            1,
            new VillagerTrade[] {
                new VillagerTrade(
                    new TradeCost(Items.PUFFERFISH, 4),
                    new ItemStackTemplate(Items.EMERALD),
                    12,
                    30,
                    0.05F,
                    Optional.empty(),
                    List.of())
            });
    }

    @SuppressWarnings("deprecation")
    private static String getItemName(Holder<Item> item) {
        return item.value().builtInRegistryHolder().getKey().identifier().getPath();
    }

    private static ResourceKey<VillagerTrade> getTradeKey(String profession, int level, VillagerTrade trade) {
        return ResourceKey.create(
            Registries.VILLAGER_TRADE,
            SuperSargassoSea.ID("scavenger/" + level + "/" + getTradeName(trade)));
    }

    private static String getTradeName(VillagerTrade trade) {
        TradeCost wants = villagerTradePeek("wants", trade);
        Optional<TradeCost> additionalWants = villagerTradePeek("additionalWants", trade);
        ItemStackTemplate gives = villagerTradePeek("gives", trade);
        return getItemName(wants.item()) +
            additionalWants.map((tradeCost) -> "_" + getItemName(tradeCost.item())).orElse("") +
            "_" +
            getItemName(gives.item());
    }

    private static TagKey<VillagerTrade> getTradeTagKey(String profession, int level) {
        return TagKey.create(Registries.VILLAGER_TRADE, SuperSargassoSea.ID(profession + "/level_" + level));
    }

    public static void provideTrades(BootstrapContext<VillagerTrade> bootstrap) {
        scavengerTrades.forEach((level, trades) -> {
            for (VillagerTrade trade : trades) {
                bootstrap.register(getTradeKey("scavenger", level, trade), trade);
            }
        });
    }

    public static void provideTradeSets(BootstrapContext<TradeSet> bootstrap) {
        LocalTradeSets.SCAVENGER.forEach((level, key) -> {
            bootstrap.register(
                key,
                new TradeSet(
                    bootstrap.lookup(Registries.VILLAGER_TRADE)
                        .getOrThrow(getTradeTagKey("scavenger", level)),
                    ConstantValue.exactly(2.0F),
                    false,
                    Optional.of(key.identifier().withPrefix("trade_set/"))));
        });
    }

    @SuppressWarnings("unchecked")
    private static <T> T villagerTradePeek(String field, VillagerTrade trade) {
        // well aware this should be cached
        // but datagen doesn't need to be fast
        try {
            Field f = VillagerTrade.class.getDeclaredField(field);
            f.setAccessible(true);
            return (T) f.get(trade);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException();
        }
    }

    public LocalTradesProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        scavengerTrades.forEach((level, trades) -> {
            TagAppender<ResourceKey<VillagerTrade>, VillagerTrade> tag = this
                .tag(getTradeTagKey("scavenger", level));
            for (VillagerTrade trade : trades) {
                tag.add(getTradeKey("scavenger", 1, trade));
            }
        });
    }
}
