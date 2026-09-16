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
import com.outurnate.sargasso.registry.LocalEntityDataSerializers;
import com.outurnate.sargasso.registry.LocalMobEffects;
import com.outurnate.sargasso.registry.LocalParticleTypes;

import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class SizeRayBeam extends ThrowableProjectile {
	private static final EntityDataAccessor<Holder<MobEffect>> EFFECT = SynchedEntityData
			.defineId(SizeRayBeam.class, LocalEntityDataSerializers.MOB_EFFECT.get());

	private static final int DEFAULT_LIFE = 20 * 30;

	private static Holder<MobEffect> defaultEffect() {
		return LocalMobEffects.SHRINK;
	}

	private int remainingTicks = DEFAULT_LIFE;

	public SizeRayBeam(EntityType<SizeRayBeam> type, Level level) {
		super(type, level);
	}

	public SizeRayBeam(Level level, LivingEntity owner, Holder<MobEffect> item) {
		super(LocalEntities.SIZE_RAY_BEAM.get(), owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), level);
		this.setOwner(owner);
		this.setEffect(item);
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.store("effect", MobEffect.CODEC, getEffect());
		output.putInt("remainingTicks", this.remainingTicks);
	}

	@Override
	protected void defineSynchedData(Builder entityData) {
		entityData.define(EFFECT, defaultEffect());
	}

	@Override
	protected double getDefaultGravity() {
		return 0.0;
	}

	private Holder<MobEffect> getEffect() {
		return this.getEntityData().get(EFFECT);
	}

	@Override
	protected void onHit(@NonNull HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level().isClientSide()) {
			if (hitResult instanceof EntityHitResult entityHitResult
					&& entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
				livingEntity.addEffect(new MobEffectInstance(getEffect(), 600, 0, false, false));
			}
			this.discard();
		}
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput input) {
		super.readAdditionalSaveData(input);
		this.setEffect(input.read("effect", MobEffect.CODEC).orElseGet(SizeRayBeam::defaultEffect));
		this.remainingTicks = input.getIntOr("remainingTicks", DEFAULT_LIFE);
	}

	private void setEffect(Holder<MobEffect> effect) {
		this.getEntityData().set(EFFECT, effect);
	}

	@Override
	public void tick() {
		Vec3 movement = getDeltaMovement();
		super.tick();
		setDeltaMovement(movement); // cancel friction

		--remainingTicks;
		if (remainingTicks <= 0) {
			this.remove(RemovalReason.KILLED);
		}

		level()
				.addParticle(LocalParticleTypes.BEAM.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
	}
}
