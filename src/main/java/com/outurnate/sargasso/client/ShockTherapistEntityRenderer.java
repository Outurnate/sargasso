package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;

import java.util.ArrayList;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public class ShockTherapistEntityRenderer
    implements BlockEntityRenderer<ShockTherapistBlockEntity, ShockTherapistRenderState> {
    public static record ElectricArc(
        Vector3f origin,
        Vector3f delta,
        ArrayList<LineSegment> segments) {
        private static void lightning(
            LineSegment lineSegment,
            int depth,
            ArrayList<LineSegment> accumulator,
            RandomSource random,
            float amplitude) {
            Vector3f segmentLength = new Vector3f();
            lineSegment.end.sub(lineSegment.start, segmentLength);
            segmentLength.mul(0.5F);

            Vector3f midpoint = new Vector3f();
            lineSegment.start.add(segmentLength, midpoint);
            midpoint.add(
                0.0F,
                random.nextFloat() * (amplitude / 2.0F),
                (random.nextFloat() - 0.5F) * amplitude);

            LineSegment segment1 = new LineSegment(lineSegment.start, midpoint);
            LineSegment segment2 = new LineSegment(midpoint, lineSegment.end);
            if (depth == 0) {
                accumulator.add(segment1);
                accumulator.add(segment2);
            } else {
                amplitude /= 2;
                lightning(segment1, depth - 1, accumulator, random, amplitude);
                lightning(segment2, depth - 1, accumulator, random, amplitude);
            }
        }

        public ElectricArc(Vector3f origin, Vector3f destination, long seed) {
            RandomSource random = RandomSource.createThreadLocalInstance(seed);
            int depth = 3;
            Vector3f delta = new Vector3f();
            destination.sub(origin, delta);
            float length = delta.length();
            ArrayList<LineSegment> segments = new ArrayList<>();
            lightning(
                new LineSegment(new Vector3f(0.0F), new Vector3f(length, 0.0F, 0.0F)),
                depth,
                segments,
                random,
                1.0F);
            this(origin, delta, segments);
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

    public ShockTherapistEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public ShockTherapistRenderState createRenderState() {
        return new ShockTherapistRenderState();
    }

    @Override
    public void extractRenderState(
        ShockTherapistBlockEntity blockEntity,
        ShockTherapistRenderState state,
        float partialTicks,
        Vec3 cameraPosition,
        ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(
            blockEntity,
            state,
            partialTicks,
            cameraPosition,
            breakProgress);
        Vec3 blockPos = new Vec3(state.blockPos);
        RandomSource random = RandomSource.createThreadLocalInstance(blockEntity.seed);
        state.bolts = blockEntity.bolts.stream()
            .map(
                b -> new ElectricArc(
                    b.getFirst().pos(partialTicks).subtract(blockPos).toVector3f(),
                    b.getSecond().pos(partialTicks).subtract(blockPos).toVector3f(),
                    random.nextLong()))
            .toList();
    }

    @Override
    public void submit(
        ShockTherapistRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        for (ElectricArc arc : state.bolts) {
            arc.submit(poseStack, submitNodeCollector);
        }
    }
}
