package com.outurnate.sargasso.datagen;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.repository.LocalTradeSets;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
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
    private static final Object2ObjectArrayMap<String, Int2ObjectArrayMap<List<VillagerTrade>>> allTrades = new Object2ObjectArrayMap<>();

    static {
        allTrades.put(
            "scavenger_circuits",
            makeTradesForProfession((level) -> new Pair<>(LocalItems.CIRCUIT_BOARD.get(), level)));
        allTrades.put(
            "scavenger_clocksprings",
            makeTradesForProfession((level) -> new Pair<>(LocalItems.CLOCKSPRING.get(), level)));
        allTrades.put(
            "scavenger_bolts",
            makeTradesForProfession((level) -> new Pair<>(LocalItems.RUSTED_BOLT.get(), level * 2)));
        allTrades.put(
            "scavenger_buckets",
            makeTradesForProfession((level) -> new Pair<>(LocalItems.LEAKY_BUCKET.get(), level * 2)));
        allTrades.put(
            "scavenger_cogs",
            makeTradesForProfession((level) -> new Pair<>(LocalItems.BROKEN_COG.get(), level * 3)));
        allTrades.put(
            "scavenger_wires",
            makeTradesForProfession((level) -> new Pair<>(LocalItems.LOOSE_WIRE.get(), level * 3)));
    }

    private static ItemStackTemplate[] generateGives(int scale) {
        return new ItemStackTemplate[] {
            new ItemStackTemplate(LocalItems.BROKEN_COG, 3 * scale),
            new ItemStackTemplate(LocalItems.LOOSE_WIRE, 3 * scale),
            new ItemStackTemplate(LocalItems.RUSTED_BOLT, 2 * scale),
            new ItemStackTemplate(LocalItems.LEAKY_BUCKET, 2 * scale),
            new ItemStackTemplate(LocalItems.CLOCKSPRING, scale),
            new ItemStackTemplate(LocalItems.CIRCUIT_BOARD, scale)
        };
    }

    @SuppressWarnings("deprecation")
    private static String getItemName(Holder<Item> item) {
        return item.value().builtInRegistryHolder().getKey().identifier().getPath();
    }

    private static ResourceKey<VillagerTrade> getTradeKey(String profession, int level, VillagerTrade trade) {
        return ResourceKey.create(
            Registries.VILLAGER_TRADE,
            SuperSargassoSea.ID(profession + "/" + level + "/" + getTradeName(trade)));
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

    private static int getXP(int scale) {
        return switch (scale) {
            case 1 -> 1;
            case 2 -> 5;
            case 3 -> 10;
            case 4 -> 15;
            case 5 -> 30;
            default -> 0;
        };
    }

    // function is level -> cost
    private static Int2ObjectArrayMap<List<VillagerTrade>> makeTradesForProfession(
        Function<Integer, Pair<Item, Integer>> currency) {
        Function<Integer, TradeCost> currencyWants = (amount) -> {
            Pair<Item, Integer> template = currency.apply(amount);
            return new TradeCost(template.getFirst(), template.getSecond());
        };
        Function<Integer, ItemStackTemplate> currencyGives = (amount) -> {
            Pair<Item, Integer> template = currency.apply(amount);
            return new ItemStackTemplate(template.getFirst(), template.getSecond());
        };
        Int2ObjectArrayMap<List<VillagerTrade>> scavengerTrades = new Int2ObjectArrayMap<>();

        List<VillagerTrade> levelOne = new ArrayList<>();
        TradeCost[] foods = new TradeCost[] {
            new TradeCost(Items.BEEF, 1),
            new TradeCost(Items.PORKCHOP, 2),
            new TradeCost(Items.CHICKEN, 2),
            new TradeCost(Items.SALMON, 1),
            new TradeCost(Items.MUTTON, 2),
            new TradeCost(Items.COD, 4),
            new TradeCost(Items.RABBIT, 4)
        };
        for (TradeCost tradeCost : foods) {
            levelOne.add(trade(tradeCost, currencyGives.apply(1), getXP(1)));
        }
        levelOne.add(
            trade(
                currencyWants.apply(1),
                new ItemStackTemplate(
                    Items.POTION,
                    1,
                    DataComponentPatch.builder()
                        .set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER)).build()),
                getXP(1)));
        levelOne.add(trade(currencyWants.apply(1), new ItemStackTemplate(LocalItems.ATLAS), getXP(1)));
        scavengerTrades.put(1, levelOne);

        List<VillagerTrade> levelTwo = new ArrayList<>();
        for (ItemStackTemplate gives : generateGives(2)) {
            TradeCost cost = currencyWants.apply(2);
            if (!cost.item().is(gives.item())) {
                levelTwo.add(trade(cost, gives, 12, getXP(2), 0.0F));
            }
        }
        scavengerTrades.put(2, levelTwo);

        List<VillagerTrade> levelThree = new ArrayList<>();
        TradeCost[] buildingMats = new TradeCost[] {
            new TradeCost(Items.GRAVEL, 64),
            new TradeCost(Items.STONE_BRICKS, 48),
            new TradeCost(Items.OAK_PLANKS, 64),
            new TradeCost(Items.GLASS, 32),
            new TradeCost(Items.STRING, 48)
        };
        for (TradeCost tradeCost : buildingMats) {
            levelThree.add(trade(tradeCost, currencyGives.apply(3), getXP(3)));
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
        for (ItemStackTemplate gives : armor) {
            levelThree.add(trade(currencyWants.apply(3), gives, 1, getXP(3)));
        }
        scavengerTrades.put(3, levelThree);

        List<VillagerTrade> levelFour = new ArrayList<>();
        levelFour
            .add(
                trade(
                    currencyWants.apply(4),
                    new ItemStackTemplate(LocalItems.STARMETAL_SCRAP, 2),
                    getXP(4)));
        levelFour
            .add(trade(currencyWants.apply(4), new ItemStackTemplate(LocalItems.AA_BATTERY, 2), getXP(4)));
        levelFour
            .add(trade(currencyWants.apply(4), new ItemStackTemplate(LocalItems.QUARTER, 7), getXP(4)));
        scavengerTrades.put(4, levelFour);

        List<VillagerTrade> levelFive = new ArrayList<>();
        levelFive
            .add(
                trade(
                    currencyWants.apply(5),
                    new ItemStackTemplate(LocalItems.RECHARGABLE_AA_BATTERY, 1),
                    150));
        levelFive
            .add(trade(currencyWants.apply(5), new ItemStackTemplate(LocalItems.FOX_EARS, 1), 1, getXP(5)));
        scavengerTrades.put(5, levelFive);

        return scavengerTrades;
    }

    public static void provideTrades(BootstrapContext<VillagerTrade> bootstrap) {
        allTrades.forEach((profession, professionTrades) -> {
            professionTrades.forEach((level, trades) -> {
                for (VillagerTrade trade : trades) {
                    bootstrap.register(getTradeKey(profession, level, trade), trade);
                }
            });
        });
    }

    public static void provideTradeSets(BootstrapContext<TradeSet> bootstrap) {
        LocalTradeSets.ALL_TRADESETS.forEach((profession, tradeSet) -> {
            tradeSet.forEach((level, key) -> {
                bootstrap.register(
                    key,
                    new TradeSet(
                        bootstrap.lookup(Registries.VILLAGER_TRADE)
                            .getOrThrow(getTradeTagKey(profession, level)),
                        ConstantValue.exactly(2.0F),
                        false,
                        Optional.of(key.identifier().withPrefix("trade_set/"))));
            });
        });
    }

    private static VillagerTrade trade(TradeCost wants, ItemStackTemplate gives, int xp) {
        return trade(wants, gives, 12, xp);
    }

    private static VillagerTrade trade(TradeCost wants, ItemStackTemplate gives, int maxUses, int xp) {
        return trade(wants, gives, maxUses, xp, 0.05F);
    }

    private static VillagerTrade trade(
        TradeCost wants,
        ItemStackTemplate gives,
        int maxUses,
        int xp,
        float reputationDiscount) {
        return new VillagerTrade(wants, gives, maxUses, xp, reputationDiscount, Optional.empty(), List.of());
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
        allTrades.forEach((profession, professionTrades) -> {
            professionTrades.forEach((level, trades) -> {
                TagAppender<ResourceKey<VillagerTrade>, VillagerTrade> tag = this
                    .tag(getTradeTagKey(profession, level));
                for (VillagerTrade trade : trades) {
                    tag.add(getTradeKey(profession, level, trade));
                }
            });
        });
    }
}
