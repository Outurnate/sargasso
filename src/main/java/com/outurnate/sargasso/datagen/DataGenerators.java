/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.EasingType;
import net.minecraft.util.TriState;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.TrapezoidInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.attribute.modifier.BooleanModifier;
import net.minecraft.world.attribute.modifier.ColorModifier;
import net.minecraft.world.attribute.modifier.FloatModifier;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.DimensionType.Skybox;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class DataGenerators {
    public class LocalBiomes {
        public static final ResourceKey<Biome> SEA = ResourceKey
            .create(Registries.BIOME, Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "sea"));
    }

    public class LocalConfiguredFeatures {
        public static final ResourceKey<ConfiguredFeature<?, ?>> DEBRIS = ResourceKey
            .create(
                Registries.CONFIGURED_FEATURE,
                Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "debris"));
    }

    public class LocalDimensions {
        public static final ResourceKey<Level> SEA = ResourceKey
            .create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "sea"));
    }

    public class LocalDimensionTypes {
        public static final ResourceKey<DimensionType> SEA = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "sea"));
    }

    public class LocalNoiseSettings {
        public static final ResourceKey<NoiseGeneratorSettings> SEA = ResourceKey.create(
            Registries.NOISE_SETTINGS,
            Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "sea"));
    }

    public class LocalPlacedFeatures {
        public static final ResourceKey<PlacedFeature> PATCH_DEBRIS = ResourceKey
            .create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "patch_debris"));
    }

    public class LocalTimelines {
        public static final ResourceKey<Timeline> DAY = ResourceKey
            .create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "day"));
    }

    public class LocalWorldClocks {
        public static final ResourceKey<WorldClock> SEA = ResourceKey
            .create(Registries.WORLD_CLOCK, Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "sea"));
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(LocalModelProvider::new);
        event.createProvider(EnglishLanguageProvider::new);
        event.createProvider(LocalTimelineTagsProvider::new);
        event.createProvider(LocalItemTagsProvider::new);
        event.createProvider(LocalRecipeProvider.Runner::new);
        event
            .createProvider(
                (
                    output,
                    lookupProvider) -> new LootTableProvider(
                        output,
                        Set.of(),
                        List.of(
                            new SubProviderEntry(LocalBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                            new SubProviderEntry(LocalLootTableSubProvider::new, LootContextParamSets.CHEST)),
                        lookupProvider));
        event.createDatapackRegistryObjects(
            new RegistrySetBuilder()
                .add(Registries.NOISE_SETTINGS, DataGenerators::generateNoiseSettings)
                .add(Registries.DIMENSION_TYPE, DataGenerators::generateDimensionTypes)
                .add(Registries.BIOME, DataGenerators::generateBiomes)
                .add(Registries.PLACED_FEATURE, DataGenerators::generatePlacedFeatures)
                .add(Registries.CONFIGURED_FEATURE, DataGenerators::generateConfiguredFeatures)
                .add(Registries.WORLD_CLOCK, DataGenerators::generateWorldClocks)
                .add(Registries.TIMELINE, DataGenerators::generateTimelines));
    }

    private static void generateBiomes(BootstrapContext<Biome> bootstrap) {
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
            new MobSpawnSettings.SpawnerData(EntityType.BREEZE, 1, 1));
        mobs.addSpawn(
            MobCategory.MONSTER,
            10,
            new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 1, 1));

        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(
            placedFeaturesRegistry,
            configuredCarverRegistry);
        BiomeDefaultFeatures.addFossilDecoration(generation);
        generation.addFeature(
            GenerationStep.Decoration.VEGETAL_DECORATION,
            LocalPlacedFeatures.PATCH_DEBRIS);

        bootstrap.register(
            LocalBiomes.SEA,
            new Biome.BiomeBuilder()
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
                .build());
    }

    private static void generateConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap) {
        bootstrap.register(
            LocalConfiguredFeatures.DEBRIS,
            new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                    new WeightedStateProvider(
                        WeightedList.<BlockState>builder()
                            .add(LocalBlocks.DEBRIS.get().defaultBlockState(), 1)))));
    }

    private static void generateDimensionTypes(BootstrapContext<DimensionType> bootstrap) {
        HolderGetter<Timeline> timelineRegistry = bootstrap
            .lookup(Registries.TIMELINE);
        HolderGetter<WorldClock> worldClockRegistry = bootstrap
            .lookup(Registries.WORLD_CLOCK);

        BedRule canAlwaysSleep = new BedRule(
            BedRule.Rule.ALWAYS,
            BedRule.Rule.ALWAYS,
            false,
            Optional.of(Component.translatable("block.minecraft.bed.no_sleep")));
        EnvironmentAttributeMap dimensionAttributes = EnvironmentAttributeMap.builder()
            .set(EnvironmentAttributes.FOG_COLOR, -4138753)
            .set(EnvironmentAttributes.SKY_COLOR, OverworldBiomes.calculateSkyColor(2.0F))
            .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, -16448251)
            .set(EnvironmentAttributes.CLOUD_COLOR, ARGB.white(0.8F))
            .set(EnvironmentAttributes.CLOUD_HEIGHT, 192.33F)
            .set(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.OVERWORLD)
            .set(EnvironmentAttributes.BED_RULE, canAlwaysSleep)
            .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, true)
            .set(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, false)
            .set(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS)
            .build();
        bootstrap.register(
            LocalDimensionTypes.SEA,
            new DimensionType(
                false,
                true,
                false,
                false,
                1.0,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                0.0F,
                new DimensionType.MonsterSettings(UniformInt.of(0, 15), 0),
                Skybox.OVERWORLD,
                CardinalLighting.Type.DEFAULT,
                dimensionAttributes,
                timelineRegistry.getOrThrow(LocalTimelineTagsProvider.IN_SEA),
                Optional.of(worldClockRegistry.getOrThrow(LocalWorldClocks.SEA))));
    }

    private static void generateNoiseSettings(BootstrapContext<NoiseGeneratorSettings> bootstrap) {
        HolderGetter<NoiseParameters> noiseParametersRegistry = bootstrap
            .lookup(Registries.NOISE);
        NoiseSettings noiseSettings = new NoiseSettings(-64, 384, 1, 2);
        NoiseRouter noiseRouter = new NoiseRouter(
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.cache2d(DensityFunctions.endIslands(345789)),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.add(
                DensityFunctions.yClampedGradient(-64, 320, 1, -1),
                DensityFunctions.noise(noiseParametersRegistry.getOrThrow(Noises.GRAVEL), 0.25, 0)),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0));
        SurfaceRules.RuleSource surfaceRules = SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.verticalGradient(
                    "bedrock_floor",
                    VerticalAnchor.bottom(),
                    VerticalAnchor.aboveBottom(5)),
                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())));
        bootstrap.register(
            LocalNoiseSettings.SEA,
            new NoiseGeneratorSettings(
                noiseSettings,
                LocalBlocks.FLOTSAM.get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                noiseRouter,
                surfaceRules,
                List.of(),
                -64,
                true,
                false,
                false,
                false));
    }

    private static void generatePlacedFeatures(BootstrapContext<PlacedFeature> bootstrap) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = bootstrap
            .lookup(Registries.CONFIGURED_FEATURE);
        bootstrap.register(
            LocalPlacedFeatures.PATCH_DEBRIS,
            new PlacedFeature(
                configuredFeatureRegistry.getOrThrow(LocalConfiguredFeatures.DEBRIS),
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

    private static void generateTimelines(BootstrapContext<Timeline> bootstrap) {
        HolderGetter<WorldClock> worldClockRegistry = bootstrap.lookup(Registries.WORLD_CLOCK);
        EasingType skyAngleEase = EasingType.symmetricCubicBezier(0.362F, 0.241F);
        int daySkyColor = ARGB.color(0xAA, 0xAA, 0xAA);
        bootstrap.register(
            LocalTimelines.DAY,
            Timeline.builder(worldClockRegistry.getOrThrow(LocalWorldClocks.SEA))
                .setPeriodTicks(240000)
                .addTimeMarker(ClockTimeMarkers.DAY, 10000, true)
                .addTimeMarker(ClockTimeMarkers.MIDNIGHT, 180000, true)
                .addTimeMarker(ClockTimeMarkers.NIGHT, 130000, true)
                .addTimeMarker(ClockTimeMarkers.NOON, 60000, true)
                .addTimeMarker(ClockTimeMarkers.ROLL_VILLAGE_SIEGE, 180000, false)
                .addTimeMarker(ClockTimeMarkers.WAKE_UP_FROM_SLEEP, 0, false)
                .addTrack(
                    EnvironmentAttributes.SUN_ANGLE,
                    track -> track
                        .setEasing(skyAngleEase)
                        .addKeyframe(6000, 360.0F)
                        .addKeyframe(6000, 0.0F))
                .addTrack(
                    EnvironmentAttributes.MOON_ANGLE,
                    track -> track
                        .setEasing(skyAngleEase)
                        .addKeyframe(6000, 540.0F)
                        .addKeyframe(6000, 180.0F))
                .addTrack(
                    EnvironmentAttributes.STAR_ANGLE,
                    track -> track
                        .setEasing(skyAngleEase)
                        .addKeyframe(6000, 360.0F)
                        .addKeyframe(6000, 0.0F))
                .addModifierTrack(
                    EnvironmentAttributes.FIREFLY_BUSH_SOUNDS,
                    BooleanModifier.OR,
                    track -> track
                        .addKeyframe(126000, true)
                        .addKeyframe(234010, false))
                .addModifierTrack(
                    EnvironmentAttributes.FOG_COLOR,
                    AttributeModifier.override(),
                    track -> track
                        .addKeyframe(133, daySkyColor)
                        .addKeyframe(118670, daySkyColor)
                        .addKeyframe(136700, ARGB.color(0x00, 0x00, 0x00))
                        .addKeyframe(223300, ARGB.color(0x00, 0x00, 0x00)))
                .addModifierTrack(
                    EnvironmentAttributes.SKY_COLOR,
                    AttributeModifier.override(),
                    track -> track
                        .addKeyframe(1330, daySkyColor)
                        .addKeyframe(118670, daySkyColor)
                        .addKeyframe(136700, ARGB.color(0x00, 0x00, 0x00))
                        .addKeyframe(223300, ARGB.color(0x00, 0x00, 0x00)))
                .addModifierTrack(
                    EnvironmentAttributes.SKY_LIGHT_COLOR,
                    ColorModifier.MULTIPLY_RGB,
                    track -> track
                        .addKeyframe(7300, ARGB.color(0xFF, 0xFF, 0xFF))
                        .addKeyframe(112700, ARGB.color(0xFF, 0xFF, 0xFF))
                        .addKeyframe(131400, Timelines.NIGHT_SKY_LIGHT_COLOR)
                        .addKeyframe(228600, Timelines.NIGHT_SKY_LIGHT_COLOR))
                .addModifierTrack(
                    EnvironmentAttributes.SKY_LIGHT_FACTOR,
                    FloatModifier.MULTIPLY,
                    track -> track
                        .addKeyframe(7300, 0.45F)
                        .addKeyframe(112700, 0.45F)
                        .addKeyframe(131400, 0.24F)
                        .addKeyframe(228600, 0.24F))
                .addModifierTrack(
                    EnvironmentAttributes.SKY_LIGHT_LEVEL,
                    FloatModifier.MULTIPLY,
                    track -> track
                        .addKeyframe(1330, 0.45F)
                        .addKeyframe(118670, 0.45F)
                        .addKeyframe(136700, 0.26666668F)
                        .addKeyframe(223300, 0.26666668F))
                .addTrack(
                    EnvironmentAttributes.SUNRISE_SUNSET_COLOR,
                    track -> track
                        .addKeyframe(710, 1609540403)
                        .addKeyframe(3100, 703969843)
                        .addKeyframe(5650, 117167155)
                        .addKeyframe(7300, 16770355)
                        .addKeyframe(112700, 16770355)
                        .addKeyframe(113970, 83679283)
                        .addKeyframe(115220, 268028723)
                        .addKeyframe(116900, 703969843)
                        .addKeyframe(119290, 1609540403)
                        .addKeyframe(122430, -1310226637)
                        .addKeyframe(123580, -857440717)
                        .addKeyframe(125120, -371166669)
                        .addKeyframe(126130, -153261261)
                        .addKeyframe(127320, -19242189)
                        .addKeyframe(128410, -19440589)
                        .addKeyframe(130350, -321760973)
                        .addKeyframe(132520, -1043577037)
                        .addKeyframe(137750, 918435635)
                        .addKeyframe(138880, 532362547)
                        .addKeyframe(140390, 163001139)
                        .addKeyframe(141920, 11744051)
                        .addKeyframe(218070, 11678515)
                        .addKeyframe(219610, 163001139)
                        .addKeyframe(221120, 532362547)
                        .addKeyframe(222250, 918435635)
                        .addKeyframe(227480, -1043577037)
                        .addKeyframe(229650, -321760973)
                        .addKeyframe(231590, -19440589)
                        .addKeyframe(232720, -19242189)
                        .addKeyframe(234880, -371166669)
                        .addKeyframe(236420, -857440717)
                        .addKeyframe(237570, -1310226637))
                .addModifierTrack(
                    EnvironmentAttributes.STAR_BRIGHTNESS,
                    FloatModifier.MAXIMUM,
                    track -> track
                        .addKeyframe(920, 0.037F)
                        .addKeyframe(6270, 0.0F)
                        .addKeyframe(113730, 0.0F)
                        .addKeyframe(117320, 0.016F)
                        .addKeyframe(119590, 0.044F)
                        .addKeyframe(123990, 0.143F)
                        .addKeyframe(127290, 0.258F)
                        .addKeyframe(132280, 0.5F)
                        .addKeyframe(227720, 0.5F)
                        .addKeyframe(230320, 0.364F)
                        .addKeyframe(233560, 0.225F)
                        .addKeyframe(237580, 0.101F))
                .addModifierTrack(
                    EnvironmentAttributes.CLOUD_COLOR,
                    ColorModifier.MULTIPLY_ARGB,
                    track -> track
                        .addKeyframe(1330, ARGB.color(0xFF, 0xFF, 0xFF))
                        .addKeyframe(118670, ARGB.color(0xFF, 0xFF, 0xFF))
                        .addKeyframe(136700, Timelines.NIGHT_CLOUD_COLOR_MULTIPLIER)
                        .addKeyframe(223300, Timelines.NIGHT_CLOUD_COLOR_MULTIPLIER))
                .addTrack(
                    EnvironmentAttributes.EYEBLOSSOM_OPEN,
                    track -> track
                        .addKeyframe(126000, TriState.TRUE)
                        .addKeyframe(234010, TriState.FALSE))
                .addModifierTrack(
                    EnvironmentAttributes.CREAKING_ACTIVE,
                    BooleanModifier.OR,
                    track -> track
                        .addKeyframe(126000, true)
                        .addKeyframe(234010, false))
                .addModifierTrack(
                    EnvironmentAttributes.TURTLE_EGG_HATCH_CHANCE,
                    FloatModifier.MAXIMUM,
                    track -> track
                        .setEasing(EasingType.CONSTANT)
                        .addKeyframe(210620, 1.0F)
                        .addKeyframe(219050, 0.002F))
                .addModifierTrack(
                    EnvironmentAttributes.CAT_WAKING_UP_GIFT_CHANCE,
                    FloatModifier.MAXIMUM,
                    track -> track
                        .setEasing(EasingType.CONSTANT)
                        .addKeyframe(3620, 0.0F)
                        .addKeyframe(236670, 0.7F))
                .addModifierTrack(
                    EnvironmentAttributes.BEES_STAY_IN_HIVE,
                    BooleanModifier.OR,
                    track -> track
                        .addKeyframe(125420, true)
                        .addKeyframe(234600, false))
                .addModifierTrack(
                    EnvironmentAttributes.MONSTERS_BURN,
                    BooleanModifier.OR,
                    track -> track
                        .addKeyframe(125420, false)
                        .addKeyframe(234600, true))
                .addTrack(
                    EnvironmentAttributes.MOON_PHASE,
                    track -> track
                        .addKeyframe(0, MoonPhase.FULL_MOON))
                .build());
    }

    private static void generateWorldClocks(BootstrapContext<WorldClock> bootstrap) {
        bootstrap.register(LocalWorldClocks.SEA, new WorldClock());
    }
}
