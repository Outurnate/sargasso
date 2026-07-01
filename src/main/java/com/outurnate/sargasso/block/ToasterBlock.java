/* (C)2026 */
package com.outurnate.sargasso.block;

import com.mojang.serialization.MapCodec;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ToasterBlock extends Block {
    public static final MapCodec<ToasterBlock> CODEC = simpleCodec(ToasterBlock::new);
    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 5.0, 12.0, 6.0, 10.0);

    public ToasterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends ToasterBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context) {
        return SHAPE;
    }

    private void timeTravelMakeToast(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.hasData(LocalAttachmentTypes.BREAD_EATEN)) {
                serverPlayer.sendSystemMessage(Component.literal("you have eaten bread"));
                serverPlayer.removeData(LocalAttachmentTypes.BREAD_EATEN);
            } else {
                serverPlayer.sendSystemMessage(Component.literal("you haven't eaten bread"));
            }
        }
    }

    @Override
    protected InteractionResult useItemOn(
        ItemStack itemStack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hitResult) {
        timeTravelMakeToast(player);
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    protected InteractionResult useWithoutItem(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        BlockHitResult hitResult) {
        timeTravelMakeToast(player);
        return InteractionResult.SUCCESS_SERVER;
    }
}
