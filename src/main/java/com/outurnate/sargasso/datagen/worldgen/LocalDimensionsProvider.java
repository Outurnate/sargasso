package com.outurnate.sargasso.datagen.worldgen;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class LocalDimensionsProvider {
    public static final ResourceKey<LevelStem> SEA = ResourceKey
        .create(Registries.LEVEL_STEM, SuperSargassoSea.ID("sea"));

    public static void provide(BootstrapContext<LevelStem> bootstrap) {
        HolderGetter<DimensionType> dimensionTypeRegistry = bootstrap
            .lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomeRegistry = bootstrap
            .lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseGeneratorSettingsRegistry = bootstrap
            .lookup(Registries.NOISE_SETTINGS); // TODO MAYBE NOT OVERWORLD

        bootstrap.register(
            SEA,
            new LevelStem(
                dimensionTypeRegistry.getOrThrow(LocalDimensionTypesProvider.SEA),
                new NoiseBasedChunkGenerator(
                    MultiNoiseBiomeSource.createFromList(
                        new ParameterList<>(
                            List.of(
                                Pair.of(
                                    Climate.parameters(-0.3F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                                    biomeRegistry.getOrThrow(Biomes.JUNGLE)),
                                Pair.of(
                                    Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                                    biomeRegistry.getOrThrow(LocalBiomesProvider.SEA)),
                                Pair.of(
                                    Climate.parameters(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                                    biomeRegistry.getOrThrow(Biomes.BEACH)),
                                Pair.of(
                                    Climate.parameters(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                                    biomeRegistry.getOrThrow(Biomes.PLAINS))))),
                    noiseGeneratorSettingsRegistry.getOrThrow(NoiseGeneratorSettings.OVERWORLD))));
    }
}
