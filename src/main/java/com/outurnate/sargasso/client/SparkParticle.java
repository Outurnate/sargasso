package com.outurnate.sargasso.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SparkParticle extends SingleQuadParticle {
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        public Particle createParticle(
            SimpleParticleType options,
            ClientLevel level,
            double x,
            double y,
            double z,
            double xAux,
            double yAux,
            double zAux,
            RandomSource random) {
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
        this.gravity = -1.0F;
    }

    @Override
    protected Layer getLayer() {
        return SPARK_LAYER;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }
}
