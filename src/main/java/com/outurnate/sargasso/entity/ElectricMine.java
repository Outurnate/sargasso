package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.registry.LocalEntities;

import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class ElectricMine extends Entity {
    private int remainingTicks = 20 * 30;

    public ElectricMine(EntityType<?> type, Level level) {
        super(type, level);
    }

    public ElectricMine(Level level) {
        super(LocalEntities.ELECTRIC_MINE.get(), level);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
    }

    @Override
    protected void defineSynchedData(Builder entityData) {
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
    }

    @Override
    public void tick() {
        --remainingTicks;
        if (remainingTicks <= 0) {
            this.remove(RemovalReason.KILLED);
        }

        this.applyGravity();
        Vec3 deltaMovement = this.getDeltaMovement();
        if (this.onGround()) {
            deltaMovement = deltaMovement.multiply(0.1, 1.0, 0.1);
        }
        this.move(MoverType.SELF, deltaMovement);
        this.applyEffectsFromBlocks();

        super.tick();
    }
}
