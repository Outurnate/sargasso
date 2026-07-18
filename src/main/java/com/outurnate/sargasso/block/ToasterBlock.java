/* (C)2026 */
package com.outurnate.sargasso.block;

import com.mojang.serialization.MapCodec;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.network.chat.DurationContents;
import com.outurnate.sargasso.registry.LocalAdvancements;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;
import com.outurnate.sargasso.registry.LocalSoundEvents;
import com.outurnate.sargasso.registry.LocalTags;
import java.time.Instant;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public class ToasterBlock extends Block {
    public static final MapCodec<ToasterBlock> CODEC = simpleCodec(ToasterBlock::new);
    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
        Direction.NORTH,
        Block.box(4.0, 0.0, 5.0, 12.0, 6.0, 10.0),
        Direction.EAST,
        Block.box(6.0, 0.0, 4.0, 11.0, 6.0, 12.0),
        Direction.SOUTH,
        Block.box(4.0, 0.0, 6.0, 12.0, 6.0, 11.0),
        Direction.WEST,
        Block.box(5.0, 0.0, 4.0, 10.0, 6.0, 12.0));
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    @SubscribeEvent
    public static void onEntityFinishUsing(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().is(LocalTags.BREAD) && event.getEntity() instanceof ServerPlayer player) {
            player.setData(LocalAttachmentTypes.BREAD_EATEN, Instant.now());
        }
    }

    public ToasterBlock(Properties properties) {
        super(properties);
        registerDefaultState(
            stateDefinition.any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public MapCodec<? extends ToasterBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
            .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    private void timeTravelMakeToast(Player player, Level level) {
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            Component message;
            if (serverPlayer.hasData(LocalAttachmentTypes.BREAD_EATEN)) {
                serverPlayer.getAdvancements().award(
                    serverLevel.getServer().getAdvancements()
                        .get(LocalAdvancements.TOAST),
                    "impossible");
                message = Component.translatable(
                    "sargasso.lore.toast",
                    DurationContents
                        .localizedDate(serverPlayer.getData(LocalAttachmentTypes.BREAD_EATEN)));
                serverLevel.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    LocalSoundEvents.TOASTER,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F);
                serverPlayer.removeData(LocalAttachmentTypes.BREAD_EATEN);
            } else {
                message = Component.translatable("sargasso.lore.no_toast");
            }
            serverPlayer.sendSystemMessage(message);
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
        timeTravelMakeToast(player, level);
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    protected InteractionResult useWithoutItem(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        BlockHitResult hitResult) {
        timeTravelMakeToast(player, level);
        return InteractionResult.SUCCESS_SERVER;
    }
}
