package com.outurnate.sargasso.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class HammerArmPoseTransformer implements IArmPoseTransformer {
    @Override
    public void applyTransform(HumanoidModel<?> model, HumanoidRenderState entity, HumanoidArm arm) {
    }
}
