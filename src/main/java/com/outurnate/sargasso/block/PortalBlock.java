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
package com.outurnate.sargasso.block;

import com.outurnate.sargasso.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class PortalBlock extends Block {
	public PortalBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, RandomSource random) {
		if (random.nextInt(2) == 0) {
			Vec3 randPos = pos.getCenter()
					.add(random.nextGaussian() / 2.0, random.nextGaussian() / 2.0, random.nextGaussian() / 2.0);
			level.addParticle(
					ParticleTypes.END_ROD,
					randPos.x,
					randPos.y,
					randPos.z,
					random.nextGaussian() * 0.005,
					random.nextGaussian() * 0.005,
					random.nextGaussian() * 0.005);
		}
	}

	@Override
	protected void entityInside(
			@NonNull BlockState state,
			@NonNull Level level,
			@NonNull BlockPos pos,
			@NonNull Entity entity,
			@NonNull InsideBlockEffectApplier effectApplier,
			boolean isPrecise) {
		if (entity instanceof ServerPlayer player && player.canUsePortal(false)) {
			Utils.sendToSea(player);
		}
	}

	@Override
	protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
		return RenderShape.INVISIBLE;
	}
}
