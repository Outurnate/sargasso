/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalFeatures;
import com.outurnate.sargasso.worldgen.FloatingIslandFeature.FloatingIslandFeatureConfiguration;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class LocalConfiguredFeaturesProvider {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEBRIS = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("debris"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOATING_ISLAND_TAIGA = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("floating_island_taiga"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOATING_ISLAND_PLAINS = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("floating_island_plains"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOATING_ISLAND_PLAINS_BARE = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("floating_island_plains_bare"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOATING_ISLAND_TAIGA_BARE = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("floating_island_taiga_bare"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> PORTAL = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("portal"));

    public static void provide(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap) {
        bootstrap.register(
            DEBRIS,
            new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                    new WeightedStateProvider(
                        WeightedList.<BlockState>builder()
                            .add(LocalBlocks.DEBRIS.get().defaultBlockState(), 1)))));

        WeightedList<Identifier> taigaBuildings = WeightedList.<Identifier>builder()
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/taiga/houses/taiga_small_house_1"))
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/taiga/houses/taiga_small_house_2"))
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/taiga/houses/taiga_small_house_3"))
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/taiga/houses/taiga_small_house_4"))
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/taiga/houses/taiga_small_house_5"))
            .build();
        WeightedList<Identifier> plainsBuildings = WeightedList.<Identifier>builder()
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/plains/houses/plains_small_house_1"))
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/plains/houses/plains_small_house_2"))
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/plains/houses/plains_small_house_3"))
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/plains/houses/plains_small_house_4"))
            .add(Identifier.fromNamespaceAndPath("minecraft", "village/plains/houses/plains_small_house_5"))
            .build();

        bootstrap.register(
            FLOATING_ISLAND_TAIGA,
            new ConfiguredFeature<>(
                LocalFeatures.FLOATING_ISLAND.get(),
                new FloatingIslandFeatureConfiguration(
                    taigaBuildings,
                    TreeFeatures.SPRUCE,
                    Blocks.SHORT_GRASS.defaultBlockState(),
                    20,
                    15)));
        bootstrap.register(
            FLOATING_ISLAND_PLAINS,
            new ConfiguredFeature<>(
                LocalFeatures.FLOATING_ISLAND.get(),
                new FloatingIslandFeatureConfiguration(
                    plainsBuildings,
                    TreeFeatures.OAK,
                    Blocks.SHORT_GRASS.defaultBlockState(),
                    30,
                    20)));
        bootstrap.register(
            FLOATING_ISLAND_TAIGA_BARE,
            new ConfiguredFeature<>(
                LocalFeatures.FLOATING_ISLAND.get(),
                new FloatingIslandFeatureConfiguration(
                    taigaBuildings,
                    TreeFeatures.SPRUCE,
                    Blocks.SHORT_GRASS.defaultBlockState(),
                    0,
                    15)));
        bootstrap.register(
            FLOATING_ISLAND_PLAINS_BARE,
            new ConfiguredFeature<>(
                LocalFeatures.FLOATING_ISLAND.get(),
                new FloatingIslandFeatureConfiguration(
                    plainsBuildings,
                    TreeFeatures.OAK,
                    Blocks.SHORT_GRASS.defaultBlockState(),
                    0,
                    20)));
        bootstrap.register(
            PORTAL,
            new ConfiguredFeature<>(
                LocalFeatures.PORTAL.get(),
                NoneFeatureConfiguration.INSTANCE));
    }
}
