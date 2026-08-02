package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownRedstoneEMP extends ThrowableItemProjectile {
    private static final double MAX_VEL_IN_AXIS = 0.5;

    public ThrownRedstoneEMP(EntityType<? extends ThrownRedstoneEMP> type, Level level) {
        super(type, level);
    }

    public ThrownRedstoneEMP(Level level, double x, double y, double z, ItemStack itemStack) {
        super(LocalEntities.REDSTONE_EMP.get(), x, y, z, level, itemStack);
    }

    public ThrownRedstoneEMP(Level level, LivingEntity mob, ItemStack itemStack) {
        super(LocalEntities.REDSTONE_EMP.get(), mob, level, itemStack);
    }

    @Override
    protected Item getDefaultItem() {
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
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            RandomSource rand = this.level().getRandom();
            Vec3 impact = this.getPosition(1.0F);
            for (int i = 0; i < 15; ++i) {
                RedstoneBug bug = new RedstoneBug(this.level(), 10 * 20, impact.toVector3f());
                if (bug != null) {
                    bug.setPos(impact);
                    bug.setDeltaMovement(
                        (rand.nextDouble() * 2.0 * MAX_VEL_IN_AXIS) - MAX_VEL_IN_AXIS,
                        (rand.nextDouble() * 2.0 * MAX_VEL_IN_AXIS) - MAX_VEL_IN_AXIS,
                        (rand.nextDouble() * 2.0 * MAX_VEL_IN_AXIS) - MAX_VEL_IN_AXIS);
                    this.level().addFreshEntity(bug);
                }
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}
