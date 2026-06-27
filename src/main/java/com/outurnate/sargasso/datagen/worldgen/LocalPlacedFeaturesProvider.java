/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.TrapezoidInt;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class LocalPlacedFeaturesProvider {
    public static final ResourceKey<PlacedFeature> PATCH_DEBRIS = ResourceKey
        .create(Registries.PLACED_FEATURE, SuperSargassoSea.ID("patch_debris"));

    public static void provide(BootstrapContext<PlacedFeature> bootstrap) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = bootstrap
            .lookup(Registries.CONFIGURED_FEATURE);
        bootstrap.register(
            PATCH_DEBRIS,
            new PlacedFeature(
                configuredFeatureRegistry.getOrThrow(LocalConfiguredFeaturesProvider.DEBRIS),
                List.of(
                    RarityFilter.onAverageOnceEvery(2),
                    InSquarePlacement.spread(),
                    HeightmapPlacement.onHeightmap(Types.MOTION_BLOCKING),
                    BiomeFilter.biome(),
                    CountPlacement.of(256),
                    RandomOffsetPlacement
                        .of(TrapezoidInt.of(-14, 14, 0), TrapezoidInt.of(-6, 6, 0)),
                    BlockPredicateFilter
                        .forPredicate(
                            BlockPredicate.matchesTag(BlockTags.AIR)))));
    }
}
