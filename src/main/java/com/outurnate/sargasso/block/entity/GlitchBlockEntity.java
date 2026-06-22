/* (C)2026 */
package com.outurnate.sargasso.block.entity;

import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalItems;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GlitchBlockEntity extends BlockEntity {
    private static final WeightedList<Function<Level, Entity>> entities = WeightedList.of(
        new Weighted<>((level) -> EntityType.ARROW.create(level, EntitySpawnReason.SPAWNER), 10),
        new Weighted<>((level) -> EntityType.SPLASH_POTION.create(level, EntitySpawnReason.SPAWNER), 10),
        new Weighted<>(
            (level) -> new ItemEntity(level, 0.0, 0.0, 0.0, new ItemStack(LocalItems.JUNK.get(), 1)),
            1));

    public GlitchBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.GLITCH.get(), worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        RandomSource rand = level.getRandom();
        Entity proj = entities.getRandom(level.getRandom()).get().apply(level);
        if (proj != null) {
            float speed = 1.0F;
            float yRot = rand.nextFloat() * 360.0F;
            float xRot = (rand.nextFloat() * 180.0F) + 180.0F;
            float xd = -Mth.sin(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
            float yd = -Mth.sin(xRot * Mth.DEG_TO_RAD);
            float zd = Mth.cos(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
            Vec3 movement = new Vec3(xd, yd, zd).normalize().scale(speed);
            proj.setDeltaMovement(movement);
            proj.needsSync = true;
            proj.setYRot((float) (Mth.atan2(movement.x, movement.z) * Mth.RAD_TO_DEG));
            proj.setXRot(
                (float) (Mth.atan2(movement.y, movement.horizontalDistance()) * Mth.RAD_TO_DEG));
            proj.yRotO = proj.getYRot();
            proj.xRotO = proj.getXRot();
            proj.setPos(pos.getCenter());
            level.addFreshEntity(proj);
        }
    }
}
