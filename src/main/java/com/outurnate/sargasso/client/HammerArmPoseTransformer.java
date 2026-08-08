package com.outurnate.sargasso.client;

import com.outurnate.sargasso.SuperSargassoSea;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class HammerArmPoseTransformer implements IArmPoseTransformer {
    private static class Animation {
        public static record Keyframe(float time, Vec3 value) {
        }

        private final Keyframe[] keyframes;

        public Animation(Keyframe... keyframes) {
            this.keyframes = keyframes;
        }

        public Vec3 sample(float time) {
            Keyframe last = keyframes[keyframes.length - 1];
            if (time >= last.time) {
                return last.value;
            }

            for (int i = 0; i < keyframes.length - 1; i++) {
                Keyframe current = keyframes[i];
                Keyframe next = keyframes[i + 1];

                if (time >= current.time && time <= next.time) {
                    float span = next.time - current.time;
                    float t = (span == 0f) ? 0f : (time - current.time) / span;
                    return Mth.lerp(t, current.value, next.value);
                }
            }

            return last.value;
        }
    }

    public static final EnumProxy<ArmPose> PROXY = new EnumProxy<ArmPose>(
        ArmPose.class,
        true,
        true,
        new HammerArmPoseTransformer());

    private static final Animation MAIN_HAND_ANIM = new Animation(
        new Animation.Keyframe(0.0F, new Vec3(Math.PI * -0.75, 0.0, 0.0)),
        new Animation.Keyframe(0.1F, new Vec3(Math.PI * 0.75, 0.0, 0.0)),
        new Animation.Keyframe(1.0F, new Vec3(Math.PI * -0.75, 0.0, 0.0)));

    private static final Animation OFF_HAND_ANIM = new Animation(
        new Animation.Keyframe(0.0F, new Vec3(Math.PI * -0.5, 0.0, 0.0)),
        new Animation.Keyframe(1.0F, new Vec3(Math.PI * -0.5, 0.0, 0.0)));

    @Override
    public void applyTransform(HumanoidModel<?> model, HumanoidRenderState entity, HumanoidArm arm) {
        SuperSargassoSea.LOGGER.error("t" + entity.attackTime);
        Vec3 main;
        Vec3 off;
        if (!(entity.attackTime <= 0.0F)) {
            main = MAIN_HAND_ANIM.sample(entity.attackTime);
            off = OFF_HAND_ANIM.sample(entity.attackTime);
        } else {
            main = MAIN_HAND_ANIM.sample(0.0F);
            off = OFF_HAND_ANIM.sample(0.0F);
        }
        float mainArmX = (float) main.x;
        float mainArmY = (float) main.y;
        float mainArmZ = (float) main.z;
        float secondArmX = (float) off.x;
        float secondArmY = (float) off.y;
        float secondArmZ = (float) off.z;
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
