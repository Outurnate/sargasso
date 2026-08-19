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
    public static final Holder<SoundEvent> GLITCH_TELEPORT = REGISTRY.register(
        "glitch_teleport",
        SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> TOASTER = REGISTRY.register(
        "toaster",
        SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> PYLON = REGISTRY.register(
        "pylon",
        SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> RECORD_UNCHECKED = REGISTRY.register(
        "record_unchecked",
        SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> ZAP = REGISTRY.register(
        "zap",
        SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> HAMMER_HIT = REGISTRY.register(
        "hammer_hit",
        SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> HAMMER_HIT_GROUND = REGISTRY.register(
        "hammer_hit_ground",
        SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> HAMMER_THROW = REGISTRY.register(
        "hammer_throw",
        SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> HAMMER_RETURN = REGISTRY.register(
        "hammer_return",
        SoundEvent::createVariableRangeEvent);

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
