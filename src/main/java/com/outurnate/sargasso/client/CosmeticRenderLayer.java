package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.data.Cosmetic;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CosmeticRenderLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>>
    extends RenderLayer<S, M> {
    private final ItemModelResolver itemModelResolver;

    public CosmeticRenderLayer(RenderLayerParent<S, M> renderer, ItemModelResolver itemModelResolver) {
        super(renderer);
        this.itemModelResolver = itemModelResolver;
    }

    @Override
    public void submit(
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords,
        S state,
        float yRot,
        float xRot) {
        ItemStack helmet = state.headEquipment;

        if (helmet.get(LocalDataComponentTypes.COSMETIC_ITEM) instanceof Cosmetic cosmetic) {
            ItemStack cosmeticItemStack = cosmetic.cosmetic().create();
            poseStack.pushPose();
            getParentModel().head.translateAndRotate(poseStack);
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            itemModelResolver
                .updateForLiving(itemStackRenderState, cosmeticItemStack, ItemDisplayContext.HEAD, null);
            itemStackRenderState.submit(
                poseStack,
                submitNodeCollector,
                lightCoords,
                OverlayTexture.NO_OVERLAY,
                state.outlineColor);
            poseStack.popPose();
        }
    }
}
