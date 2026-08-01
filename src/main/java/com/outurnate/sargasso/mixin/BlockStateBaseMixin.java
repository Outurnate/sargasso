package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.SuperSargassoSea;
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
    @Inject(method = "getSignal", at = @At("HEAD"), cancellable = true)
    private void sargasso$getSignal(
        BlockGetter level,
        BlockPos pos,
        Direction direction,
        CallbackInfoReturnable<Integer> callbackInfo) {
        if (level instanceof ServerLevel serverLevel) {
            SuperSargassoSea.LOGGER
                .error("MIXIN AT " + (int) pos.getX() + "," + (int) pos.getY() + "," + (int) pos.getZ());
            List<RedstoneBug> bugs = serverLevel
                .getEntitiesOfClass(RedstoneBug.class, new AABB(pos));
            if (bugs.size() > 0) {
                callbackInfo.setReturnValue(Redstone.SIGNAL_MAX);
            }
        }
    }
}
