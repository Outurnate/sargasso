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
package com.outurnate.sargasso.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.entity.SizeRayBeam;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.jspecify.annotations.NonNull;

public class SizeRayBeamRenderer extends EntityRenderer<SizeRayBeam, EntityRenderState> {
	public SizeRayBeamRenderer(Context context) {
		super(context);
	}

	@Override
	public @NonNull EntityRenderState createRenderState() {
		return new EntityRenderState();
	}

	@Override
	public void submit(
			@NonNull EntityRenderState state,
			@NonNull PoseStack poseStack,
			@NonNull SubmitNodeCollector submitNodeCollector,
			@NonNull CameraRenderState camera) {}
}
