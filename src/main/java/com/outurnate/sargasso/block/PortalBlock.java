/* (C)2026 */
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

public class PortalBlock extends Block {
    public PortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
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
        BlockState state,
        Level level,
        BlockPos pos,
        Entity entity,
        InsideBlockEffectApplier effectApplier,
        boolean isPrecise) {
        if (entity instanceof ServerPlayer player && player.canUsePortal(false)) {
            Utils.sendToSea(player);
        }
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
