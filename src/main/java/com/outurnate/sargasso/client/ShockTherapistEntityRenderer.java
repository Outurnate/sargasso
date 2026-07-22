package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ShockTherapistEntityRenderer
    implements BlockEntityRenderer<ShockTherapistBlockEntity, ShockTherapistRenderState> {
    public static final Identifier CRYSTAL_BEAM_LOCATION = Identifier
        .withDefaultNamespace("textures/entity/end_crystal/end_crystal_beam.png");
    private static final RenderType BEAM = RenderTypes.endCrystalBeam(CRYSTAL_BEAM_LOCATION);

    private static void submitCrystalBeams(
        float deltaX,
        float deltaY,
        float deltaZ,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords) {
        float horizontalLength = Mth.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float length = Mth.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        poseStack.pushPose();
        // poseStack.mulPose(Axis.YP.rotation((float)(-Math.atan2(deltaZ, deltaX)) -
        // (float) (Math.PI / 2)));
        // poseStack.mulPose(Axis.XP.rotation((float)(-Math.atan2(horizontalLength,
        // deltaY)) - (float) (Math.PI / 2)));
        float v0 = 0.0F;
        float v1 = length / 32.0F;
        submitNodeCollector.submitCustomGeometry(
            poseStack,
            BEAM,
            (pose, buffer) -> {
                buffer.addVertex(pose, -0.1F, -0.1F, 0.0F)
                    .setColor(-16777216)
                    .setUv(0.0F, v0)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(lightCoords)
                    .setNormal(pose, 0.0F, -1.0F, 0.0F);
                buffer.addVertex(pose, deltaX - 0.1F, deltaY + 0.1F, deltaZ)
                    .setColor(-1)
                    .setUv(0.0F, v1)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(lightCoords)
                    .setNormal(pose, 0.0F, -1.0F, 0.0F);
                buffer.addVertex(pose, deltaX + 0.1F, deltaY + 0.1F, deltaZ)
                    .setColor(-1)
                    .setUv(0.125F, v1)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(lightCoords)
                    .setNormal(pose, 0.0F, -1.0F, 0.0F);
                buffer.addVertex(pose, 0.1F, -0.1F, 0.0F)
                    .setColor(-16777216)
                    .setUv(0.125F, v0)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(lightCoords)
                    .setNormal(pose, 0.0F, -1.0F, 0.0F);
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
