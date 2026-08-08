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
        float mainArmX = -Mth.HALF_PI;
        float mainArmY = Mth.HALF_PI / 2;
        float mainArmZ = Mth.HALF_PI;
        float secondArmX = -Mth.HALF_PI / 2;
        float secondArmY = 0;
        float secondArmZ = 0;
        if (arm.compareTo(HumanoidArm.RIGHT) == 0) {
            model.rightArm.xRot = mainArmX;
            model.rightArm.yRot = mainArmY;
            model.rightArm.zRot = mainArmZ;
            model.leftArm.xRot = secondArmX;
            model.leftArm.yRot = secondArmY;
            model.leftArm.zRot = secondArmZ;
            entity.leftHandItemState.clear();
        } else if (arm.compareTo(HumanoidArm.LEFT) == 0) {
            model.leftArm.xRot = mainArmX;
            model.leftArm.yRot = mainArmY;
            model.leftArm.zRot = -mainArmZ;
            model.rightArm.xRot = secondArmX;
            model.rightArm.yRot = secondArmY;
            model.rightArm.zRot = secondArmZ;
            entity.rightHandItemState.clear();
        }
    }
}
