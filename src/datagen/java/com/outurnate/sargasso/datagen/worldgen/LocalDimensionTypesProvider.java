/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.LocalTimelineTagsProvider;
import java.util.Optional;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.BedRule;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.DimensionType.Skybox;
import net.minecraft.world.timeline.Timeline;

public class LocalDimensionTypesProvider {
    public static final ResourceKey<DimensionType> SEA = ResourceKey
        .create(Registries.DIMENSION_TYPE, SuperSargassoSea.ID("sea"));

    public static void provide(BootstrapContext<DimensionType> bootstrap) {
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
            SEA,
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
                Optional.of(worldClockRegistry.getOrThrow(LocalWorldClocksProvider.SEA))));
    }
}
