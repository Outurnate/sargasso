package com.outurnate.sargasso.registry;

import com.mojang.serialization.Codec;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.data.Cosmetic;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalDataComponentTypes {
    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister
        .createDataComponents(Registries.DATA_COMPONENT_TYPE, SuperSargassoSea.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY = REGISTRY
        .registerComponentType(
            "energy",
            builder -> builder
                .persistent(Codec.INT)
                .networkSynchronized(ByteBufCodecs.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Cosmetic>> COSMETIC_ITEM = REGISTRY
        .registerComponentType(
            "cosmetic_item",
            builder -> builder
                .persistent(Cosmetic.CODEC)
                .networkSynchronized(Cosmetic.STREAM_CODEC));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
