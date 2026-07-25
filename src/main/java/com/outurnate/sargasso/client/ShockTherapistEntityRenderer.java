package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;
import com.outurnate.sargasso.entity.ElectricMine;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
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
            // need (2^depth)*2 random values
            Vec3 segmentLength = lineSegment.end.subtract(lineSegment.start).multiply(0.5, 0.5, 0.5);
            Vec3 midpoint = lineSegment.start.add(segmentLength)
                .add(
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
                // randomValues[i] = random.nextFloat();
                randomValues[i] = 0.5F;
            }

            Vector3f delta = new Vector3f();
            destination.sub(origin, delta);
            float length = delta.length();
            ArrayList<LineSegment> segments = new ArrayList<>(); // TODO this can just be an array
            lightning(
                new LineSegment(new Vec3(0.0, 0.0, 0.0), new Vec3(length, 0.0, 0.0)),
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
            poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(delta.z, delta.x))));
            poseStack
                .mulPose(Axis.ZP.rotation((float) (-Math.atan2(horizontalDistance, delta.y)) + Mth.HALF_PI));
            poseStack.translate(origin.x, origin.y, origin.z);
            submitNodeCollector.submitCustomGeometry(
                poseStack,
                ZAP,
                (pose, buffer) -> {
                    for (LineSegment segment : segments) {
                        segment.draw(buffer, pose, lightCoords);
                    }
                });
            poseStack.popPose();
        }
    }

    private static record LineSegment(Vec3 start, Vec3 end) {
        private void drawBeamQuad(
            VertexConsumer buffer,
            PoseStack.Pose pose,
            int lightCoords,
            float zw,
            float yw) {
            buffer.addVertex(pose, (float) start.x, -yw + (float) start.y, (float) start.z - zw)
                .setColor(-1)
                .setUv(0.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(lightCoords)
                .setNormal(pose, 0.0F, -1.0F, 0.0F);

            buffer.addVertex(pose, (float) end.x, (float) end.y - yw, (float) end.z - zw)
                .setColor(-1)
                .setUv(0.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(lightCoords)
                .setNormal(pose, 0.0F, -1.0F, 0.0F);

            buffer.addVertex(pose, (float) end.x, (float) end.y + yw, (float) end.z + zw)
                .setColor(-1)
                .setUv(1.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(lightCoords)
                .setNormal(pose, 0.0F, -1.0F, 0.0F);

            buffer.addVertex(pose, (float) start.x, yw + (float) start.y, (float) start.z + zw)
                .setColor(-1)
                .setUv(1.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(lightCoords)
                .setNormal(pose, 0.0F, -1.0F, 0.0F);
        }

        public void draw(
            VertexConsumer buffer,
            PoseStack.Pose pose,
            int lightCoords) {
            drawBeamQuad(buffer, pose, lightCoords, 0.1F, 0.0F);
            drawBeamQuad(buffer, pose, lightCoords, 0.0F, 0.1F);
        }
    }

    public static final Identifier ZAP_LOCATION = SuperSargassoSea.ID("textures/entity/zap.png");

    private static final RenderType ZAP = RenderTypes.entityTranslucent(ZAP_LOCATION);

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
        for (ElectricMine mine : state.mines) {
            new ElectricArc(
                new Vector3f(0.5F, 0.5F, 0.5F),
                mine.getPosition(state.partialTicks).subtract(state.blockPos.getCenter()).toVector3f())
                    .draw(poseStack, submitNodeCollector, state.lightCoords);
        }
    }
}
