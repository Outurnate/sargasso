package com.outurnate.sargasso.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
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
        if (arm.compareTo(HumanoidArm.RIGHT) == 0) {
            model.rightArm.xRot = Mth.HALF_PI;
            model.rightArm.yRot = 0;
            model.rightArm.zRot = 0;
            model.leftArm.xRot = 0;
            model.leftArm.yRot = 0;
            model.leftArm.zRot = 0;
        } else if (arm.compareTo(HumanoidArm.LEFT) == 0) {
            model.leftArm.xRot = Mth.HALF_PI;
            model.leftArm.yRot = 0;
            model.leftArm.zRot = 0;
            model.rightArm.xRot = 0;
            model.rightArm.yRot = 0;
            model.rightArm.zRot = 0;
        }
    }
}
