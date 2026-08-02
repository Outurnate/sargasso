package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.outurnate.sargasso.SuperSargassoSea;

import java.util.ArrayList;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ElectricArc {
    private static record LineSegment(Vector3f start, Vector3f end) {
        private void drawQuadA(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            float zw,
            float yw,
            float zo,
            float yo) {

            submitNodeCollector.submitCustomGeometry(
                poseStack,
                LocalRenderTypes.ZAP,
                (pose, buffer) -> {
                    int fullBright = 0x00F000F0;
                    buffer.addVertex(pose, start.x, start.y - yw + yo, start.z - zw + zo)
                        .setColor(-1)
                        .setUv(0.0F, 0.0F)
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(fullBright)
                        .setNormal(pose, 0.0F, -1.0F, 0.0F);

                    buffer.addVertex(pose, end.x, end.y - yw + yo, end.z - zw + zo)
                        .setColor(-1)
                        .setUv(0.0F, 1.0F)
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(fullBright)
                        .setNormal(pose, 0.0F, -1.0F, 0.0F);

                    buffer.addVertex(pose, end.x, end.y + yw + yo, end.z + zw + zo)
                        .setColor(-1)
                        .setUv(1.0F, 1.0F)
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(fullBright)
                        .setNormal(pose, 0.0F, -1.0F, 0.0F);

                    buffer.addVertex(pose, start.x, start.y + yw + yo, start.z + zw + zo)
                        .setColor(-1)
                        .setUv(1.0F, 0.0F)
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(fullBright)
                        .setNormal(pose, 0.0F, -1.0F, 0.0F);
                });
        }

        private void drawQuadB(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            float zw,
            float yw,
            float zo,
            float yo) {
            submitNodeCollector.submitCustomGeometry(
                poseStack,
                LocalRenderTypes.ZAP,
                (pose, buffer) -> {
                    int fullBright = 0x00F000F0;
                    buffer.addVertex(pose, start.x, start.y + yw + yo, start.z + zw + zo)
                        .setColor(-1)
                        .setUv(1.0F, 0.0F)
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(fullBright)
                        .setNormal(pose, 0.0F, -1.0F, 0.0F);

                    buffer.addVertex(pose, end.x, end.y + yw + yo, end.z + zw + zo)
                        .setColor(-1)
                        .setUv(1.0F, 1.0F)
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(fullBright)
                        .setNormal(pose, 0.0F, -1.0F, 0.0F);

                    buffer.addVertex(pose, end.x, end.y - yw + yo, end.z - zw + zo)
                        .setColor(-1)
                        .setUv(0.0F, 1.0F)
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(fullBright)
                        .setNormal(pose, 0.0F, -1.0F, 0.0F);

                    buffer.addVertex(pose, start.x, start.y - yw + yo, start.z - zw + zo)
                        .setColor(-1)
                        .setUv(0.0F, 0.0F)
                        .setOverlay(OverlayTexture.NO_OVERLAY)
                        .setLight(fullBright)
                        .setNormal(pose, 0.0F, -1.0F, 0.0F);
                });
        }

        public void draw(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector) {
            float size = 0.5F / 16.0F;
            drawQuadB(poseStack, submitNodeCollector, size, 0.0F, 0.0F, -size);
            drawQuadA(poseStack, submitNodeCollector, 0.0F, size, -size, 0.0F);
            drawQuadA(poseStack, submitNodeCollector, size, 0.0F, 0.0F, size);
            drawQuadB(poseStack, submitNodeCollector, 0.0F, size, size, 0.0F);
        }
    }

    private static void lightning(
        LineSegment lineSegment,
        int depth,
        ArrayList<LineSegment> accumulator,
        RandomSource random,
        float amplitude) {
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
            accumulator.add(segment1);
            accumulator.add(segment2);
        } else {
            amplitude /= 2;
            lightning(segment1, depth - 1, accumulator, random, amplitude);
            lightning(segment2, depth - 1, accumulator, random, amplitude);
        }
    }

    private final Vector3f origin;
    private final Vector3f delta;
    private final ArrayList<LineSegment> segments;

    public ElectricArc(Vector3f origin, Vector3f destination, long seed) {
        this(origin, destination, seed, null);
    }

    public ElectricArc(Vector3f origin, Vector3f destination, long seed, Float segmentLength) {
        RandomSource random = RandomSource.createThreadLocalInstance(seed);
        this.delta = new Vector3f();
        destination.sub(origin, delta);
        float length = delta.length();

        int depth = 3;
        if (segmentLength != null) {
            double targetNumberOfSegments = length / segmentLength;
            depth = (int) Math.round(Math.log(targetNumberOfSegments) / Math.log(2));
            SuperSargassoSea.LOGGER.error("depth=" + depth);
        }

        // d | s
        // 0 | 2
        // 1 | 4
        // 2 | 8
        // segments = 2^(depth+1)

        this.segments = new ArrayList<>();
        lightning(
            new LineSegment(new Vector3f(0.0F), new Vector3f(length, 0.0F, 0.0F)),
            depth,
            segments,
            random,
            1.0F);
        this.origin = origin;
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        double horizontalDistance = Math.sqrt(delta.x * delta.x + delta.z * delta.z);

        poseStack.pushPose();
        poseStack.translate(origin.x, origin.y, origin.z);
        poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(delta.z, delta.x))));
        poseStack
            .mulPose(Axis.ZP.rotation((float) (-Math.atan2(horizontalDistance, delta.y)) + Mth.HALF_PI));
        for (LineSegment segment : segments) {
            segment.draw(poseStack, submitNodeCollector);
        }
        poseStack.popPose();
    }
}