package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.repository.LocalTradeSets;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LocalTradesProvider extends VillagerTradesTagsProvider {
    private static final Int2ObjectArrayMap<List<VillagerTrade>> scavengerTrades = new Int2ObjectArrayMap<>();

    static {
        List<VillagerTrade> levelOne = new ArrayList<>();
        TradeCost[] foods = new TradeCost[] {
            new TradeCost(Items.BEEF, 1),
            new TradeCost(Items.PORKCHOP, 2),
            new TradeCost(Items.CHICKEN, 2),
            new TradeCost(Items.SALMON, 1)
        };
        for (TradeCost tradeCost : foods) {
            for (ItemStackTemplate gives : generateGives(1)) {
                levelOne.add(
                    new VillagerTrade(
                        tradeCost,
                        gives,
                        12,
                        30,
                        0.05F,
                        Optional.empty(),
                        List.of()));
            }
        }
        for (TradeCost tradeCost : generateCosts(1)) {
            levelOne.add(
                new VillagerTrade(
                    tradeCost,
                    new ItemStackTemplate(
                        Items.POTION,
                        1,
                        DataComponentPatch.builder()
                            .set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER)).build()),
                    12,
                    30,
                    0.05F,
                    Optional.empty(),
                    List.of()));
        }
        scavengerTrades.put(1, levelOne);

        List<VillagerTrade> levelTwo = new ArrayList<>();
        for (TradeCost tradeCost : generateCosts(2)) {
            for (ItemStackTemplate gives : generateGives(2)) {
                if (!tradeCost.item().is(gives.item())) {
                    levelTwo.add(
                        new VillagerTrade(
                            tradeCost,
                            gives,
                            12,
                            60,
                            0.0F,
                            Optional.empty(),
                            List.of()));
                }
            }
        }
        scavengerTrades.put(2, levelTwo);

        List<VillagerTrade> levelThree = new ArrayList<>();
        TradeCost[] buildingMats = new TradeCost[] {
            new TradeCost(Items.BRICK, 6),
            new TradeCost(Items.GRANITE, 24),
            new TradeCost(Items.ANDESITE, 24),
            new TradeCost(Items.DIORITE, 24),
            new TradeCost(Items.GLASS_PANE, 32)
        };
        for (TradeCost tradeCost : buildingMats) {
            for (ItemStackTemplate gives : generateGives(3)) {
                levelThree.add(
                    new VillagerTrade(
                        tradeCost,
                        gives,
                        12,
                        90,
                        0.05F,
                        Optional.empty(),
                        List.of()));
            }
        }
        ItemStackTemplate[] armor = new ItemStackTemplate[] {
            new ItemStackTemplate(
                LocalItems.STUDDED_LEATHER_HELMET,
                1,
                DataComponentPatch.builder().set(DataComponents.DAMAGE, 54).build()),
            new ItemStackTemplate(
                LocalItems.STUDDED_LEATHER_CHESTPLATE,
                1,
                DataComponentPatch.builder().set(DataComponents.DAMAGE, 45).build()),
            new ItemStackTemplate(
                LocalItems.STUDDED_LEATHER_LEGGINGS,
                1,
                DataComponentPatch.builder().set(DataComponents.DAMAGE, 64).build()),
            new ItemStackTemplate(
                LocalItems.STUDDED_LEATHER_BOOTS,
                1,
                DataComponentPatch.builder().set(DataComponents.DAMAGE, 84).build())
        };
        for (TradeCost tradeCost : generateCosts(3)) {
            for (ItemStackTemplate gives : armor) {
                levelThree.add(
                    new VillagerTrade(
                        tradeCost,
                        gives,
                        1,
                        90,
                        0.05F,
                        Optional.empty(),
                        List.of()));
            }
        }
        scavengerTrades.put(3, levelThree);

        scavengerTrades.put(4, levelOne);
        scavengerTrades.put(5, levelOne);
    }

    private static TradeCost[] generateCosts(int scale) {
        return new TradeCost[] {
            new TradeCost(LocalItems.BROKEN_COG, 1 * scale),
            new TradeCost(LocalItems.LOOSE_WIRE, 1 * scale),
            new TradeCost(LocalItems.RUSTED_BOLT, 2 * scale),
            new TradeCost(LocalItems.LEAKY_BUCKET, 2 * scale),
            new TradeCost(LocalItems.CLOCKSPRING, 3 * scale),
            new TradeCost(LocalItems.CIRCUIT_BOARD, 3 * scale)
        };
    }

    private static ItemStackTemplate[] generateGives(int scale) {
        return new ItemStackTemplate[] {
            new ItemStackTemplate(LocalItems.BROKEN_COG, 1 * scale),
            new ItemStackTemplate(LocalItems.LOOSE_WIRE, 1 * scale),
            new ItemStackTemplate(LocalItems.RUSTED_BOLT, 2 * scale),
            new ItemStackTemplate(LocalItems.LEAKY_BUCKET, 2 * scale),
            new ItemStackTemplate(LocalItems.CLOCKSPRING, 3 * scale),
            new ItemStackTemplate(LocalItems.CIRCUIT_BOARD, 3 * scale)
        };
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
                tag.add(getTradeKey("scavenger", level, trade));
            }
        });
    }
}
