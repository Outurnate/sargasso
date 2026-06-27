/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalSoundEvents {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister
        .create(Registries.SOUND_EVENT, SuperSargassoSea.MODID);

    public static final Holder<SoundEvent> CREAM_APPLY = REGISTRY.register(
        "cream_apply",
        SoundEvent::createVariableRangeEvent);

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
