package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LocalEntityDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> REGISTRY = DeferredRegister
        .create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, SuperSargassoSea.MODID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<MobEffect>>> MOB_EFFECT = REGISTRY
        .register("mob_effect", () -> EntityDataSerializer.forValueType(MobEffect.STREAM_CODEC));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
