package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.entity.ElectricMineRenderState;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.data.AtlasIds;

public class ElectricMineRenderLayer extends RenderLayer<ElectricMineRenderState, ElectricMineModel> {
    private final ElectricMineModel model;

    public ElectricMineRenderLayer(
        RenderLayerParent<ElectricMineRenderState, ElectricMineModel> renderer,
        EntityModelSet entityModelSet) {
        super(renderer);
        this.model = new ElectricMineModel(entityModelSet.bakeLayer(ElectricMineModel.LAYER_LOCATION));
    }

    @Override
    public void submit(
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords,
        ElectricMineRenderState state,
        float yRot,
        float xRot) {
        submitNodeCollector
            .order(1)
            .submitModel(
                this.model,
                state,
                poseStack,
                RenderTypes.entitySolid(AtlasIds.BLOCKS),
                lightCoords,
                OverlayTexture.NO_OVERLAY,
                0,
                null);
    }
}
