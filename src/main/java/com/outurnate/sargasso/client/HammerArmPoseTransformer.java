package com.outurnate.sargasso.client;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class HammerArmPoseTransformer implements IArmPoseTransformer {
    public static final EnumProxy<ArmPose> PROXY = new EnumProxy<ArmPose>(
        ArmPose.class,
        true,
        true,
        new HammerArmPoseTransformer());

    public HammerArmPoseTransformer() {
        SuperSargassoSea.LOGGER.error("butt");
    }

    @Override
    public void applyTransform(HumanoidModel<?> model, HumanoidRenderState entity, HumanoidArm arm) {
    }
}
