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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class RedstoneBug extends Entity {
    private static final int DEFAULT_LIFE = 20 * 30;

    private static void spamUpdates(Level level, BlockPos center, int radius) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = center.getX() - radius; x <= center.getX() + radius; ++x) {
            for (int y = center.getY() - radius; y <= center.getY() + radius; ++y) {
                for (int z = center.getZ() - radius; z <= center.getZ() + radius; ++z) {
                    pos.set(x, y, z);
                    level.neighborChanged(
                        pos,
                        level.getBlockState(pos).getBlock(),
                        null);
                    level.updateNeighborsAt(
                        pos,
                        level.getBlockState(pos).getBlock(),
                        null);
                }
            }
        }
    }

    private int remainingTicks = DEFAULT_LIFE;
    private final Vec3 origin;

    public RedstoneBug(EntityType<?> type, Level level) {
        super(type, level);
        this.origin = Vec3.ZERO;
    }

    public RedstoneBug(Level level) {
        super(LocalEntities.REDSTONE_BUG.get(), level);
        this.origin = Vec3.ZERO;
    }

    public RedstoneBug(Level level, int remainingTicks, Vec3 origin) {
        super(LocalEntities.REDSTONE_BUG.get(), level);
        this.remainingTicks = remainingTicks;
        this.origin = origin;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.putInt("remainingTicks", this.remainingTicks);
    }

    @Override
    protected void defineSynchedData(Builder entityData) {
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

        spamUpdates(this.level(), this.blockPosition(), 2);

        this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        this.move(MoverType.SELF, this.getDeltaMovement());
    }
}
