package com.outurnate.sargasso.registry;

import com.mojang.serialization.Codec;
import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister
        .create(Registries.DATA_COMPONENT_TYPE, SuperSargassoSea.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY = REGISTRY
        .register(
            "energy",
            () -> DataComponentType.<Integer>builder()
                .persistent(Codec.INT)
                .networkSynchronized(ByteBufCodecs.INT)
                .build());

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
