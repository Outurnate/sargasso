/* (C)2026 */
package com.outurnate.sargasso;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.registry.LocalMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;

public class HeadExplosionEffect extends MobEffect {
    public static final Logger LOGGER = LogUtils.getLogger();

    public HeadExplosionEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification) {
        MobEffectInstance selfInstance = mob.getEffect(LocalMobEffects.HEAD_EXPLOSION);
        LOGGER.debug(String.valueOf(selfInstance.getDuration()));
        if (selfInstance != null && selfInstance.getDuration() < 10) {
            mob.hurtServer(level, mob.damageSources().magic(), 100000000.0F);
        }
        return true;
    }
}
