package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;
import com.outurnate.sargasso.entity.ElectricMine;
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
    private static record ElectricArc(
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
                (random.nextFloat() - 0.5F) * amplitude,
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

        public void draw(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords) {
            double horizontalDistance = Math.sqrt(delta.x * delta.x + delta.z * delta.z);

            poseStack.pushPose();
            poseStack.translate(origin.x, origin.y, origin.z);
            poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(delta.z, delta.x))));
            poseStack
                .mulPose(Axis.ZP.rotation((float) (-Math.atan2(horizontalDistance, delta.y)) + Mth.HALF_PI));
            for (LineSegment segment : segments) {
                submitNodeCollector.submitCustomGeometry(
                    poseStack,
                    LocalRenderTypes.ZAP,
                    (pose, buffer) -> {
                        segment.draw(buffer, pose, lightCoords);
                    });
            }
            poseStack.popPose();
        }
    }

    private static record LineSegment(Vector3f start, Vector3f end) {
        private void drawQuad(
            VertexConsumer buffer,
            PoseStack.Pose pose,
            int lightCoords,
            float zw,
            float yw,
            float zo,
            float yo,
            boolean aorb) {
            float zf = zw + zo;
            float yf = yw + yo;
            buffer.addVertex(pose, start.x, start.y + (aorb ? -yf : yf), start.z + (aorb ? -zf : zf))
                .setColor(-1)
                .setUv(0.0F, 0.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(lightCoords)
                .setNormal(pose, 0.0F, -1.0F, 0.0F);

            buffer.addVertex(pose, end.x, end.y + (aorb ? -yf : yf), end.z + (aorb ? -zf : zf))
                .setColor(-1)
                .setUv(0.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(lightCoords)
                .setNormal(pose, 0.0F, -1.0F, 0.0F);

            buffer.addVertex(pose, end.x, end.y + (aorb ? yf : -yf), end.z + (aorb ? zf : -zf))
                .setColor(-1)
                .setUv(1.0F, 1.0F)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(lightCoords)
                .setNormal(pose, 0.0F, -1.0F, 0.0F);

            buffer.addVertex(pose, start.x, start.y + (aorb ? yf : -yf), start.z + (aorb ? zf : -zf))
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
            float size = 1.0F / 16.0F;
            drawQuad(buffer, pose, lightCoords, size, 0.0F, 0.0F, -size, true);
            drawQuad(buffer, pose, lightCoords, 0.0F, size, -size, 0.0F, false);
            drawQuad(buffer, pose, lightCoords, size, 0.0F, 0.0F, size, false);
            drawQuad(buffer, pose, lightCoords, 0.0F, size, size, 0.0F, true);
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
                blockRelativeMinePosition,
                0)
                    .draw(poseStack, submitNodeCollector, state.lightCoords);
        }
    }
}
