package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.registry.LocalStandaloneModels;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeadGearRenderLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>>
    extends RenderLayer<S, M> {
    public HeadGearRenderLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
    }

    @Override
    public void submit(
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords,
        S state,
        float yRot,
        float xRot) {
        BlockStateModelPart model = Minecraft.getInstance().getModelManager()
            .getStandaloneModel(LocalStandaloneModels.FOX_EARS);
        if (model != null) {
            poseStack.pushPose();
            this.getParentModel().head.translateAndRotate(poseStack);
            submitNodeCollector.submitBlockModel(
                poseStack,
                RenderTypes.entitySolid(TextureAtlas.LOCATION_BLOCKS),
                List.of(model),
                BlockModelRenderState.EMPTY_TINTS,
                lightCoords,
                OverlayTexture.NO_OVERLAY,
                0);
            poseStack.popPose();
        }
    }
}
