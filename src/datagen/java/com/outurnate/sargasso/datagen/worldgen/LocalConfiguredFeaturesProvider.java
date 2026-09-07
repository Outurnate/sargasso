/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalFeatures;
import com.outurnate.sargasso.worldgen.FloatingIslandFeature.FloatingIslandFeatureConfiguration;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

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
    public static final ResourceKey<ConfiguredFeature<?, ?>> JUNK_ORE = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("junk_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRICK_ORE = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("brick_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMARINE_ORE = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("prismarine_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPUR_ORE = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("purpur_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_ORE = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("copper_ore"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUINS = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("ruins"));

    private static OreConfiguration ore(Block block, int size) {
        return new OreConfiguration(
            List.of(
                OreConfiguration.target(
                    new BlockMatchTest(LocalBlocks.PETRIFIED_FLOTSAM.get()),
                    block.defaultBlockState())),
            size);
    }

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
            .add(SuperSargassoSea.ID("island/taiga_1"))
            .add(SuperSargassoSea.ID("island/taiga_2"))
            .add(SuperSargassoSea.ID("island/taiga_3"))
            .add(SuperSargassoSea.ID("island/taiga_4"))
            .build();
        WeightedList<Identifier> plainsBuildings = WeightedList.<Identifier>builder()
            .add(SuperSargassoSea.ID("island/oak_1"))
            .add(SuperSargassoSea.ID("island/oak_2"))
            .add(SuperSargassoSea.ID("island/oak_3"))
            .add(SuperSargassoSea.ID("island/oak_4"))
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
        bootstrap.register(
            JUNK_ORE,
            new ConfiguredFeature<>(Feature.ORE, ore(LocalBlocks.RICH_PETRIFIED_FLOTSAM.get(), 32)));
        bootstrap.register(
            BRICK_ORE,
            new ConfiguredFeature<>(Feature.ORE, ore(Blocks.BRICKS, 8)));
        bootstrap.register(
            PRISMARINE_ORE,
            new ConfiguredFeature<>(Feature.ORE, ore(Blocks.PRISMARINE_BRICKS, 8)));
        bootstrap.register(
            PURPUR_ORE,
            new ConfiguredFeature<>(Feature.ORE, ore(Blocks.PURPUR_BLOCK, 8)));
        bootstrap.register(
            COPPER_ORE,
            new ConfiguredFeature<>(Feature.ORE, ore(Blocks.OXIDIZED_CUT_COPPER, 8)));
        bootstrap.register(
            RUINS,
            new ConfiguredFeature<>(
                LocalFeatures.RUINS.get(),
                NoneFeatureConfiguration.INSTANCE));
    }
}
