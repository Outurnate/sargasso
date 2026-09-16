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
package com.outurnate.sargasso.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class SparkParticle extends SingleQuadParticle {
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprite;

		public Provider(SpriteSet sprite) {
			this.sprite = sprite;
		}

		public Particle createParticle(
				@NonNull SimpleParticleType options,
				@NonNull ClientLevel level,
				double x,
				double y,
				double z,
				double xAux,
				double yAux,
				double zAux,
				@NonNull RandomSource random) {
			return new SparkParticle(level, x, y, z, xAux, yAux, zAux, this.sprite);
		}
	}

	@SuppressWarnings("deprecation")
	public static final SingleQuadParticle.Layer SPARK_LAYER = new SingleQuadParticle.Layer(
			true,
			TextureAtlas.LOCATION_PARTICLES,
			RenderPipelines.WEATHER_DEPTH_WRITE);

	private final SpriteSet spriteSet;

	public SparkParticle(
			ClientLevel level,
			double x,
			double y,
			double z,
			double xa,
			double ya,
			double za,
			SpriteSet spriteSet) {
		super(level, x, y, z, xa, ya, za, spriteSet.first());
		this.spriteSet = spriteSet;
		this.gravity = 1.0F;
	}

	@Override
	protected @NonNull Layer getLayer() {
		return SPARK_LAYER;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.spriteSet);
	}
}
