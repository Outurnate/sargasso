package com.outurnate.sargasso.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.registry.LocalBlocks;
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
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import net.neoforged.neoforge.registries.DeferredBlock;

@OnlyIn(Dist.CLIENT)
public class HeadGearRenderLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>>
    extends RenderLayer<S, M> {
    private static final Map<Item, StandaloneModelKey<BlockStateModelPart>> objHats = ImmutableMap
        .<Item, StandaloneModelKey<BlockStateModelPart>>builder()
        .put(LocalItems.BLACK_FOX_EARS.get(), LocalStandaloneModels.BLACK_FOX_EARS)
        .put(LocalItems.TWO_COLOR_FOX_EARS.get(), LocalStandaloneModels.TWO_COLOR_FOX_EARS)
        .put(LocalItems.COMICALLY_TALL_FOX_EARS.get(), LocalStandaloneModels.COMICALLY_TALL_FOX_EARS)
        .put(LocalItems.ORANGE_FOX_EARS.get(), LocalStandaloneModels.ORANGE_FOX_EARS)
        .build();
    private static final Map<Item, DeferredBlock<Block>> blockHats = ImmutableMap
        .<Item, DeferredBlock<Block>>builder()
        .put(LocalItems.PYLON.get(), LocalBlocks.PYLON)
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
        StandaloneModelKey<BlockStateModelPart> objModelKey = objHats.get(state.headEquipment.getItem());
        if (objModelKey != null) {
            BlockStateModelPart model = Minecraft.getInstance().getModelManager()
                .getStandaloneModel(objModelKey);
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
        /*
         * DeferredBlock<Block> blockModelKey =
         * blockHats.get(state.headEquipment.getItem()); if (blockModelKey != null) {
         * BlockStateModel model = Minecraft .getInstance() .getModelManager()
         * .getBlockStateModelSet() .get(blockModelKey.get().defaultBlockState()); if
         * (model != null) { poseStack.pushPose();
         * this.getParentModel().head.translateAndRotate(poseStack);
         * submitNodeCollector.submitBlockModel( poseStack,
         * RenderTypes.entitySolid(TextureAtlas.LOCATION_BLOCKS), List.of(model),
         * BlockModelRenderState.EMPTY_TINTS, lightCoords, OverlayTexture.NO_OVERLAY,
         * 0); } }
         */
    }
}
