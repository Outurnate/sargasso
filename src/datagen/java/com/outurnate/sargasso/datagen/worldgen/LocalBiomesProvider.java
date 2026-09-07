/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class LocalBiomesProvider {
    public static final ResourceKey<Biome> LOWLANDS = ResourceKey
        .create(Registries.BIOME, SuperSargassoSea.ID("lowlands"));
    public static final ResourceKey<Biome> HILLS = ResourceKey
        .create(Registries.BIOME, SuperSargassoSea.ID("hills"));
    public static final ResourceKey<Biome> PEAKS = ResourceKey
        .create(Registries.BIOME, SuperSargassoSea.ID("peaks"));
    public static final ResourceKey<Biome> RARE = ResourceKey
        .create(Registries.BIOME, SuperSargassoSea.ID("rare"));

    private static Biome buildDefault(
        BootstrapContext<Biome> bootstrap,
        boolean hasFloatingIslands,
        boolean hasPortals,
        boolean hasRuins) {
        HolderGetter<PlacedFeature> placedFeaturesRegistry = bootstrap
            .lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarverRegistry = bootstrap
            .lookup(Registries.CONFIGURED_CARVER);

        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        mobs.addSpawn(
            MobCategory.CREATURE,
            1,
            new MobSpawnSettings.SpawnerData(EntityType.SKELETON_HORSE, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            95,
            new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            5,
            new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 1, 1));
        mobs.addSpawn(
            MobCategory.MONSTER,
            100,
            new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            100,
            new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            100,
            new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            100,
            new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            10,
            new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            80,
            new MobSpawnSettings.SpawnerData(EntityType.HUSK, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            50,
            new MobSpawnSettings.SpawnerData(EntityType.PARCHED, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            50,
            new MobSpawnSettings.SpawnerData(EntityType.BOGGED, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            50,
            new MobSpawnSettings.SpawnerData(EntityType.STRAY, 4, 4));
        mobs.addSpawn(
            MobCategory.MONSTER,
            10,
            new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 1, 1));
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(
            placedFeaturesRegistry,
            configuredCarverRegistry);
        BiomeDefaultFeatures.addFossilDecoration(generation);
        generation.addCarver(LocalConfiguredCarversProvider.CAVE);
        generation.addFeature(
            GenerationStep.Decoration.VEGETAL_DECORATION,
            LocalPlacedFeaturesProvider.PATCH_DEBRIS);
        generation.addFeature(
            GenerationStep.Decoration.UNDERGROUND_ORES,
            LocalPlacedFeaturesProvider.JUNK_ORE);
        generation.addFeature(
            GenerationStep.Decoration.UNDERGROUND_ORES,
            LocalPlacedFeaturesProvider.BRICK_ORE);
        generation.addFeature(
            GenerationStep.Decoration.UNDERGROUND_ORES,
            LocalPlacedFeaturesProvider.COPPER_ORE);
        generation.addFeature(
            GenerationStep.Decoration.UNDERGROUND_ORES,
            LocalPlacedFeaturesProvider.PURPUR_ORE);
        generation.addFeature(
            GenerationStep.Decoration.UNDERGROUND_ORES,
            LocalPlacedFeaturesProvider.PRISMARINE_ORE);
        if (hasFloatingIslands) {
            generation.addFeature(
                GenerationStep.Decoration.RAW_GENERATION,
                LocalPlacedFeaturesProvider.FLOATING_ISLAND_TAIGA);
            generation.addFeature(
                GenerationStep.Decoration.RAW_GENERATION,
                LocalPlacedFeaturesProvider.FLOATING_ISLAND_PLAINS);
            generation.addFeature(
                GenerationStep.Decoration.RAW_GENERATION,
                LocalPlacedFeaturesProvider.FLOATING_ISLAND_TAIGA_BARE);
            generation.addFeature(
                GenerationStep.Decoration.RAW_GENERATION,
                LocalPlacedFeaturesProvider.FLOATING_ISLAND_PLAINS_BARE);
        }
        if (hasPortals) {
            generation.addFeature(
                GenerationStep.Decoration.RAW_GENERATION,
                LocalPlacedFeaturesProvider.PORTAL);
        }
        if (hasRuins) {
            generation.addFeature(
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                LocalPlacedFeaturesProvider.RUINS);
        }

        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(2.0F)
            .downfall(0.0F)
            .setAttribute(
                EnvironmentAttributes.SKY_COLOR,
                OverworldBiomes.calculateSkyColor(2.0F))
            .specialEffects(new BiomeSpecialEffects.Builder().waterColor(4159204).build())
            .hasPrecipitation(false)
            .setAttribute(
                EnvironmentAttributes.BACKGROUND_MUSIC,
                new BackgroundMusic(SoundEvents.MUSIC_BIOME_DESERT))
            .setAttribute(EnvironmentAttributes.SNOW_GOLEM_MELTS, true)
            .mobSpawnSettings(mobs.build())
            .generationSettings(generation.build())
            .build();
    }

    public static void provide(BootstrapContext<Biome> bootstrap) {
        bootstrap.register(
            LOWLANDS,
            buildDefault(bootstrap, false, false, true));
        bootstrap.register(
            HILLS,
            buildDefault(bootstrap, true, false, false));
        bootstrap.register(
            PEAKS,
            buildDefault(bootstrap, false, true, false));
        bootstrap.register(
            RARE,
            buildDefault(bootstrap, true, false, false));
    }
}
