package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.entity.RedstoneBug;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneWireBlockMixin {
    @Inject(method = "getSignal", at = @At("HEAD"), cancellable = true)
    private void sargasso$getSignal(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        Direction direction,
        CallbackInfoReturnable<Integer> callbackInfo) {
        if (level instanceof ServerLevel serverLevel) {
            List<RedstoneBug> bugs = serverLevel
                .getEntitiesOfClass(RedstoneBug.class, AABB.unitCubeFromLowerCorner(new Vec3(pos)));
            if (bugs.size() > 0) {
                // callbackInfo.setReturnValue(Redstone.SIGNAL_MAX);
            }
        }
    }
}
