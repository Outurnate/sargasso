/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.EasingType;
import net.minecraft.util.TriState;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.attribute.modifier.BooleanModifier;
import net.minecraft.world.attribute.modifier.ColorModifier;
import net.minecraft.world.attribute.modifier.FloatModifier;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;

public class LocalTimelinesProvider {
    public static final ResourceKey<Timeline> DAY = ResourceKey
        .create(Registries.TIMELINE, SuperSargassoSea.ID("day"));

    public static void provide(BootstrapContext<Timeline> bootstrap) {
        HolderGetter<WorldClock> worldClockRegistry = bootstrap.lookup(Registries.WORLD_CLOCK);
        EasingType skyAngleEase = EasingType.symmetricCubicBezier(0.362F, 0.241F);
        int daySkyColor = ARGB.color(0xAA, 0xAA, 0xAA);
        bootstrap.register(
            DAY,
            Timeline.builder(worldClockRegistry.getOrThrow(LocalWorldClocksProvider.SEA))
                .setPeriodTicks(240000)
                .addTimeMarker(ClockTimeMarkers.DAY, 60000, true)
                .addTimeMarker(ClockTimeMarkers.MIDNIGHT, 180000, true)
                .addTimeMarker(ClockTimeMarkers.NIGHT, 130000, true)
                .addTimeMarker(ClockTimeMarkers.NOON, 10000, true)
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
}
