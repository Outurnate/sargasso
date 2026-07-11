package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class LocalNoisesProvider {
    public static final ResourceKey<NoiseParameters> TEMPERATURE = ResourceKey
        .create(Registries.NOISE, SuperSargassoSea.ID("temperature"));

    public static void provide(BootstrapContext<NoiseParameters> bootstrap) {
        bootstrap.register(
            TEMPERATURE,
            new NoiseParameters(
                -10,
                new DoubleArrayList(
                    new double[] {
                        1.5,
                        0.0,
                        1.0,
                        0.0,
                        0.0,
                        0.0 })));
    }
}
