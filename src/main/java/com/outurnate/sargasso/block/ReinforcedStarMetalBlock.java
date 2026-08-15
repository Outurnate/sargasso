package com.outurnate.sargasso.block;

import com.outurnate.sargasso.block.entity.ReinforcedStarMetalBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class ReinforcedStarMetalBlock extends Block implements EntityBlock {
    public ReinforcedStarMetalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new ReinforcedStarMetalBlockEntity(worldPosition, blockState);
    }
}
