package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalParticleTypes;
import java.util.Iterator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class LocalParticleDescriptionProvider extends ParticleDescriptionProvider {
    public LocalParticleDescriptionProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {

        this.spriteSet(LocalParticleTypes.SPARK.get(), () -> new Iterator<>() {
            private int counter = 0;

            @Override
            public boolean hasNext() {
                return this.counter < 6;
            }

            @Override
            public Identifier next() {
                var texture = SuperSargassoSea.ID("spark").withSuffix("_" + (this.counter + 1));
                this.counter++;
                return texture;
            }
        });
    }
}
