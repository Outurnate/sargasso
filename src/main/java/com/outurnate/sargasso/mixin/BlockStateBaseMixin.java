package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.entity.RedstoneBug;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStateBase.class)
public abstract class BlockStateBaseMixin {
    private static boolean sargasso$getBugs(BlockGetter level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            List<RedstoneBug> bugs = serverLevel
                .getEntitiesOfClass(RedstoneBug.class, new AABB(pos));
            return bugs.stream().filter(bug -> bug.getRemovalReason() == null).count() > 0;
        }
        return false;
    }

    @Inject(method = "getDirectSignal", at = @At("HEAD"), cancellable = true)
    private void getDirectSignal(
        BlockGetter level,
        BlockPos pos,
        Direction direction,
        CallbackInfoReturnable<Integer> callbackInfo) {
        if (sargasso$getBugs(level, pos)) {
            callbackInfo.setReturnValue(Redstone.SIGNAL_MAX);
        }
    }

    @Inject(method = "getSignal", at = @At("HEAD"), cancellable = true)
    private void sargasso$getSignal(
        BlockGetter level,
        BlockPos pos,
        Direction direction,
        CallbackInfoReturnable<Integer> callbackInfo) {
        if (sargasso$getBugs(level, pos)) {
            callbackInfo.setReturnValue(Redstone.SIGNAL_MAX);
        }
    }
}

// -2.5 -59.0 -26.5
// -2   -59   -26   | -1 -58 -25