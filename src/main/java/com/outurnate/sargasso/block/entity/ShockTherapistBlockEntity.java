package com.outurnate.sargasso.block.entity;

import com.outurnate.sargasso.entity.ElectricMine;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShockTherapistBlockEntity extends BlockEntity {
    public List<ElectricMine> mines = List.of();

    public ShockTherapistBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.SHOCK_THERAPIST.get(), worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        double arcRadius = 8.0;

        mines = level.getEntities(
            EntityTypeTest.forClass(ElectricMine.class),
            AABB.ofSize(pos.getCenter(), 2.0 * arcRadius, 2.0 * arcRadius, 2.0 * arcRadius),
            e -> true);

        if ((level.getGameTime() % (20 * 10)) == 0) {
            RandomSource random = level.getRandom();
            int num = random.nextInt(5, 10);
            for (int i = 0; i < num; ++i) {
                Entity electricMine = new ElectricMine(level);
                electricMine.setPos(pos.getCenter().add(0.0, 1.0, 0.0));

                float yRot = random.nextFloat() * 360.0F;
                float xRot = 225.0F;
                float speed = 0.2F;
                float xd = -Mth.sin(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
                float yd = -Mth.sin(xRot * Mth.DEG_TO_RAD);
                float zd = Mth.cos(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
                Vec3 movement = new Vec3(xd, yd, zd).normalize().scale(speed);
                electricMine.setDeltaMovement(movement);
                level.addFreshEntity(electricMine);
            }
        }
    }
}
