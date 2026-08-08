/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalFeatures;
import com.outurnate.sargasso.worldgen.FloatingIslandFeature.FloatingIslandFeatureConfiguration;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class LocalConfiguredFeaturesProvider {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEBRIS = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("debris"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOATING_ISLAND = ResourceKey
        .create(Registries.CONFIGURED_FEATURE, SuperSargassoSea.ID("floating_island"));

    public static void provide(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap) {
        bootstrap.register(
            DEBRIS,
            new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                    new WeightedStateProvider(
                        WeightedList.<BlockState>builder()
                            .add(LocalBlocks.DEBRIS.get().defaultBlockState(), 1)))));
        bootstrap.register(
            FLOATING_ISLAND,
            new ConfiguredFeature<>(
                LocalFeatures.FLOATING_ISLAND.get(),
                new FloatingIslandFeatureConfiguration(
                    Identifier
                        .fromNamespaceAndPath("minecraft", "village/snowy/houses/snowy_small_house_1"),
                    TreeFeatures.SPRUCE,
                    VegetationFeatures.FLOWER_FLOWER_FOREST)));
    }
}
