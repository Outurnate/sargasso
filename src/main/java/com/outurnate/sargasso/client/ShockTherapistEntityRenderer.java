package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;

public class ShockTherapistEntityRenderer
    implements BlockEntityRenderer<ShockTherapistBlockEntity, ShockTherapistRenderState> {
    public ShockTherapistEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public ShockTherapistRenderState createRenderState() {
        return new ShockTherapistRenderState();
    }

    @Override
    public void submit(
        ShockTherapistRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
    }
}
