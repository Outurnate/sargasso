package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.registry.LocalEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.joml.Vector3f;

public class RedstoneBug extends Entity implements IEntityWithComplexSpawn {
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
    public Vector3f origin = new Vector3f();
    public long seed = 0;

    public RedstoneBug(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public RedstoneBug(Level level) {
        super(LocalEntities.REDSTONE_BUG.get(), level);
        this.noPhysics = true;
    }

    public RedstoneBug(Level level, int remainingTicks, Vector3f origin) {
        super(LocalEntities.REDSTONE_BUG.get(), level);
        this.remainingTicks = remainingTicks;
        this.origin = origin;
        this.noPhysics = true;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.putInt("remainingTicks", this.remainingTicks);
        output.putFloat("originX", origin.x);
        output.putFloat("originY", origin.y);
        output.putFloat("originZ", origin.z);
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
        this.origin = new Vector3f(
            input.getFloatOr("originX", 0.0F),
            input.getFloatOr("originY", 0.0F),
            input.getFloatOr("originZ", 0.0F));
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        this.origin = additionalData.readVector3f();
    }

    @Override
    public void tick() {
        super.tick();
        --remainingTicks;
        if (remainingTicks <= 0) {
            this.remove(RemovalReason.KILLED);
        }

        if ((level().getGameTime() % 3) == 0) {
            this.seed = level().getRandom().nextLong();
        }

        spamUpdates(this.level(), this.blockPosition(), 2);

        this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        buffer.writeVector3f(origin);
    }
}
