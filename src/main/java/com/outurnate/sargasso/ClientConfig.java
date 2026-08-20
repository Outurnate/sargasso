package com.outurnate.sargasso;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ADVANCED_RENDERING = BUILDER
        .comment(
            "Whether to enable advanced rendering effects")
        .define("advancedRendering", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
