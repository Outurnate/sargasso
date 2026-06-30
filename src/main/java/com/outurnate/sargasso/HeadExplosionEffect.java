/* (C)2026 */
package com.outurnate.sargasso;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;

public class HeadExplosionEffect extends MobEffect {
    public static final Logger LOGGER = LogUtils.getLogger();

    public HeadExplosionEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification) {
        mob.hurtServer(level, mob.damageSources().magic(), 100000000.0F);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        LOGGER.debug(String.valueOf(tickCount));
        return tickCount < 10;
    }
}
