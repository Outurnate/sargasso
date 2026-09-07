/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.TrapezoidInt;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class LocalPlacedFeaturesProvider {
    public static final ResourceKey<PlacedFeature> PATCH_DEBRIS = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("patch_debris"));
    public static final ResourceKey<PlacedFeature> FLOATING_ISLAND_TAIGA = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("floating_island_taiga"));
    public static final ResourceKey<PlacedFeature> FLOATING_ISLAND_PLAINS = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("floating_island_plains"));
    public static final ResourceKey<PlacedFeature> FLOATING_ISLAND_TAIGA_BARE = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("floating_island_taiga_bare"));
    public static final ResourceKey<PlacedFeature> FLOATING_ISLAND_PLAINS_BARE = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("floating_island_plains_bare"));
    public static final ResourceKey<PlacedFeature> PORTAL = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("portal"));
    public static final ResourceKey<PlacedFeature> JUNK_ORE = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("junk_ore"));
    public static final ResourceKey<PlacedFeature> RUINS = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("ruins"));

    public static void provide(BootstrapContext<PlacedFeature> bootstrap) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = bootstrap
            .lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(
            bootstrap,
            PATCH_DEBRIS,
            configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.DEBRIS),
            RarityFilter.onAverageOnceEvery(2),
            InSquarePlacement.spread(),
            HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING),
            BiomeFilter.biome(),
            CountPlacement.of(256),
            RandomOffsetPlacement
                .of(TrapezoidInt.of(-14, 14, 0), TrapezoidInt.of(-6, 6, 0)),
            BlockPredicateFilter
                .forPredicate(
                    BlockPredicate.matchesTag(BlockTags.AIR)));

        PlacementUtils.register(
            bootstrap,
            PORTAL,
            configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.PORTAL),
            RarityFilter.onAverageOnceEvery(90),
            HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING),
            BiomeFilter.biome());

        PlacementUtils.register(
            bootstrap,
            FLOATING_ISLAND_TAIGA,
            configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.FLOATING_ISLAND_TAIGA),
            RarityFilter.onAverageOnceEvery(80),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(200), VerticalAnchor.absolute(300)),
            BiomeFilter.biome());

        PlacementUtils.register(
            bootstrap,
            FLOATING_ISLAND_PLAINS,
            configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.FLOATING_ISLAND_PLAINS),
            RarityFilter.onAverageOnceEvery(80),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(200), VerticalAnchor.absolute(300)),
            BiomeFilter.biome());

        PlacementUtils.register(
            bootstrap,
            FLOATING_ISLAND_TAIGA_BARE,
            configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.FLOATING_ISLAND_TAIGA_BARE),
            RarityFilter.onAverageOnceEvery(80),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(200), VerticalAnchor.absolute(300)),
            BiomeFilter.biome());

        PlacementUtils.register(
            bootstrap,
            FLOATING_ISLAND_PLAINS_BARE,
            configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.FLOATING_ISLAND_PLAINS_BARE),
            RarityFilter.onAverageOnceEvery(80),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.absolute(200), VerticalAnchor.absolute(300)),
            BiomeFilter.biome());

        PlacementUtils.register(
            bootstrap,
            JUNK_ORE,
            configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.JUNK_ORE),
            List.of(
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(70)),
                BiomeFilter.biome()));

        PlacementUtils.register(
            bootstrap,
            RUINS,
            configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.RUINS),
            RarityFilter.onAverageOnceEvery(80),
            HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING),
            BiomeFilter.biome());
    }
}