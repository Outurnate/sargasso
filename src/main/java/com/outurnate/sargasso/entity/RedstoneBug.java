package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.registry.LocalEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class RedstoneBug extends Entity {
    private static final int DEFAULT_LIFE = 20 * 30;
    private int remainingTicks = DEFAULT_LIFE;

    public RedstoneBug(EntityType<?> type, Level level) {
        super(type, level);
    }

    public RedstoneBug(Level level) {
        super(LocalEntities.REDSTONE_BUG.get(), level);
    }

    public RedstoneBug(Level level, int remainingTicks) {
        super(LocalEntities.REDSTONE_BUG.get(), level);
        this.remainingTicks = remainingTicks;
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

        BlockState nearest = this.level()
            .getBlockState(new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()));

        if (nearest.hasProperty(BlockStateProperties.POWER)) {
            nearest.setValue(BlockStateProperties.POWER, Redstone.SIGNAL_MAX);
        }
        if (nearest.hasProperty(BlockStateProperties.POWERED)) {
            nearest.setValue(BlockStateProperties.POWERED, true);
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.applyEffectsFromBlocks();
    }
}
