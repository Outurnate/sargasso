/* (C)2026 */
package com.outurnate.sargasso;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerLevel;
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
        mob.hurtServer(level, mob.damageSources().magic(), 100000000.0F);
        if (level.getGameRules().get(GameRules.MOB_GRIEFING)) {
            level
                .explode(
                    mob,
                    Explosion.getDefaultDamageSource(level, mob),
                    null,
                    mob.getX(),
                    mob.getY(0.0625),
                    mob.getZ(),
                    4.0F, // TODO amplification
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
