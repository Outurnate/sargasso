/* (C)2026 */
package com.outurnate.sargasso.block.entity;

import com.outurnate.sargasso.Utils;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GlitchBlockEntity extends BlockEntity {
    private static final WeightedList<BiFunction<Level, Vec3, Entity>> entities = buildWeights();

    private static WeightedList<BiFunction<Level, Vec3, Entity>> buildWeights() {
        WeightedList.Builder<BiFunction<Level, Vec3, Entity>> list = WeightedList.builder();
        List<Weighted<Holder.Reference<Potion>>> potions = List.of(
            new Weighted<>(Potions.FIRE_RESISTANCE, 1),
            new Weighted<>(Potions.HARMING, 5),
            new Weighted<>(Potions.HEALING, 1),
            new Weighted<>(Potions.INFESTED, 2),
            new Weighted<>(Potions.INVISIBILITY, 3),
            new Weighted<>(Potions.LEAPING, 4),
            new Weighted<>(Potions.NIGHT_VISION, 2),
            new Weighted<>(Potions.OOZING, 9),
            new Weighted<>(Potions.POISON, 4),
            new Weighted<>(Potions.REGENERATION, 1),
            new Weighted<>(Potions.SLOWNESS, 7),
            new Weighted<>(Potions.SLOW_FALLING, 8),
            new Weighted<>(Potions.STRENGTH, 1),
            new Weighted<>(Potions.SWIFTNESS, 1),
            new Weighted<>(Potions.WATER_BREATHING, 10),
            new Weighted<>(Potions.WEAKNESS, 9),
            new Weighted<>(Potions.WEAVING, 2),
            new Weighted<>(Potions.WIND_CHARGED, 5));
        for (Weighted<Holder.Reference<Potion>> weightedPotion : potions) {
            list.add(weightedPotion.map((potion) -> (level, pos) -> {
                return new ThrownSplashPotion(
                    level,
                    pos.x,
                    pos.y,
                    pos.z,
                    PotionContents.createItemStack(Items.POTION, potion));
            }));
        }
        for (Weighted<Holder.Reference<Potion>> weightedPotion : potions) {
            list.add(weightedPotion.map((potion) -> (level, pos) -> {
                Arrow arrow = new Arrow(level, pos.x, pos.y, pos.z, ItemStack.EMPTY, null);
                for (MobEffectInstance effect : potion.value().getEffects()) {
                    arrow.addEffect(effect);
                }
                return arrow;
            }));
        }
        list.add(
            (
                level,
                pos) -> new ItemEntity(level, pos.x, pos.y, pos.z, new ItemStack(LocalItems.DEBRIS.get(), 1)),
            20);
        list.add(
            (
                level,
                pos) -> new Arrow(level, pos.x, pos.y, pos.z, new ItemStack(Items.ARROW, 1), null),
            100);
        return list.build();
    }

    public GlitchBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.GLITCH.get(), worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        RandomSource rand = level.getRandom();
        if (rand.nextFloat() > 0.9999) {
            List<Direction> exposedDirections = new ArrayList<>();
            if (level.getBlockState(pos.above()).is(Blocks.AIR)) {
                exposedDirections.add(Direction.UP);
            }
            if (level.getBlockState(pos.below()).is(Blocks.AIR)) {
                exposedDirections.add(Direction.DOWN);
            }
            if (level.getBlockState(pos.east()).is(Blocks.AIR)) {
                exposedDirections.add(Direction.EAST);
            }
            if (level.getBlockState(pos.west()).is(Blocks.AIR)) {
                exposedDirections.add(Direction.WEST);
            }
            if (level.getBlockState(pos.north()).is(Blocks.AIR)) {
                exposedDirections.add(Direction.NORTH);
            }
            if (level.getBlockState(pos.south()).is(Blocks.AIR)) {
                exposedDirections.add(Direction.SOUTH);
            }
            if (exposedDirections.size() != 0) {
                Direction chosenDirection = exposedDirections.get(rand.nextInt(exposedDirections.size()));
                Entity proj = entities.getRandom(level.getRandom()).get().apply(level, pos.getCenter());
                if (proj != null) {
                    Utils.VelocityHeading movement = Utils
                        .randomVelInDirection(rand, chosenDirection, 1.0F, 2.0F);
                    proj.setDeltaMovement(movement.velocity());
                    proj.needsSync = true;
                    proj.setYRot(movement.yrot());
                    proj.setXRot(movement.xrot());
                    proj.yRotO = proj.getYRot();
                    proj.xRotO = proj.getXRot();
                    level.addFreshEntity(proj);
                }
            }
        }
    }
}
