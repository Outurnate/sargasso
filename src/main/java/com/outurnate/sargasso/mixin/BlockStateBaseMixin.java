/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStateBase.class)
public abstract class BlockStateBaseMixin {
	@Unique private static boolean sargasso$getBugs(BlockGetter level, BlockPos pos) {
		if (level instanceof ServerLevel serverLevel) {
			List<RedstoneBug> bugs = serverLevel
					.getEntitiesOfClass(RedstoneBug.class, new AABB(pos));
			// we filter out dead bugs here
			// this is because bugs fire one last
			// update on death - this is so that
			// redstone blocks don't end up stuck
			// in a powered state await a block
			// update
			return bugs.stream().anyMatch(bug -> bug.getRemovalReason() == null);
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
