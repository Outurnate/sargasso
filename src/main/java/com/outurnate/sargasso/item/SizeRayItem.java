package com.outurnate.sargasso.item;

import com.outurnate.sargasso.entity.SizeRayBeam;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SizeRayItem extends Item {
    public SizeRayItem(Properties properties, Holder<MobEffect> effect) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        level.playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            SoundEvents.SPLASH_POTION_THROW, // TODO
            SoundSource.PLAYERS,
            0.5F,
            0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(
                (l, e, i) -> new SizeRayBeam(l),
                serverLevel,
                null,
                player,
                0.0F,
                0.5F,
                1.0F);
        }

        return InteractionResult.SUCCESS;
    }
}
