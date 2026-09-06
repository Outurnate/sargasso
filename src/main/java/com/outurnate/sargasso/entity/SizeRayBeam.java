package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.ExtraEntityDataSerializers;
import com.outurnate.sargasso.item.SizeRayItem;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalMobEffects;

import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SizeRayBeam extends ThrowableProjectile {
    private static final EntityDataAccessor<Holder<MobEffect>> EFFECT = SynchedEntityData
        .defineId(SizeRayBeam.class, ExtraEntityDataSerializers.MOB_EFFECT);

    private static Holder<MobEffect> defaultEffect() {
        return LocalMobEffects.SHRINK;
    }

    public SizeRayBeam(EntityType<SizeRayBeam> type, Level level) {
        super(type, level);
    }

    public SizeRayBeam(Level level, LivingEntity owner, ItemStack itemStack) {
        super(LocalEntities.SIZE_RAY_BEAM.get(), owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), level);
        this.setOwner(owner);
        if (itemStack.getItem() instanceof SizeRayItem item) {
            this.setEffect(item.getEffect());
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.store("effect", MobEffect.CODEC, getEffect());
    }

    @Override
    protected void defineSynchedData(Builder entityData) {
        entityData.define(EFFECT, defaultEffect());
    }

    private Holder<MobEffect> getEffect() {
        return this.getEntityData().get(EFFECT);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setEffect(input.read("effect", MobEffect.CODEC).orElseGet(SizeRayBeam::defaultEffect));
    }

    private void setEffect(Holder<MobEffect> effect) {
        this.getEntityData().set(EFFECT, effect);
    }
}
