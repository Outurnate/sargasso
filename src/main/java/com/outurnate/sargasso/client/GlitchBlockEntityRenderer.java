/* (C)2026 */
package com.outurnate.sargasso.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.entity.GlitchBlockEntity;
import com.outurnate.sargasso.client.iris.IrisCompat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GlitchBlockEntityRenderer
    implements BlockEntityRenderer<GlitchBlockEntity, GlitchBlockRenderState> {
    private static final Vector3fc FROM = new Vector3f(0.0F, 0.0F, 0.0F);
    private static final Vector3fc TO = new Vector3f(1.0F, 1.0F, 1.0F);
    private static final Map<Direction, List<Vector3fc>> FACES = Util.makeEnumMap(
        Direction.class,
        direction -> {
            FaceInfo faceInfo = FaceInfo.fromFacing(direction);
            return List.of(
                faceInfo.getVertexInfo(0).select(FROM, TO),
                faceInfo.getVertexInfo(1).select(FROM, TO),
                faceInfo.getVertexInfo(2).select(FROM, TO),
                faceInfo.getVertexInfo(3).select(FROM, TO));
        });
    private static final List<Direction> ALL_FACES = List.of(Direction.values());

    private static final Identifier staticIdentifier = SuperSargassoSea.ID("dynamic/static");

    private static final float[][] UVS = {
        { 0.0F, 0.0F },
        { 0.0F, 1.0F },
        { 1.0F, 1.0F },
        { 1.0F, 0.0F }
    };

    public static void getExtents(Consumer<Vector3fc> output) {
        FACES.values().forEach(vertices -> vertices.forEach(output));
    }

    protected static void submitCube(
        Collection<Direction> facesToShow,
        RenderType renderType,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector) {
        if (!facesToShow.isEmpty()) {
            submitNodeCollector.submitCustomGeometry(poseStack, renderType, (pose, buffer) -> {
                for (Direction direction : facesToShow) {
                    int i = 0;
                    for (Vector3fc faceVertex : FACES.get(direction)) {
                        if (IrisCompat.INSTANCE.shouldUseFallbackRendering()) {
                            buffer.addVertex(pose, faceVertex);
                        } else {
                            int fullBright = 0x00F000F0;
                            buffer
                                .addVertex(pose, faceVertex)
                                .setNormal(pose, direction.getUnitVec3f())
                                .setUv(UVS[i][0], UVS[i][1])
                                .setColor(-1)
                                .setOverlay(OverlayTexture.NO_OVERLAY)
                                .setLight(fullBright);
                            ++i;
                        }
                    }
                }
            });
        }
    }

    public static void submitSpecial(
        RenderType renderType,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector) {
        submitCube(ALL_FACES, renderType, poseStack, submitNodeCollector);
    }

    private final NativeImage staticImage = new NativeImage(16, 16, false);

    private final DynamicTexture staticTexture = new DynamicTexture(() -> "static", staticImage);

    public GlitchBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        Minecraft.getInstance().getTextureManager().register(staticIdentifier, staticTexture);
    }

    @Override
    public GlitchBlockRenderState createRenderState() {
        return new GlitchBlockRenderState();
    }

    @Override
    public void extractRenderState(
        GlitchBlockEntity blockEntity,
        GlitchBlockRenderState state,
        float partialTicks,
        Vec3 cameraPosition,
        ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(
            blockEntity,
            state,
            partialTicks,
            cameraPosition,
            breakProgress);
        state.facesToShow.clear();

        for (Direction direction : Direction.values()) {
            if (Block.shouldRenderFace(
                blockEntity.getLevel(),
                blockEntity.getBlockPos(),
                blockEntity.getBlockState(),
                blockEntity.getLevel().getBlockState(blockEntity.getBlockPos().relative(direction)),
                direction)) {
                state.facesToShow.add(direction);
            }
        }
    }

    @Override
    public void submit(
        GlitchBlockRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        RenderType renderType;

        if (IrisCompat.INSTANCE.shouldUseFallbackRendering()) {
            renderType = RenderTypes.entityTranslucentEmissive(staticIdentifier);
            RandomSource rand = RandomSource.createThreadLocalInstance();
            for (int y = 0; y < staticImage.getHeight(); ++y) {
                for (int x = 0; x < staticImage.getWidth(); ++x) {
                    staticImage.setPixelABGR(x, y, rand.nextBoolean() ? 0xFFFFFFFF : 0xFF000000);
                }
            }
        } else {
            renderType = LocalRenderTypes.GLITCH;
        }

        poseStack.pushPose();
        submitCube(state.facesToShow, renderType, poseStack, submitNodeCollector);
        poseStack.popPose();
    }
}
