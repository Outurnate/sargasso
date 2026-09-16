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
package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

public class ThrownLightningBottle extends ThrowableItemProjectile {
	public ThrownLightningBottle(EntityType<? extends ThrownLightningBottle> type, Level level) {
		super(type, level);
	}

	public ThrownLightningBottle(Level level, double x, double y, double z, ItemStack itemStack) {
		super(LocalEntities.LIGHTNING_BOTTLE.get(), x, y, z, level, itemStack);
	}

	public ThrownLightningBottle(Level level, LivingEntity mob, ItemStack itemStack) {
		super(LocalEntities.LIGHTNING_BOTTLE.get(), mob, level, itemStack);
	}

	@Override
	protected @NonNull Item getDefaultItem() {
		return LocalItems.LIGHTNING_BOTTLE.get();
	}

	@Override
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			ItemStack item = this.getItem();
			if (!item.isEmpty()) {
				ItemParticleOption breakParticle = new ItemParticleOption(
						ParticleTypes.ITEM,
						ItemStackTemplate.fromNonEmptyStack(item));

				for (int i = 0; i < 8; i++) {
					this.level()
							.addParticle(
									breakParticle,
									this.getX(),
									this.getY(),
									this.getZ(),
									(this.random.nextFloat() - 0.5) * 0.08,
									(this.random.nextFloat() - 0.5) * 0.08,
									(this.random.nextFloat() - 0.5) * 0.08);
				}
			}
		}
	}

	@Override
	protected void onHit(@NonNull HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level().isClientSide()) {
			LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT
					.create(this.level(), EntitySpawnReason.TRIGGERED);
			if (lightningBolt != null) {
				lightningBolt.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
				this.level().addFreshEntity(lightningBolt);
			}

			this.level().broadcastEntityEvent(this, (byte) 3);
			this.discard();
		}
	}
}
