package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;
import com.outurnate.sargasso.entity.ElectricMine;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public class ShockTherapistEntityRenderer
    implements BlockEntityRenderer<ShockTherapistBlockEntity, ShockTherapistRenderState> {
    private static record ElectricArc(
        Vector3f origin,
        Vector3f delta,
        ArrayList<LineSegment> segments,
        float[] randomValues) {
        private static void lightning(
            LineSegment lineSegment,
            int depth,
            ArrayList<LineSegment> accumulator,
            float[] randomValues,
            int randomIndex,
            float amplitude) {
            Vector3f segmentLength = new Vector3f();
            lineSegment.end.sub(lineSegment.start, segmentLength);
            segmentLength.mul(0.5F);

            Vector3f midpoint = new Vector3f();
            lineSegment.start.add(segmentLength, midpoint);
            midpoint.add(
                0.0F,
                (randomValues[randomIndex++] - 0.5F) * amplitude,
                (randomValues[randomIndex++] - 0.5F) * amplitude);

            LineSegment segment1 = new LineSegment(lineSegment.start, midpoint);
            LineSegment segment2 = new LineSegment(midpoint, lineSegment.end);
            if (depth == 0) {
                accumulator.add(segment1);
                accumulator.add(segment2);
            } else {
                amplitude /= 2;
                lightning(segment1, depth - 1, accumulator, randomValues, randomIndex, amplitude);
                lightning(segment2, depth - 1, accumulator, randomValues, randomIndex, amplitude);
            }
        }

        public ElectricArc(Vector3f origin, Vector3f destination) {
            Random random = new Random(0);
            int depth = 3;
            float[] randomValues = new float[Math.powExact(2, depth) * 2];
            for (int i = 0; i < randomValues.length; ++i) {
                randomValues[i] = random.nextFloat();
                // randomValues[i] = 0.5F;
            }

            Vector3f delta = new Vector3f();
            destination.sub(origin, delta);
            float length = delta.length();
            ArrayList<LineSegment> segments = new ArrayList<>(); // TODO this can just be an array
            lightning(
                new LineSegment(new Vector3f(0.0F), new Vector3f(length, 0.0F, 0.0F)),
                depth,
                segments,
                randomValues,
                0,
                1.0F);
            this(origin, delta, segments, randomValues);
        }

        public void draw(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords) {
            double horizontalDistance = Math.sqrt(delta.x * delta.x + delta.z * delta.z);

            poseStack.pushPose();
            poseStack.translate(origin.x, origin.y, origin.z);
            poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(delta.z, delta.x))));
            poseStack
                .mulPose(Axis.ZP.rotation((float) (-Math.atan2(horizontalDistance, delta.y)) + Mth.HALF_PI));
            submitNodeCollector.submitCustomGeometry(
                poseStack,
                LocalRenderTypes.ZAP,
                (pose, buffer) -> {
                    for (LineSegment segment : segments) {
                        segment.draw(buffer, pose, lightCoords);
                    }
                });
            poseStack.popPose();
        }
    }

    private static record LineSegment(Vector3f start, Vector3f end) {
        private void drawBeamQuadA(
            VertexConsumer buffer,
            PoseStack.Pose pose,
            int lightCoords,
            float zw,
            float yw,
            float zo,
            float yo) {
            buffer.addVertex(pose, start.x, start.y - yw + yo, start.z - zw + zo)
                .setColor(-1)
                .setUv(0.0F, 0.0F);

            buffer.addVertex(pose, end.x, end.y - yw + yo, end.z - zw + zo)
                .setColor(-1)
                .setUv(0.0F, 1.0F);

            buffer.addVertex(pose, end.x, end.y + yw + yo, end.z + zw + zo)
                .setColor(-1)
                .setUv(1.0F, 1.0F);

            buffer.addVertex(pose, start.x, start.y + yw + yo, start.z + zw + zo)
                .setColor(-1)
                .setUv(1.0F, 0.0F);
        }

        private void drawBeamQuadB(
            VertexConsumer buffer,
            PoseStack.Pose pose,
            int lightCoords,
            float zw,
            float yw,
            float zo,
            float yo) {
            buffer.addVertex(pose, start.x, start.y + yw + yo, start.z + zw + zo)
                .setColor(-1)
                .setUv(1.0F, 0.0F);

            buffer.addVertex(pose, end.x, end.y + yw + yo, end.z + zw + zo)
                .setColor(-1)
                .setUv(1.0F, 1.0F);

            buffer.addVertex(pose, end.x, end.y - yw + yo, end.z - zw + zo)
                .setColor(-1)
                .setUv(0.0F, 1.0F);

            buffer.addVertex(pose, start.x, start.y - yw + yo, start.z - zw + zo)
                .setColor(-1)
                .setUv(0.0F, 0.0F);
        }

        public void draw(
            VertexConsumer buffer,
            PoseStack.Pose pose,
            int lightCoords) {
            float size = 1.0F / 16.0F;
            drawBeamQuadA(buffer, pose, lightCoords, size, 0.0F, 0.0F, -size);
            drawBeamQuadB(buffer, pose, lightCoords, 0.0F, size, -size, 0.0F);
            drawBeamQuadB(buffer, pose, lightCoords, size, 0.0F, 0.0F, size);
            drawBeamQuadA(buffer, pose, lightCoords, 0.0F, size, size, 0.0F);
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
        state.mines = blockEntity.mines;
        state.partialTicks = partialTicks;
    }

    @Override
    public void submit(
        ShockTherapistRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        Vector3f blockPos = state.blockPos.getCenter().toVector3f();
        Vector3f blockOffset = new Vector3f(0.5F);
        for (ElectricMine mine : state.mines) {
            Vector3f blockRelativeMinePosition = mine.getPosition(state.partialTicks).toVector3f()
                .sub(blockPos).add(blockOffset); // TODO WHY DOES THIS WORK
            new ElectricArc(
                blockOffset,
                blockRelativeMinePosition)
                    .draw(poseStack, submitNodeCollector, state.lightCoords);
        }
    }
}
