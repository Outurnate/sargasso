package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.registry.LocalEntities;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class ElectricMine extends Entity {
    private static final int DEFAULT_LIFE = 20 * 30;
    private int remainingTicks = DEFAULT_LIFE;

    public ElectricMine(EntityType<?> type, Level level) {
        super(type, level);
    }

    public ElectricMine(Level level) {
        super(LocalEntities.ELECTRIC_MINE.get(), level);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.putInt("remainingTicks", this.remainingTicks);
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
    public void onClientRemoval() {
        this.level().addParticle(
            new BlockParticleOption(ParticleTypes.BLOCK, Blocks.IRON_BLOCK.defaultBlockState()),
            this.getX(),
            this.getY(),
            this.getZ(),
            this.getX(),
            this.getY(),
            this.getZ());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        this.remainingTicks = input.getIntOr("remainingTicks", DEFAULT_LIFE);
    }

    @Override
    public void tick() {
        super.tick();

        --remainingTicks;
        if (remainingTicks <= 0) {
            this.remove(RemovalReason.KILLED);
        }

        this.applyGravity();
        Vec3 velocity = this.getDeltaMovement();

        if (onGround()) {
            float friction = this.level().getBlockState(getBlockPosBelowThatAffectsMyMovement())
                .getBlock()
                .getFriction();

            double factor = friction * 0.91;
            velocity = new Vec3(
                velocity.x * factor,
                velocity.y,
                velocity.z * factor);
        } else {
            velocity = new Vec3(
                velocity.x * 0.98,
                velocity.y,
                velocity.z * 0.98);
        }

        this.setDeltaMovement(velocity);
        this.move(MoverType.SELF, velocity);
        this.applyEffectsFromBlocks();
    }
}
