/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.repository;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class LocalDamageTypes {
	public static final ResourceKey<DamageType> HEAD_EXPLOSION = ResourceKey
			.create(Registries.DAMAGE_TYPE, SuperSargassoSea.ID("head_explosion"));
	public static final ResourceKey<DamageType> ELECTRIC_SHOCK = ResourceKey
			.create(Registries.DAMAGE_TYPE, SuperSargassoSea.ID("electric_shock"));
	public static final ResourceKey<DamageType> HAMMER = ResourceKey
			.create(Registries.DAMAGE_TYPE, SuperSargassoSea.ID("hammer"));
}
