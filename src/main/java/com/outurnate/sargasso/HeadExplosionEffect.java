/* (C)2026 */
package com.outurnate.sargasso;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.registry.LocalDamageTypes;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;

import org.slf4j.Logger;

public class HeadExplosionEffect extends MobEffect {
    public static final Logger LOGGER = LogUtils.getLogger();

    public HeadExplosionEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification) {
        DamageSource damageSource = new DamageSource(
            level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE)
                .getOrThrow(LocalDamageTypes.HEAD_EXPLOSION),
            mob,
            mob,
            null);
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
