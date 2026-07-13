package com.outurnate.sargasso.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalStandaloneModels;
import java.util.List;
import java.util.Map;

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
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

@OnlyIn(Dist.CLIENT)
public class HeadGearRenderLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>>
    extends RenderLayer<S, M> {
    private static final Map<Item, StandaloneModelKey<BlockStateModelPart>> hats = ImmutableMap
        .<Item, StandaloneModelKey<BlockStateModelPart>>builder()
        .put(LocalItems.BLACK_FOX_EARS.get(), LocalStandaloneModels.BLACK_FOX_EARS)
        .put(LocalItems.TWO_COLOR_FOX_EARS.get(), LocalStandaloneModels.TWO_COLOR_FOX_EARS)
        .build();

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
        StandaloneModelKey<BlockStateModelPart> modelKey = hats.get(state.headEquipment.getItem());
        if (modelKey != null) {
            BlockStateModelPart model = Minecraft.getInstance().getModelManager()
                .getStandaloneModel(modelKey);
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
}
