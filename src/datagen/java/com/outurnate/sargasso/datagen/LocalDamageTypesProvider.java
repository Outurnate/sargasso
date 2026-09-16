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
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.repository.LocalDamageTypes;

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
						SuperSargassoSea.MODID + ".head_explosion",
						DamageScaling.NEVER,
						0.1F,
						DamageEffects.HURT,
						DeathMessageType.DEFAULT));
		bootstrap.register(
				LocalDamageTypes.ELECTRIC_SHOCK,
				new DamageType(
						LocalDamageTypes.ELECTRIC_SHOCK.identifier().toLanguageKey(),
						DamageScaling.NEVER,
						0.1F,
						DamageEffects.HURT,
						DeathMessageType.DEFAULT));
		bootstrap.register(
				LocalDamageTypes.HAMMER,
				new DamageType(
						LocalDamageTypes.HAMMER.identifier().toLanguageKey(),
						DamageScaling.NEVER,
						0.1F,
						DamageEffects.HURT,
						DeathMessageType.DEFAULT));
	}
}
