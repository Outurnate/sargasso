/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.registry.LocalMobEffects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class HeadExplosionEffect extends MobEffect {
    public HeadExplosionEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification) {
        MobEffectInstance selfInstance = mob.getEffect(LocalMobEffects.HEAD_EXPLOSION);
        if (selfInstance.getDuration() < 10) {
            mob.hurtServer(level, mob.damageSources().magic(), 100000000.0F);
        }
        return true;
    }
}
