package com.outurnate.sargasso;

import com.outurnate.sargasso.client.HammerArmPoseTransformer;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class EnumExtensions {
    public static final EnumProxy<ArmPose> SARGASSO_HAMMER = new EnumProxy<ArmPose>(
        ArmPose.class,
        true,
        true,
        new HammerArmPoseTransformer());
}
