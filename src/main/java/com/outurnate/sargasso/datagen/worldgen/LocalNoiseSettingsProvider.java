/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class LocalNoiseSettingsProvider {
    public static final ResourceKey<NoiseGeneratorSettings> SEA = ResourceKey
        .create(Registries.NOISE_SETTINGS, SuperSargassoSea.ID("sea"));

    public static void provide(BootstrapContext<NoiseGeneratorSettings> bootstrap) {
        HolderGetter<NoiseParameters> noiseParametersRegistry = bootstrap
            .lookup(Registries.NOISE);
        HolderGetter<DensityFunction> densityFunctionsRegistry = bootstrap
            .lookup(Registries.DENSITY_FUNCTION);
        NoiseSettings noiseSettings = new NoiseSettings(-64, 384, 1, 2);
        NoiseRouter noiseRouter = new NoiseRouter(
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            new DensityFunctions.HolderHolder(
                densityFunctionsRegistry.getOrThrow(LocalDensityFunctionProvider.TEMPERATURE)),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.cache2d(DensityFunctions.endIslands(345789)),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.constant(0.0),
            DensityFunctions.add(
                // base grad for y level
                DensityFunctions.yClampedGradient(-64, 320, 1, -1),
                // actual terrain
                DensityFunctions.add(
                    DensityFunctions.mul(
                        // sample the noise
                        DensityFunctions
                            .noise(noiseParametersRegistry.getOrThrow(LocalNoisesProvider.MAIN), 0.25, 0),
                        // scale it by temperature - our "flatness" var
                        new DensityFunctions.HolderHolder(
                            densityFunctionsRegistry.getOrThrow(LocalDensityFunctionProvider.TEMPERATURE))
                                .abs().square()),
                    // roughness
                    DensityFunctions.shiftedNoise2d(
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        2.0,
                        noiseParametersRegistry.getOrThrow(LocalNoisesProvider.DETAIL)))),
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
            SEA,
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
}
