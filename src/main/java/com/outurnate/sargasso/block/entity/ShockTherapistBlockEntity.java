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
    public ShockTherapistBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.SHOCK_THERAPIST.get(), worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        Entity electricMine = new ElectricMine(level);
        electricMine.setPos(pos.getCenter());
        RandomSource random = level.getRandom();
        float theta = random.nextFloat() * Mth.TWO_PI;
        float speed = random.nextFloat() + 0.5F;
        electricMine.setDeltaMovement(speed + Mth.cos(theta), 0.5F, speed + Mth.sin(theta));
        level.addFreshEntity(electricMine);
    }
}
