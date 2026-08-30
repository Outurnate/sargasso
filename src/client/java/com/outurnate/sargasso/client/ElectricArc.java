package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import java.util.ArrayList;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ElectricArc {
    private static record LineSegment(Vector3f start, Vector3f end) {
        private void submitQuad(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            float zw,
            float yw,
            float zo,
            float yo,
            boolean reverse,
            RenderType renderType) {

            submitNodeCollector.submitCustomGeometry(
                poseStack,
                renderType,
                (pose, buffer) -> {
                    int fullBright = 0x00F000F0;

                    float[] x = { start.x, end.x, end.x, start.x };
                    float[] y = {
                        start.y - yw + yo,
                        end.y - yw + yo,
                        end.y + yw + yo,
                        start.y + yw + yo
                    };
                    float[] z = {
                        start.z - zw + zo,
                        end.z - zw + zo,
                        end.z + zw + zo,
                        start.z + zw + zo
                    };

                    float[] u = { 0.0F, 0.0F, 1.0F, 1.0F };
                    float[] v = { 0.0F, 1.0F, 1.0F, 0.0F };

                    int[] order = reverse
                        ? new int[] { 3, 2, 1, 0 }
                        : new int[] { 0, 1, 2, 3 };

                    for (int i : order) {
                        buffer.addVertex(pose, x[i], y[i], z[i])
                            .setColor(-1)
                            .setUv(u[i], v[i])
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setLight(fullBright)
                            .setNormal(pose, 0.0F, -1.0F, 0.0F);
                    }
                });
        }

        public void submit(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            RenderType renderType) {
            float size = 0.5F / 16.0F;
            submitQuad(poseStack, submitNodeCollector, size, 0.0F, 0.0F, -size, true, renderType);
            submitQuad(poseStack, submitNodeCollector, 0.0F, size, -size, 0.0F, false, renderType);
            submitQuad(poseStack, submitNodeCollector, size, 0.0F, 0.0F, size, false, renderType);
            submitQuad(poseStack, submitNodeCollector, 0.0F, size, size, 0.0F, true, renderType);
        }
    }

    private static void lightning(
        LineSegment lineSegment,
        int depth,
        ArrayList<LineSegment> accumulator,
        RandomSource random,
        float amplitude,
        float maxLength) {
        Vector3f segmentLength = new Vector3f();
        lineSegment.end().sub(lineSegment.start(), segmentLength);
        segmentLength.mul(0.5F);

        Vector3f midpoint = new Vector3f();
        lineSegment.start().add(segmentLength, midpoint);
        midpoint.add(
            0.0F,
            random.nextFloat() * (amplitude / 2.0F),
            (random.nextFloat() - 0.5F) * amplitude);

        LineSegment segment1 = new LineSegment(lineSegment.start(), midpoint);
        LineSegment segment2 = new LineSegment(midpoint, lineSegment.end());
        if (depth <= 0) {
            if (segment1.start.x < maxLength) {
                accumulator.add(segment1);
            }
            if (segment2.start.x < maxLength) {
                accumulator.add(segment2);
            }
        } else {
            amplitude /= 2;
            lightning(segment1, depth - 1, accumulator, random, amplitude, maxLength);
            lightning(segment2, depth - 1, accumulator, random, amplitude, maxLength);
        }
    }

    private final Vector3f origin;
    private final Vector3f delta;
    private final ArrayList<LineSegment> segments;
    private final RenderType renderType;

    public ElectricArc(
        Vector3f origin,
        Vector3f destination,
        long seed,
        int depth,
        Float segmentLength,
        float amplitude,
        RenderType renderType) {
        RandomSource random = RandomSource.createThreadLocalInstance(seed);
        this.delta = new Vector3f();
        destination.sub(origin, delta);

        float length;
        float maxLength;
        if (segmentLength != null) {
            // d | s
            // 0 | 2
            // 1 | 4
            // 2 | 8
            // segments = 2^(depth+1)
            int segments = Math.powExact(2, depth + 1);
            length = segmentLength * segments;
            maxLength = delta.length();
        } else {
            length = delta.length();
            maxLength = length + 1;
        }

        this.segments = new ArrayList<>();
        lightning(
            new LineSegment(new Vector3f(0.0F), new Vector3f(length, 0.0F, 0.0F)),
            depth,
            segments,
            random,
            amplitude,
            maxLength);
        this.origin = origin;
        this.renderType = renderType;
    }

    public ElectricArc(Vector3f origin, Vector3f destination, long seed, RenderType renderType) {
        this(origin, destination, seed, 3, null, 1.0F, renderType);
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        double horizontalDistance = Math.sqrt(delta.x * delta.x + delta.z * delta.z);

        poseStack.pushPose();
        poseStack.translate(origin.x, origin.y, origin.z);
        poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(delta.z, delta.x))));
        poseStack
            .mulPose(Axis.ZP.rotation((float) (-Math.atan2(horizontalDistance, delta.y)) + Mth.HALF_PI));
        for (LineSegment segment : segments) {
            segment.submit(poseStack, submitNodeCollector, renderType);
        }
        poseStack.popPose();
    }
}