package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister
        .create(Registries.PARTICLE_TYPE, SuperSargassoSea.MODID);

    public static final Supplier<SimpleParticleType> SPARK = REGISTRY.register(
        "spark",
        () -> new SimpleParticleType(false));

    public static final Supplier<SimpleParticleType> BEAM = REGISTRY.register(
        "beam",
        () -> new SimpleParticleType(true));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
