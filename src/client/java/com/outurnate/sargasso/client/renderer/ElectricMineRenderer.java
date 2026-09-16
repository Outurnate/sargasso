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
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.client.model.ElectricMineModel;
import com.outurnate.sargasso.entity.ElectricMine;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class ElectricMineRenderer extends EntityRenderer<ElectricMine, EntityRenderState> {
	private static final Identifier ELECTRIC_MINE_LOCATION = SuperSargassoSea
			.ID("textures/entity/electric_mine.png");
	private final ElectricMineModel model;

	public ElectricMineRenderer(Context context) {
		super(context);
		this.model = new ElectricMineModel(context.bakeLayer(ElectricMineModel.LAYER_LOCATION));
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
			@NonNull CameraRenderState camera) {
		super.submit(state, poseStack, submitNodeCollector, camera);
		submitNodeCollector.submitModel(
				this.model,
				state,
				poseStack,
				ELECTRIC_MINE_LOCATION,
				state.lightCoords,
				OverlayTexture.NO_OVERLAY,
				state.outlineColor,
				null);
	}
}
