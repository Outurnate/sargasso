package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;
import com.outurnate.sargasso.entity.ElectricMine;
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
    public static final Identifier ZAP_LOCATION = SuperSargassoSea.ID("textures/entity/zap.png");
    private static final RenderType ZAP = RenderTypes.endCrystalBeam(ZAP_LOCATION);

    private static void drawBeam(
        VertexConsumer buffer,
        PoseStack.Pose pose,
        int lightCoords,
        float deltaX,
        float deltaY,
        float deltaZ,
        float v0,
        float v1) {
        drawBeamQuad(buffer, pose, lightCoords, deltaX, deltaY, deltaZ, v0, v1, 0.1F, 0.0F);
        drawBeamQuad(buffer, pose, lightCoords, deltaX, deltaY, deltaZ, v0, v1, 0.0F, 0.1F);
    }

    private static void drawBeamQuad(
        VertexConsumer buffer,
        PoseStack.Pose pose,
        int lightCoords,
        float deltaX,
        float deltaY,
        float deltaZ,
        float v0,
        float v1,
        float xw,
        float yw) {
        buffer.addVertex(pose, -xw, -yw, 0.0F)
            .setColor(-1)
            .setUv(0.0F, v0)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(lightCoords)
            .setNormal(pose, 0.0F, -1.0F, 0.0F);

        buffer.addVertex(pose, deltaX - xw, deltaY - yw, deltaZ)
            .setColor(-1)
            .setUv(0.0F, v1)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(lightCoords)
            .setNormal(pose, 0.0F, -1.0F, 0.0F);

        buffer.addVertex(pose, deltaX + xw, deltaY + yw, deltaZ)
            .setColor(-1)
            .setUv(0.125F, v1)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(lightCoords)
            .setNormal(pose, 0.0F, -1.0F, 0.0F);

        buffer.addVertex(pose, xw, yw, 0.0F)
            .setColor(-1)
            .setUv(0.125F, v0)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(lightCoords)
            .setNormal(pose, 0.0F, -1.0F, 0.0F);
    }

    private static void submitCrystalBeams(
        float deltaX,
        float deltaY,
        float deltaZ,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords) {
        poseStack.pushPose();
        submitNodeCollector.submitCustomGeometry(
            poseStack,
            ZAP,
            (pose, buffer) -> {
                drawBeam(buffer, pose, lightCoords, deltaX, deltaY, deltaZ, 0.0F, 1.0F);
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
            poseStack.translate(0.5F, 0.5F, 0.5F);
            submitCrystalBeams(
                (float) delta.x,
                (float) delta.y,
                (float) delta.z,
                poseStack,
                submitNodeCollector,
                state.lightCoords);
            poseStack.popPose();
        }
    }
}
