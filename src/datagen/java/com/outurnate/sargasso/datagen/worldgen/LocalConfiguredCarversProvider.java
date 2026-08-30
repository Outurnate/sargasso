package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.repository.LocalTags;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class LocalConfiguredCarversProvider {
    public static final ResourceKey<ConfiguredWorldCarver<?>> CAVE = ResourceKey
        .create(Registries.CONFIGURED_CARVER, SuperSargassoSea.ID("cave"));

    public static void provide(BootstrapContext<ConfiguredWorldCarver<?>> bootstrap) {
        HolderGetter<Block> blocks = bootstrap.lookup(Registries.BLOCK);
        bootstrap.register(
            CAVE,
            WorldCarver.CAVE
                .configured(
                    new CaveCarverConfiguration(
                        0.25F,
                        UniformHeight.of(VerticalAnchor.aboveBottom(1), VerticalAnchor.absolute(70)),
                        UniformFloat.of(0.1F, 0.9F),
                        VerticalAnchor.aboveBottom(0),
                        CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
                        blocks.getOrThrow(LocalTags.SEA_CARVER_REPLACEABLES),
                        UniformFloat.of(0.6F, 1.5F),
                        UniformFloat.of(0.7F, 1.4F),
                        UniformFloat.of(-1.0F, -0.4F))));
    }
}
