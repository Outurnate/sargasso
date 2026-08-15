package com.outurnate.sargasso.block.entity;

import com.outurnate.sargasso.registry.LocalBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedStarMetalBlockEntity extends BlockEntity {
    public ReinforcedStarMetalBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.REINFORCED_STAR_METAL.get(), worldPosition, blockState);
    }
}
