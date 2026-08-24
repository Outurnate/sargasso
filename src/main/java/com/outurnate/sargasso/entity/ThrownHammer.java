package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.registry.LocalDamageTypes;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalSoundEvents;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownHammer extends AbstractArrow {
    private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData
        .defineId(ThrownHammer.class, EntityDataSerializers.BYTE);
    private Vec3 incomingVelocity = Vec3.ZERO;
    private boolean dealtDamage = false;
    public int clientSideReturnHammerTickCount;

    public ThrownHammer(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public ThrownHammer(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(LocalEntities.HAMMER.get(), x, y, z, level, pickupItemStack, pickupItemStack);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
    }

    public ThrownHammer(Level level, LivingEntity mob, ItemStack pickupItemStack) {
        super(LocalEntities.HAMMER.get(), mob, level, pickupItemStack, pickupItemStack);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(ID_LOYALTY, (byte) 0);
    }

    @Override
    protected void doKnockback(LivingEntity mob, DamageSource damageSource) {
        float minimumKnockback = 1.0F;
        double knockback = this.getWeaponItem() != null && this.level() instanceof ServerLevel serverLevel
            ? EnchantmentHelper
                .modifyKnockback(serverLevel, this.getWeaponItem(), mob, damageSource, minimumKnockback)
            : minimumKnockback;
        double knockbackResistance = Math
            .max(0.0, 1.0 - mob.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
        float deflection = random.nextBoolean() ? -Mth.HALF_PI : Mth.HALF_PI;
        Vec3 movement = this.getDeltaMovement().yRot(deflection).multiply(1.0, 0.0, 1.0).normalize()
            .scale(knockback * 0.6 * knockbackResistance);
        if (movement.lengthSqr() > 0.0) {
            mob.push(movement.x, 0.1, movement.z);
            // get the mob out of the way so we fly straight
            while (mob.getBoundingBox().intersects(this.getBoundingBox())) {
                mob.setPos(mob.getX() + movement.x, mob.getY(), mob.getZ() + movement.z);
            }
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return LocalSoundEvents.HAMMER_HIT_GROUND.value();
    }

    @Override
    public ItemStack getDefaultPickupItem() {
        return new ItemStack(LocalItems.HAMMER.get());
    }

    private byte getLoyaltyFromItem(ItemStack hammerItem) {
        return this.level() instanceof ServerLevel serverLevel ? (byte) Mth.clamp(
            EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverLevel, hammerItem, this),
            0,
            127) : 0;
    }

    @Override
    public ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity currentOwner = this.getOwner();
        return currentOwner == null || !currentOwner.isAlive() ? false
            : !(currentOwner instanceof ServerPlayer) || !currentOwner.isSpectator();
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        Vec3 normal = hitResult.getDirection().getUnitVec3();
        double dot = incomingVelocity.dot(normal);
        Vec3 reflected = incomingVelocity.subtract(normal.scale(2.0 * dot));
        setDeltaMovement(reflected.scale(0.1));
        setPos(position().add(normal.scale(0.01)));
        if (incomingVelocity.length() < 0.1) {
            super.onHitBlock(hitResult);
        }
        RandomSource rand = this.level().getRandom();
        ParticleOptions options = new BlockParticleOption(
            ParticleTypes.BLOCK,
            this.level().getBlockState(hitResult.getBlockPos()));
        for (int i = 0; i < 15; ++i) {
            this.level().addParticle(
                options,
                this.getX(),
                this.getY(),
                this.getZ(),
                rand.nextDouble() - 0.5,
                rand.nextDouble() - 0.5,
                rand.nextDouble() - 0.5);
        }
        this.playSound(LocalSoundEvents.HAMMER_HIT.value(), 1.0F, 1.0F);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        float dmg = 8.0F;
        Entity currentOwner = this.getOwner();
        Registry<DamageType> damageTypes = level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE);
        DamageSource damageSource = new DamageSource(
            damageTypes.getOrThrow(LocalDamageTypes.HAMMER),
            (Entity) (currentOwner == null ? this : currentOwner));
        if (this.level() instanceof ServerLevel serverLevel) {
            dmg = EnchantmentHelper
                .modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, dmg);
        }

        this.dealtDamage = true;
        if (entity.hurtOrSimulate(damageSource, dmg)) {
            if (entity.is(EntityType.ENDERMAN)) {
                return;
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffectsWithItemSourceOnBreak(
                    serverLevel,
                    entity,
                    damageSource,
                    this.getWeaponItem(),
                    weapon -> this.kill(serverLevel));
            }

            if (entity instanceof LivingEntity mob) {
                this.doKnockback(mob, damageSource);
                this.doPostHurtEffects(mob);
            }
        }

        // this.deflect(ProjectileDeflection.REVERSE, entity, this.owner, false);
        // this.setDeltaMovement(this.getDeltaMovement().multiply(0.02, 0.2, 0.02));
        this.playSound(LocalSoundEvents.HAMMER_HIT.value(), 1.0F, 1.0F);
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.dealtDamage = input.getBooleanOr("DealtDamage", false);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(this.getPickupItemStackOrigin()));
    }

    @Override
    public boolean shouldRender(double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void tick() {
        incomingVelocity = getDeltaMovement();

        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity currentOwner = this.getOwner();
        int loyalty = this.entityData.get(ID_LOYALTY);
        if (loyalty > 0 && (this.dealtDamage || this.isNoPhysics()) && currentOwner != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (this.level() instanceof ServerLevel level
                    && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(level, this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                if (!(currentOwner instanceof Player) && this.position()
                    .distanceTo(currentOwner.getEyePosition()) < currentOwner.getBbWidth() + 1.0) {
                    this.discard();
                    return;
                }

                this.setNoPhysics(true);
                Vec3 vec = currentOwner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec.y * 0.015 * loyalty, this.getZ());
                double accel = 0.05 * loyalty;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec.normalize().scale(accel)));
                if (this.clientSideReturnHammerTickCount == 0) {
                    this.playSound(LocalSoundEvents.HAMMER_RETURN.value(), 10.0F, 1.0F);
                }

                this.clientSideReturnHammerTickCount++;
            }
        }

        super.tick();
    }

    @Override
    public void tickDespawn() {
        int loyalty = this.entityData.get(ID_LOYALTY);
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || loyalty <= 0) {
            super.tickDespawn();
        }
    }

    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player)
            || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }
}
