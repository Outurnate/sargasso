package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

public class HeadGearRenderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    public HeadGearRenderLayer(
        RenderLayerParent<AvatarRenderState, PlayerModel> renderer,
        EntityModelSet entityModelSet) {
        super(renderer);
    }

    @Override
    public void submit(
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords,
        AvatarRenderState state,
        float yRot,
        float xRot) {
    }
}
