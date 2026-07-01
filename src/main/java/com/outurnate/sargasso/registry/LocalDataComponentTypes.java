/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.network.chat.LocalizedDurationContents;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister
        .create(Registries.DATA_COMPONENT_TYPE, SuperSargassoSea.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LocalizedDurationContents>> LOCALIZED_DATE = REGISTRY
        .register(
            "localized_date",
            () -> DataComponentType.<LocalizedDurationContents>builder()
                .persistent(LocalizedDurationContents.CODEC.codec())
                .networkSynchronized(LocalizedDurationContents.STREAM_CODEC)
                .build());

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
