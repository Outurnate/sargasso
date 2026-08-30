package com.outurnate.sargasso.block;

import com.outurnate.sargasso.block.entity.BetaChestBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BetaChestBlock extends ChestBlock {
    public BetaChestBlock(
        Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityType,
        SoundEvent openSound,
        SoundEvent closeSound,
        Properties properties) {
        super(blockEntityType, openSound, closeSound, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new BetaChestBlockEntity(worldPosition, blockState);
    }
}
