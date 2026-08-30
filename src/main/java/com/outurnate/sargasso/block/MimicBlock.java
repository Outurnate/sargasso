package com.outurnate.sargasso.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MimicBlock extends Block {
    private final Block copyBlock;

    public MimicBlock(Block copyBlock, Properties properties) {
        super(properties);
        this.copyBlock = copyBlock;
    }

    @Override
    public ItemStack getCloneItemStack(
        LevelReader level,
        BlockPos pos,
        BlockState state,
        boolean includeData,
        Player player) {
        return copyBlock.asItem().getDefaultInstance();
    }
}
