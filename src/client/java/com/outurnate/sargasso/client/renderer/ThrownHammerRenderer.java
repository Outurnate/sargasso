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
import com.mojang.math.Axis;
import com.outurnate.sargasso.client.renderer.state.ThrownHammerRenderState;
import com.outurnate.sargasso.entity.ThrownHammer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import org.jspecify.annotations.NonNull;

public class ThrownHammerRenderer extends EntityRenderer<ThrownHammer, ThrownHammerRenderState> {
	public ThrownHammerRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public @NonNull ThrownHammerRenderState createRenderState() {
		return new ThrownHammerRenderState();
	}

	@Override
	public void extractRenderState(@NonNull ThrownHammer entity, @NonNull ThrownHammerRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.yRot = entity.getYRot(partialTicks);
		state.xRot = entity.getXRot(partialTicks);
		Minecraft.getInstance().getItemModelResolver()
				.updateForNonLiving(state.self, entity.getDefaultPickupItem(), ItemDisplayContext.NONE, entity);
	}

	@Override
	public void submit(
			ThrownHammerRenderState state,
			PoseStack poseStack,
			@NonNull SubmitNodeCollector submitNodeCollector,
			@NonNull CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(state.xRot - 90.0F));
		poseStack.translate(0.0F, -1.5F, 0.0F);
		state.self.submit(
				poseStack,
				submitNodeCollector,
				state.lightCoords,
				OverlayTexture.NO_OVERLAY,
				state.outlineColor);
		poseStack.popPose();
		super.submit(state, poseStack, submitNodeCollector, camera);
	}
}
