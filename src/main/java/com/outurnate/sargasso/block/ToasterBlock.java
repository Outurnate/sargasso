/* (C)2026 */
package com.outurnate.sargasso.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ToasterBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 5.5, 12.0, 6.0, 10.5);

    public ToasterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context) {
        return SHAPE;
    }
}
