package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.entity.ElectricMine;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricMineRenderer extends EntityRenderer<ElectricMine, ElectricMineRenderState> {
    private static final Identifier ELECTRIC_MINE_LOCATION = SuperSargassoSea
        .ID("textures/entity/electric_mine.png");
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
