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
        new Animation.Keyframe(0.0F, new Vec3(Math.PI * -0.75, Math.PI * -0.1, 0.0)),
        new Animation.Keyframe(0.1F, new Vec3(Math.PI * -0.25, Math.PI * -0.1, 0.0)),
        new Animation.Keyframe(1.0F, new Vec3(Math.PI * -0.75, Math.PI * -0.1, 0.0)));

    private static final Animation OFF_HAND_ANIM = new Animation(
        new Animation.Keyframe(0.0F, new Vec3(Math.PI * -0.75, Math.PI * 0.3, 0.0)),
        new Animation.Keyframe(0.1F, new Vec3(Math.PI * -0.25, Math.PI * 0.3, 0.0)),
        new Animation.Keyframe(0.8F, new Vec3(Math.PI * -0.75, Math.PI * 0.3, 0.0)));

    @Override
    public void applyTransform(HumanoidModel<?> model, HumanoidRenderState entity, HumanoidArm arm) {
        SuperSargassoSea.LOGGER.error("t" + entity.attackTime);
        Vec3 main = MAIN_HAND_ANIM.sample(entity.attackTime);
        Vec3 off = OFF_HAND_ANIM.sample(entity.attackTime);
        // entity.attackArm == arm
        if (arm.compareTo(HumanoidArm.RIGHT) == 0) {
            if (entity.attackArm.compareTo(arm) == 0) {
                model.rightArm.xRot = (float) main.x;
                model.rightArm.yRot = (float) main.y;
                model.rightArm.zRot = (float) main.z;
                model.leftArm.xRot = (float) off.x;
                model.leftArm.yRot = (float) off.y;
                model.leftArm.zRot = (float) off.z;
                entity.leftHandItemState.clear();
            } else {

            }
        } else if (arm.compareTo(HumanoidArm.LEFT) == 0) {
            if (entity.attackArm.compareTo(arm) == 0) {
                model.leftArm.xRot = (float) main.x;
                model.leftArm.yRot = -(float) main.y;
                model.leftArm.zRot = -(float) main.z;
                model.rightArm.xRot = (float) off.x;
                model.rightArm.yRot = (float) off.y;
                model.rightArm.zRot = (float) off.z;
                entity.rightHandItemState.clear();
            } else {

            }
        }
    }
}
