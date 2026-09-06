package com.outurnate.sargasso.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.client.model.SizeRayBeamModel;
import com.outurnate.sargasso.entity.SizeRayBeam;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SizeRayBeamRenderer extends EntityRenderer<SizeRayBeam, EntityRenderState> {
    private static final Identifier BEAM_LOCATION = SuperSargassoSea
        .ID("textures/entity/beam.png");
    private final SizeRayBeamModel model;

    public SizeRayBeamRenderer(Context context) {
        super(context);
        this.model = new SizeRayBeamModel(context.bakeLayer(SizeRayBeamModel.LAYER_LOCATION));
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(
        EntityRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        submitNodeCollector.submitModel(
            this.model,
            state,
            poseStack,
            BEAM_LOCATION,
            state.lightCoords,
            OverlayTexture.NO_OVERLAY,
            state.outlineColor,
            null);
    }
}
