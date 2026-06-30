/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.registry.LocalDamageTypes;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

public class LocalDamageTypesProvider {
    public static void provide(BootstrapContext<DamageType> bootstrap) {
        bootstrap.register(
            LocalDamageTypes.HEAD_EXPLOSION,
            new DamageType(
                "death.attack.sargasso.head_explosion",
                DamageScaling.ALWAYS,
                0.1F,
                DamageEffects.HURT,
                DeathMessageType.DEFAULT));
    }
}
