package com.outurnate.sargasso.item;

import com.outurnate.sargasso.entity.ThrownHammer;
import com.outurnate.sargasso.registry.LocalSoundEvents;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class HammerItem extends Item implements ProjectileItem {
    public HammerItem(Properties properties) {
        super(properties);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        ThrownHammer hammer = new ThrownHammer(
            level,
            position.x(),
            position.y(),
            position.z(),
            itemStack.copyWithCount(1));
        hammer.pickup = AbstractArrow.Pickup.ALLOWED;
        return hammer;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);

        if (itemInHand.nextDamageWillBreak()) {
            return InteractionResult.FAIL;
        }

        if (player.getAttackStrengthScale(0.0F) < itemInHand
            .getOrDefault(DataComponents.MINIMUM_ATTACK_CHARGE, 0.0F)) {
            return InteractionResult.FAIL;
        }

        Holder<SoundEvent> sound = EnchantmentHelper
            .pickHighestLevel(itemInHand, EnchantmentEffectComponents.TRIDENT_SOUND)
            .orElse(LocalSoundEvents.HAMMER_THROW);
        if (level instanceof ServerLevel serverLevel) {
            itemInHand.hurtWithoutBreaking(1, player);
            ItemStack thrownItemStack = itemInHand.consumeAndReturn(1, player);
            ThrownHammer hammer = Projectile.spawnProjectileFromRotation(
                ThrownHammer::new,
                serverLevel,
                thrownItemStack,
                player,
                0.0F,
                2.5F,
                1.0F);
            if (player.hasInfiniteMaterials()) {
                hammer.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            level.playSound(null, hammer, sound.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        player.resetAttackStrengthTicker();
        return InteractionResult.CONSUME;
    }
}
