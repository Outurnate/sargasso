package com.outurnate.sargasso.client;

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

    @Override
    public void applyTransform(HumanoidModel<?> model, HumanoidRenderState entity, HumanoidArm arm) {
        model.rightArm.xRot = 4;
        model.rightArm.yRot = 4;
        model.rightArm.zRot = 4;
        model.leftArm.xRot = 4;
        model.leftArm.yRot = 4;
        model.leftArm.zRot = 4;
    }
}
