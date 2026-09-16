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
package com.outurnate.sargasso.effects;

import com.outurnate.sargasso.repository.LocalDamageTypes;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;

public class HeadExplosionEffect extends MobEffect {
	public HeadExplosionEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification) {
		DamageSource damageSource = new DamageSource(
				level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
						.getOrThrow(LocalDamageTypes.HEAD_EXPLOSION));
		mob.hurtServer(level, damageSource, (float) Math.pow(10.0, amplification + 1));
		if (level.getGameRules().get(GameRules.MOB_GRIEFING)) {
			level
					.explode(
							mob,
							Explosion.getDefaultDamageSource(level, mob),
							null,
							mob.getX(),
							mob.getY(0.0625),
							mob.getZ(),
							(float) Math.min(Math.pow(2.0, amplification + 1), 8.0),
							false,
							Level.ExplosionInteraction.TNT);
		}
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
		return tickCount == 1;
	}
}
