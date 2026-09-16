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
import com.outurnate.sargasso.client.LocalRenderTypes;
import com.outurnate.sargasso.client.model.ElectricArc;
import com.outurnate.sargasso.client.renderer.state.RedstoneBugRenderState;
import com.outurnate.sargasso.entity.RedstoneBug;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

public class RedstoneBugRenderer extends EntityRenderer<RedstoneBug, RedstoneBugRenderState> {
	public RedstoneBugRenderer(Context context) {
		super(context);
	}

	@Override
	public @NonNull RedstoneBugRenderState createRenderState() {
		return new RedstoneBugRenderState();
	}

	@Override
	public void extractRenderState(@NonNull RedstoneBug entity, @NonNull RedstoneBugRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.bolt = new ElectricArc(
				entity.getPosition(partialTicks).toVector3f().mul(-1.0F).add(entity.origin),
				new Vector3f(),
				entity.seed,
				3,
				0.5F,
				2.0F,
				LocalRenderTypes.REDSTONE_ZAP);
	}

	protected @NonNull AABB getBoundingBoxForCulling(RedstoneBug entity) {
		return new AABB(new Vec3(entity.origin), entity.getPosition(1.0F));
	}

	@Override
	public void submit(
			RedstoneBugRenderState state,
			@NonNull PoseStack poseStack,
			@NonNull SubmitNodeCollector submitNodeCollector,
			@NonNull CameraRenderState camera) {
		state.bolt.submit(poseStack, submitNodeCollector);
	}
}
