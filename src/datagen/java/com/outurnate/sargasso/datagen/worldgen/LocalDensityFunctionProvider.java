package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class LocalDensityFunctionProvider {
    public static final ResourceKey<DensityFunction> TEMPERATURE = ResourceKey
        .create(Registries.DENSITY_FUNCTION, SuperSargassoSea.ID("temperature"));

    public static void provide(BootstrapContext<DensityFunction> bootstrap) {
        HolderGetter<NoiseParameters> noiseParametersRegistry = bootstrap
            .lookup(Registries.NOISE);

        DensityFunction shift_x = DensityFunctions.flatCache(
            DensityFunctions
                .cache2d(DensityFunctions.shiftA(noiseParametersRegistry.getOrThrow(Noises.SHIFT))));
        DensityFunction shift_z = DensityFunctions.flatCache(
            DensityFunctions
                .cache2d(DensityFunctions.shiftB(noiseParametersRegistry.getOrThrow(Noises.SHIFT))));

        bootstrap.register(
            TEMPERATURE,
            DensityFunctions.shiftedNoise2d(
                shift_x,
                shift_z,
                1.0,
                noiseParametersRegistry.getOrThrow(LocalNoisesProvider.TEMPERATURE)));
    }
}
