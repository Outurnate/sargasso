/* (C)2026 */
package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownBottleOfSpiders extends ThrowableItemProjectile {
    public ThrownBottleOfSpiders(EntityType<ThrownBottleOfSpiders> type, Level level) {
        super(type, level);
    }

    public ThrownBottleOfSpiders(Level level, double x, double y, double z, ItemStack itemStack) {
        super(LocalEntities.SPIDER_BOTTLE.get(), x, y, z, level, itemStack);
    }

    public ThrownBottleOfSpiders(Level level, LivingEntity mob, ItemStack itemStack) {
        super(LocalEntities.SPIDER_BOTTLE.get(), mob, level, itemStack);
    }

    @Override
    protected Item getDefaultItem() {
        return LocalItems.SPIDER_BOTTLE.get();
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
            Identifier permanent = SuperSargassoSea.ID("permanent");
            RandomSource random = level().getRandom();
            int numSpiders = random.nextInt(5, 10);
            for (int i = 0; i < numSpiders; ++i) {
                Spider spider = EntityType.SPIDER.create(this.level(), EntitySpawnReason.TRIGGERED);
                if (spider != null) {
                    spider.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                    spider.setDeltaMovement(
                        (random.nextDouble() - 0.5) / 4,
                        0.0,
                        (random.nextDouble() - 0.5) / 4);
                    spider.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(
                        new AttributeModifier(permanent, -0.99, Operation.ADD_MULTIPLIED_TOTAL));
                    spider.getAttribute(Attributes.SCALE).addPermanentModifier(
                        new AttributeModifier(permanent, -0.8, Operation.ADD_MULTIPLIED_TOTAL));
                    this.level().addFreshEntity(spider);
                }
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}
