package com.outurnate.sargasso.block.entity;

import com.outurnate.sargasso.entity.ElectricMine;
import com.outurnate.sargasso.registry.LocalBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ShockTherapistBlockEntity extends BlockEntity {
    private static final double COS_FIXED_PITCH = Math.cos(1.5D * Math.PI);
    private static final double SIN_FIXED_PITCH = Math.sin(1.5D * Math.PI);

    public ShockTherapistBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.SHOCK_THERAPIST.get(), worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        Entity electricMine = new ElectricMine(level);
        electricMine.setPos(pos.getCenter());
        RandomSource random = level.getRandom();
        double yawRad = Mth.TWO_PI * random.nextDouble();

        double x = -Math.sin(yawRad) * COS_FIXED_PITCH;
        double y = -SIN_FIXED_PITCH;
        double z = Math.cos(yawRad) * COS_FIXED_PITCH;
        electricMine.setDeltaMovement(x, y, z);
        level.addFreshEntity(electricMine);
    }
}
