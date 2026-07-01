/* (C)2026 */
package com.outurnate.sargasso.block;

import com.mojang.serialization.MapCodec;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.network.chat.LocalizedDurationContents;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;
import com.outurnate.sargasso.registry.LocalSoundEvents;
import com.outurnate.sargasso.registry.LocalTags;
import java.time.Instant;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public class ToasterBlock extends Block {
    public static final MapCodec<ToasterBlock> CODEC = simpleCodec(ToasterBlock::new);
    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 5.0, 12.0, 6.0, 10.0);

    @SubscribeEvent
    public static void onEntityFinishUsing(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().is(LocalTags.BREAD) && event.getEntity() instanceof ServerPlayer player) {
            player.setData(LocalAttachmentTypes.BREAD_EATEN, Instant.now());
        }
    }

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

    private void timeTravelMakeToast(Player player, Level level) {
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            Component message;
            if (serverPlayer.hasData(LocalAttachmentTypes.BREAD_EATEN)) {
                message = Component.translatable(
                    "sargasso.lore.toast",
                    LocalizedDurationContents
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
