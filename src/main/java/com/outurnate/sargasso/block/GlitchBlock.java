/* (C)2026 */
package com.outurnate.sargasso.block;

import com.outurnate.sargasso.block.entity.GlitchBlockEntity;
import com.outurnate.sargasso.registry.LocalBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class GlitchBlock extends Block implements EntityBlock {
    @SuppressWarnings("unchecked")
    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
        BlockEntityType<A> type,
        BlockEntityType<E> checkedType,
        BlockEntityTicker<? super E> ticker) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    public GlitchBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level level,
        BlockState state,
        BlockEntityType<T> type) {
        return createTickerHelper(
            type,
            LocalBlockEntities.GLITCH.get(),
            (levelInner, pos, stateInner, blockEntity) -> blockEntity.tick(levelInner, pos, stateInner));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new GlitchBlockEntity(worldPosition, blockState);
    }
}
