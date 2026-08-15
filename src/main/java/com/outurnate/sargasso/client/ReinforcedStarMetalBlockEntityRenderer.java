package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.entity.ReinforcedStarMetalBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class ReinforcedStarMetalBlockEntityRenderer
    implements BlockEntityRenderer<ReinforcedStarMetalBlockEntity, BlockEntityRenderState> {
    private static void vertex(
        VertexConsumer consumer,
        Matrix4f pose,
        float x,
        float y,
        float z,
        float u,
        float v) {
        int fullBright = 0x00F000F0;
        consumer.addVertex(pose, x, y, z)
            .setColor(255, 255, 255, 255)
            .setUv(u, v)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(fullBright)
            .setNormal(0, 1, 0);
    }

    public ReinforcedStarMetalBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void submit(
        BlockEntityRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        SuperSargassoSea.LOGGER.error("ass");
        submitNodeCollector.submitCustomGeometry(
            poseStack,
            RenderTypes.armorEntityGlint(),
            (pose, vertexConsumer) -> {
                Matrix4f matrix = pose.pose();

                float x0 = 0;
                float y0 = 0;
                float z0 = 0;

                float x1 = 1;
                float y1 = 2;
                float z1 = 1;

                vertex(vertexConsumer, matrix, x0, y0, z0, 0, 0);
                vertex(vertexConsumer, matrix, x1, y0, z0, 1, 0);
                vertex(vertexConsumer, matrix, x1, y1, z0, 1, 1);
                vertex(vertexConsumer, matrix, x0, y1, z0, 0, 1);

                vertex(vertexConsumer, matrix, x1, y0, z1, 0, 0);
                vertex(vertexConsumer, matrix, x0, y0, z1, 1, 0);
                vertex(vertexConsumer, matrix, x0, y1, z1, 1, 1);
                vertex(vertexConsumer, matrix, x1, y1, z1, 0, 1);

                vertex(vertexConsumer, matrix, x0, y0, z1, 0, 0);
                vertex(vertexConsumer, matrix, x0, y0, z0, 1, 0);
                vertex(vertexConsumer, matrix, x0, y1, z0, 1, 1);
                vertex(vertexConsumer, matrix, x0, y1, z1, 0, 1);

                vertex(vertexConsumer, matrix, x1, y0, z0, 0, 0);
                vertex(vertexConsumer, matrix, x1, y0, z1, 1, 0);
                vertex(vertexConsumer, matrix, x1, y1, z1, 1, 1);
                vertex(vertexConsumer, matrix, x1, y1, z0, 0, 1);

                vertex(vertexConsumer, matrix, x0, y1, z0, 0, 0);
                vertex(vertexConsumer, matrix, x1, y1, z0, 1, 0);
                vertex(vertexConsumer, matrix, x1, y1, z1, 1, 1);
                vertex(vertexConsumer, matrix, x0, y1, z1, 0, 1);

                vertex(vertexConsumer, matrix, x0, y0, z1, 0, 0);
                vertex(vertexConsumer, matrix, x1, y0, z1, 1, 0);
                vertex(vertexConsumer, matrix, x1, y0, z0, 1, 1);
                vertex(vertexConsumer, matrix, x0, y0, z0, 0, 1);
            });
    }
}
