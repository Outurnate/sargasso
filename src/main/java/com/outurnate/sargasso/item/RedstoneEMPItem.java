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
package com.outurnate.sargasso.item;

import com.outurnate.sargasso.entity.ThrownRedstoneEMP;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class RedstoneEMPItem extends Item implements ProjectileItem {
	public RedstoneEMPItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NonNull Projectile asProjectile(@NonNull Level level, Position position, @NonNull ItemStack itemStack, @NonNull Direction direction) {
		return new ThrownRedstoneEMP(level, position.x(), position.y(), position.z(), itemStack);
	}

	@Override
	public @NonNull InteractionResult use(Level level, Player player, @NonNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		level.playSound(
				null,
				player.getX(),
				player.getY(),
				player.getZ(),
				SoundEvents.SPLASH_POTION_THROW,
				SoundSource.PLAYERS,
				0.5F,
				0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (level instanceof ServerLevel serverLevel) {
			Projectile.spawnProjectileFromRotation(
					ThrownRedstoneEMP::new,
					serverLevel,
					itemStack,
					player,
					0.0F,
					0.5F,
					1.0F);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		itemStack.consume(1, player);
		return InteractionResult.SUCCESS;
	}
}
