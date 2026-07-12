package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.registry.LocalStandaloneModels;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;

public class HeadGearRenderLayer
    extends RenderLayer<AvatarRenderState, PlayerModel> {
    public HeadGearRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer) {
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
        BlockStateModelPart model = Minecraft.getInstance().getModelManager()
            .getStandaloneModel(LocalStandaloneModels.FOX_EARS);
        if (model != null) {
            submitNodeCollector.submitBlockModel(
                poseStack,
                RenderTypes.entitySolid(TextureAtlas.LOCATION_BLOCKS),
                List.of(model),
                BlockModelRenderState.EMPTY_TINTS,
                lightCoords,
                OverlayTexture.NO_OVERLAY,
                0);
        }
    }
}
