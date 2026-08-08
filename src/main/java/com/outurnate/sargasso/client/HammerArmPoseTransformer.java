package com.outurnate.sargasso.client;

import com.outurnate.sargasso.SuperSargassoSea;

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
        float mainArmX;
        float mainArmY;
        float mainArmZ;
        float secondArmX;
        float secondArmY;
        float secondArmZ;
        SuperSargassoSea.LOGGER.error("t" + entity.attackTime);
        if (!(entity.attackTime <= 0.0F)) {
            mainArmX = Mth.HALF_PI * 0.75F;
            mainArmY = 0;
            mainArmZ = 0;
            secondArmX = 0;
            secondArmY = 0;
            secondArmZ = 0;
        } else {
            mainArmX = -Mth.HALF_PI;
            mainArmY = -Mth.HALF_PI / 2;
            mainArmZ = Mth.HALF_PI;
            secondArmX = -Mth.HALF_PI / 2;
            secondArmY = 0;
            secondArmZ = 0;
        }
        // entity.attackArm == arm
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
            model.leftArm.yRot = -mainArmY;
            model.leftArm.zRot = -mainArmZ;
            model.rightArm.xRot = secondArmX;
            model.rightArm.yRot = secondArmY;
            model.rightArm.zRot = secondArmZ;
            entity.rightHandItemState.clear();
        }
    }
}
