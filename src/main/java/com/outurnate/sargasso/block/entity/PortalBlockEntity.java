/* (C)2026 */
package com.outurnate.sargasso.block.entity;

import com.outurnate.sargasso.registry.LocalBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PortalBlockEntity extends BlockEntity {
    public PortalBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.PORTAL.get(), worldPosition, blockState);
    }
}
