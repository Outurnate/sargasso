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

public class BeamParticle extends SingleQuadParticle {
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
            return new BeamParticle(level, x, y, z, xAux, yAux, zAux, this.sprite);
        }
    }

    @SuppressWarnings("deprecation")
    public static final SingleQuadParticle.Layer BEAM_LAYER = new SingleQuadParticle.Layer(
        true,
        TextureAtlas.LOCATION_PARTICLES,
        RenderPipelines.WEATHER_DEPTH_WRITE);

    private final SpriteSet spriteSet;

    public BeamParticle(
        ClientLevel level,
        double x,
        double y,
        double z,
        double xa,
        double ya,
        double za,
        SpriteSet spriteSet) {
        super(level, x, y, z, xa, ya, za, spriteSet.first());
        this.xd = xa;
        this.yd = ya;
        this.zd = za;
        this.spriteSet = spriteSet;
        this.gravity = 0.0F;
        this.lifetime = 20;
    }

    @Override
    protected Layer getLayer() {
        return BEAM_LAYER;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteSet);
    }
}
