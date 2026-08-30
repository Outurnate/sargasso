package com.outurnate.sargasso.block;

import com.outurnate.sargasso.block.entity.BetaChestBlockEntity;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BetaChestBlock extends ChestBlock {
    public BetaChestBlock(
        Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityType,
        SoundEvent openSound,
        SoundEvent closeSound,
        Properties properties) {
        super(blockEntityType, openSound, closeSound, properties);
    }

    @Override
    public ItemStack getCloneItemStack(
        LevelReader level,
        BlockPos pos,
        BlockState state,
        boolean includeData,
        Player player) {
        return Blocks.CHEST.asItem().getDefaultInstance();
    }

    @Override
    protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new BetaChestBlockEntity(worldPosition, blockState);
    }
}
