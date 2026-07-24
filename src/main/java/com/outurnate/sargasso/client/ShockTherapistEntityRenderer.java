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
import net.minecraft.world.phys.Vec3;

import org.jspecify.annotations.Nullable;

public class ShockTherapistEntityRenderer
    implements BlockEntityRenderer<ShockTherapistBlockEntity, ShockTherapistRenderState> {
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

    private static final RenderType ZAP = RenderTypes.endCrystalBeam(ZAP_LOCATION);

    private static void lightning(
        LineSegment lineSegment,
        int depth,
        ArrayList<LineSegment> accumulator,
        Random random) {
        Vec3 segmentLength = lineSegment.end.subtract(lineSegment.start).multiply(0.5, 0.5, 0.5);
        Vec3 midpoint = lineSegment.start.add(segmentLength)
            .add(0.0F, random.nextDouble() - 0.5F, random.nextDouble() - 0.5F);
        LineSegment segment1 = new LineSegment(lineSegment.start, midpoint);
        LineSegment segment2 = new LineSegment(midpoint, lineSegment.end);
        if (depth == 0) {
            accumulator.add(segment1);
            accumulator.add(segment2);
        } else {
            lightning(segment1, depth - 1, accumulator, random);
            lightning(segment2, depth - 1, accumulator, random);
        }
    }

    private static void submitCrystalBeams(
        LineSegment original,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords) {
        poseStack.pushPose();
        submitNodeCollector.submitCustomGeometry(
            poseStack,
            ZAP,
            (pose, buffer) -> {
                Random random = new Random(0);
                ArrayList<LineSegment> segments = new ArrayList<>();
                lightning(original, 3, segments, random);
                for (LineSegment segment : segments) {
                    segment.draw(buffer, pose, lightCoords);
                }
            });
        poseStack.popPose();
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
    }

    @Override
    public void submit(
        ShockTherapistRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        for (ElectricMine mine : state.mines) {
            poseStack.pushPose();
            Vec3 delta = mine.getPosition(0).subtract(state.blockPos.getCenter()); // TODO partial tick
            double length = delta.length();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(delta.z, delta.x)))); // keep this one
            poseStack.mulPose(
                Axis.ZP.rotation(
                    (float) (-Math.atan2(delta.horizontalDistance(), delta.y)) + (float) (Math.PI / 2)));
            submitCrystalBeams(
                new LineSegment(
                    new Vec3(0.0F, 0.0F, 0.0F),
                    new Vec3(length, 0.0F, 0.0F)),
                poseStack,
                submitNodeCollector,
                state.lightCoords);
            poseStack.popPose();
        }
    }
}
