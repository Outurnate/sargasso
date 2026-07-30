/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class LocalDamageTypes {
    public static final ResourceKey<DamageType> HEAD_EXPLOSION = ResourceKey
        .create(Registries.DAMAGE_TYPE, SuperSargassoSea.ID("head_explosion"));
    public static final ResourceKey<DamageType> ELECTRIC_SHOCK = ResourceKey
        .create(Registries.DAMAGE_TYPE, SuperSargassoSea.ID("electric_shock"));
}
