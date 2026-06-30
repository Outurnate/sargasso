/* (C)2026 */
package com.outurnate.sargasso;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DO_CLIENT_CODEGEN = BUILDER
        .comment(
            "Dev-only setting - whether to run a specific datagen step on a client. You never need to turn this on")
        .define("doClientCodegen", false);
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

    public static final ModConfigSpec SPEC = BUILDER.build();
}
