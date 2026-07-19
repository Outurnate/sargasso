/* (C)2026 */
package com.outurnate.sargasso.client;

import java.util.EnumSet;
import java.util.Set;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlitchBlockRenderState extends BlockEntityRenderState {
    public final Set<Direction> facesToShow = EnumSet.noneOf(Direction.class);
}
