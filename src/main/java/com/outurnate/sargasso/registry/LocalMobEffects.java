/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.effects.HeadExplosionEffect;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister
        .create(Registries.MOB_EFFECT, SuperSargassoSea.MODID);

    public static final Holder<MobEffect> HEAD_EXPLOSION = REGISTRY.register(
        "head_explosion",
        () -> new HeadExplosionEffect(
            MobEffectCategory.HARMFUL,
            0xff0000));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
