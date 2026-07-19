package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.entity.ElectricMine;
import com.outurnate.sargasso.entity.ElectricMineRenderState;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.data.AtlasIds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricMineRenderer extends EntityRenderer<ElectricMine, ElectricMineRenderState> {
    private final ElectricMineModel model;

    public ElectricMineRenderer(Context context) {
        super(context);
        this.model = new ElectricMineModel(context.bakeLayer(ElectricMineModel.LAYER_LOCATION));
    }

    @Override
    public ElectricMineRenderState createRenderState() {
        return new ElectricMineRenderState();
    }

    @Override
    public void submit(
        ElectricMineRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        SuperSargassoSea.LOGGER.error("AAAAAAA");
        submitNodeCollector
            .order(1)
            .submitModel(
                this.model,
                state,
                poseStack,
                RenderTypes.entitySolid(AtlasIds.BLOCKS),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0,
                null);
    }
}
