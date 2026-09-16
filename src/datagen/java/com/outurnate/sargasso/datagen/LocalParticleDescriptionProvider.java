/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalParticleTypes;
import java.util.Iterator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class LocalParticleDescriptionProvider extends ParticleDescriptionProvider {
	private static Iterable<Identifier> baseOneTextures(String id, int count) {
		return () -> new Iterator<>() {
			private int counter = 0;

			@Override
			public boolean hasNext() {
				return this.counter < count;
			}

			@Override
			public Identifier next() {
				var texture = SuperSargassoSea.ID(id).withSuffix("_" + (this.counter + 1));
				this.counter++;
				return texture;
			}
		};
	}

	public LocalParticleDescriptionProvider(PackOutput output) {
		super(output);
	}

	@Override
	protected void addDescriptions() {
		this.spriteSet(LocalParticleTypes.SPARK.get(), baseOneTextures("spark", 6));
		this.spriteSet(LocalParticleTypes.BEAM.get(), baseOneTextures("beam", 8));
	}
}
