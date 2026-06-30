/* (C)2026 */
package com.outurnate.sargasso.block;

import com.outurnate.sargasso.Utils;
import com.outurnate.sargasso.block.entity.PortalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class PortalBlock extends Block implements EntityBlock {
    public PortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(
        BlockState state,
        Level level,
        BlockPos pos,
        Entity entity,
        InsideBlockEffectApplier effectApplier,
        boolean isPrecise) {
        if (entity instanceof ServerPlayer player && player.canUsePortal(false)) {
            Utils.sendToSea(player);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new PortalBlockEntity(worldPosition, blockState);
    }
}
