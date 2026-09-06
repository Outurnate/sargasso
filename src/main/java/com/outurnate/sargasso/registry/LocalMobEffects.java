/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.effects.HeadExplosionEffect;
import com.outurnate.sargasso.effects.SizeChangeEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

    public static final Holder<MobEffect> SHRINK = REGISTRY.register(
        "shrink",
        () -> new SizeChangeEffect(
            MobEffectCategory.NEUTRAL,
            ARGB.color(0x20, 0x20, 0xFF))
                .addAttributeModifier(
                    Attributes.SCALE,
                    SuperSargassoSea.ID("scale"),
                    0.1,
                    Operation.ADD_MULTIPLIED_BASE)
                .setBlendDuration(20));

    public static final Holder<MobEffect> GROW = REGISTRY.register(
        "grow",
        () -> new SizeChangeEffect(
            MobEffectCategory.NEUTRAL,
            ARGB.color(0x20, 0x20, 0xFF))
                .addAttributeModifier(
                    Attributes.SCALE,
                    SuperSargassoSea.ID("scale"),
                    0.1,
                    Operation.ADD_MULTIPLIED_BASE)
                .setBlendDuration(20));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
