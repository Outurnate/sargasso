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

    private static final Animation swingAnimation = new Animation(
        new Animation.Keyframe(0.0F, new Vec3(Math.PI * -1.0, Math.PI * -0.5, Math.PI * 0.5)),
        new Animation.Keyframe(0.1F, new Vec3(Math.PI * -0.75, 0.0, 0.0)),
        new Animation.Keyframe(0.9F, new Vec3(Math.PI * -0.75, 0.0, 0.0)),
        new Animation.Keyframe(1.0F, new Vec3(Math.PI * -1.0, Math.PI * -0.5, Math.PI * 0.5)));

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
            Vec3 main = swingAnimation.sample(entity.attackTime);
            mainArmX = (float) main.x;
            mainArmY = (float) main.y;
            mainArmZ = (float) main.z;
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
