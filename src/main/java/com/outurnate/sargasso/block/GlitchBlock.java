/* (C)2026 */
package com.outurnate.sargasso.block;

import com.outurnate.sargasso.block.entity.GlitchBlockEntity;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

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
    protected void entityInside(
        BlockState state,
        Level level,
        BlockPos pos,
        Entity entity,
        InsideBlockEffectApplier effectApplier,
        boolean isPrecise) {

        if (entity instanceof LivingEntity livingEntity && level instanceof ServerLevel serverLevel) {
            float diameter = 8.0F;

            double xx = livingEntity.getX() + (livingEntity.getRandom().nextDouble() - 0.5) * diameter;
            double yy = Mth.clamp(
                livingEntity.getY() + (livingEntity.getRandom().nextDouble() - 0.5) * diameter,
                (double) serverLevel.getMinY(),
                (double) (serverLevel.getMinY() + serverLevel.getLogicalHeight() - 1));
            double zz = livingEntity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5) * diameter;
            if (serverLevel.getBlockState(new BlockPos((int) xx, (int) yy, (int) zz))
                .is(LocalBlocks.GLITCH.get())) {
                return;
            }

            if (livingEntity.isPassenger()) {
                livingEntity.stopRiding();
            }

            Vec3 oldPos = livingEntity.position();
            if (livingEntity.randomTeleport(xx, yy, zz, false, ItemStack.EMPTY)) {
                serverLevel.gameEvent(GameEvent.TELEPORT, oldPos, GameEvent.Context.of(livingEntity));
                SoundSource soundSource;
                SoundEvent soundEvent = SoundEvents.CHORUS_FRUIT_TELEPORT; // TODO CUSTOM
                if (livingEntity instanceof Player) {
                    soundSource = SoundSource.PLAYERS;
                } else {
                    soundSource = SoundSource.NEUTRAL;
                }

                serverLevel.playSound(
                    null,
                    livingEntity.getX(),
                    livingEntity.getY(),
                    livingEntity.getZ(),
                    soundEvent,
                    soundSource);
                livingEntity.resetFallDistance();
                livingEntity.resetCurrentImpulseContext();
            }
        }
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
