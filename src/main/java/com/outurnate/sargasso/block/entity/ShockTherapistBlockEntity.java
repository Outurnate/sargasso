package com.outurnate.sargasso.block.entity;

import com.outurnate.sargasso.registry.LocalBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ShockTherapistBlockEntity extends BlockEntity {
    public ShockTherapistBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.SHOCK_THERAPIST.get(), worldPosition, blockState);
    }

    public void tick(Level levelInner, BlockPos pos, BlockState stateInner) {
    }
}
