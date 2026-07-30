package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalParticleTypes;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class LocalParticleDescriptionProvider extends ParticleDescriptionProvider {
    public LocalParticleDescriptionProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(LocalParticleTypes.SPARK.get(), SuperSargassoSea.ID("spark"), 6, false);
    }
}
