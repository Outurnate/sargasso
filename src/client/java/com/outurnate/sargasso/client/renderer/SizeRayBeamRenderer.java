package com.outurnate.sargasso.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.entity.SizeRayBeam;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;

public class SizeRayBeamRenderer extends EntityRenderer<SizeRayBeam, EntityRenderState> {
    public SizeRayBeamRenderer(Context context) {
        super(context);
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
    }
}
