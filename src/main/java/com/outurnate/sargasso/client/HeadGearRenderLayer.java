package com.outurnate.sargasso.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

public class HeadGearRenderLayer
    extends HumanoidArmorLayer<AvatarRenderState, PlayerModel, HumanoidModel<AvatarRenderState>> {
    public HeadGearRenderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer) {
        super(renderer, null, null);
    }
}
