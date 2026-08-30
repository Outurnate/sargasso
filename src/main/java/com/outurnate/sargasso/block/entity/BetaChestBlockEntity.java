package com.outurnate.sargasso.block.entity;

import com.outurnate.sargasso.registry.LocalBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BetaChestBlockEntity extends ChestBlockEntity {
    public BetaChestBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.BETA_CHEST.get(), worldPosition, blockState);
    }
}
