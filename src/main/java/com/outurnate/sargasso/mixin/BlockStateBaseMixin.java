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
    private static int sargasso$getBugs(BlockGetter level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            List<RedstoneBug> bugs = serverLevel
                .getEntitiesOfClass(RedstoneBug.class, new AABB(pos));
            // we filter out dead bugs here
            // this is because bugs fire one last
            // update on death - this is so that
            // redstone blocks don't end up stuck
            // in a powered state await a block
            // update
            if (bugs.stream().filter(bug -> bug.getRemovalReason() == null).count() > 0) {
                // the first bug might be dead, but whatevs
                return bugs.get(0).reversePolarity ? Redstone.SIGNAL_NONE : Redstone.SIGNAL_MAX;
            }
        }
        return -1;
    }

    @Inject(method = "getDirectSignal", at = @At("HEAD"), cancellable = true)
    private void sargasso$getDirectSignal(
        BlockGetter level,
        BlockPos pos,
        Direction direction,
        CallbackInfoReturnable<Integer> callbackInfo) {
        int bugVal = sargasso$getBugs(level, pos);
        if (bugVal >= 0) {
            callbackInfo.setReturnValue(bugVal);
        }
    }

    @Inject(method = "getSignal", at = @At("HEAD"), cancellable = true)
    private void sargasso$getSignal(
        BlockGetter level,
        BlockPos pos,
        Direction direction,
        CallbackInfoReturnable<Integer> callbackInfo) {
        int bugVal = sargasso$getBugs(level, pos);
        if (bugVal >= 0) {
            callbackInfo.setReturnValue(bugVal);
        }
    }
}

// -2.5 -59.0 -26.5
// -2   -59   -26   | -1 -58 -25