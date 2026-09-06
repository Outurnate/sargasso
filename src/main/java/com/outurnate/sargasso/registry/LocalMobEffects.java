/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.effects.HeadExplosionEffect;
import com.outurnate.sargasso.effects.SizeChangeEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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

    private static final Identifier scale = SuperSargassoSea.ID("scale");

    public static final Holder<MobEffect> SHRINK = REGISTRY.register(
        "shrink",
        () -> new SizeChangeEffect(
            MobEffectCategory.NEUTRAL,
            ARGB.color(0x20, 0x20, 0x60))
                .addAttributeModifier(Attributes.SCALE, scale, -0.9, Operation.ADD_MULTIPLIED_BASE)
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, scale, -0.9, Operation.ADD_MULTIPLIED_BASE)
                .addAttributeModifier(Attributes.JUMP_STRENGTH, scale, -0.5, Operation.ADD_MULTIPLIED_BASE));

    public static final Holder<MobEffect> GROW = REGISTRY.register(
        "grow",
        () -> new SizeChangeEffect(
            MobEffectCategory.NEUTRAL,
            ARGB.color(0x60, 0x20, 0x20))
                .addAttributeModifier(Attributes.SCALE, scale, 0.9, Operation.ADD_MULTIPLIED_BASE)
                .addAttributeModifier(Attributes.MOVEMENT_SPEED, scale, 0.9, Operation.ADD_MULTIPLIED_BASE)
                .addAttributeModifier(Attributes.JUMP_STRENGTH, scale, 0.9, Operation.ADD_MULTIPLIED_BASE)
                .addAttributeModifier(Attributes.STEP_HEIGHT, scale, 5, Operation.ADD_VALUE)
                .addAttributeModifier(
                    Attributes.SAFE_FALL_DISTANCE,
                    scale,
                    10,
                    Operation.ADD_MULTIPLIED_BASE));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
