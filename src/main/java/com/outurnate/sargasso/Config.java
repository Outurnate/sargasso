/* (C)2026 */
package com.outurnate.sargasso;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.LongValue MAX_LOST_ITEMS_OF_TYPE = BUILDER
        .comment(
            "Maximum number of despawned items of a given type to preserve before excess items are ignored.")
        .defineInRange("maxLostItemsOfType", Long.MAX_VALUE, 0, Long.MAX_VALUE);
    public static final ModConfigSpec.BooleanValue VOID_SENDS_TO_SEA = BUILDER
        .comment(
            "Do players who fall out of the world end up in the Super Sargasso Sea?")
        .define("voidSendsToSea", true);
    public static final ModConfigSpec.DoubleValue VOID_SPAWN_RADIUS = BUILDER
        .comment(
            "Players entering the Super Sargasso Sea will spawn on a circle with this radius")
        .defineInRange("spawnRadius", 1000.0, 0.0, 30_000_000.0);
    public static final ModConfigSpec.IntValue BATTERY_CAPACITY = BUILDER
        .comment(
            "Capacity for rechargable and regular batteries")
        .defineInRange("batteryCapacity", 10000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_POTATO_FET = BUILDER
        .comment(
            "Maximum FE/t the player can produce by eating potato batteries")
        .defineInRange("potatoGenerationMax", 5, 0, 200);
    public static final ModConfigSpec.IntValue SHOCK_THERAPIST_CAPACITY = BUILDER
        .comment(
            "Internal capacity of the shock therapist")
        .defineInRange("shockTherapistCapacity", 10000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue SHOCK_THERAPIST_FET = BUILDER
        .comment(
            "FE/t consumed by the shock therapist")
        .defineInRange("shockTherapistConsumption", 5, 0, 200);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
