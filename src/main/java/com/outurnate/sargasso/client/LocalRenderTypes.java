/* (C)2026 */
package com.outurnate.sargasso.client;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LocalRenderTypes {
    private static final RenderPipeline GLITCH_PIPELINE = RenderPipeline
        .builder(
            RenderPipelines.MATRICES_PROJECTION_SNIPPET,
            RenderPipelines.FOG_SNIPPET,
            RenderPipelines.GLOBALS_SNIPPET)
        .withVertexShader(Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "rendertype_glitch"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "rendertype_glitch"))
        .withVertexFormat(DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS)
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .withLocation(Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "pipeline/glitch"))
        .build();

    public static final RenderType GLITCH = RenderType.create(
        "glitch",
        RenderSetup.builder(GLITCH_PIPELINE)
            .createRenderSetup());

    public static final Identifier ZAP_LOCATION = SuperSargassoSea.ID("textures/entity/zap.png");

    public static final RenderPipeline.Snippet ZAP_SNIPPET = RenderPipeline.builder(
        RenderPipelines.GENERIC_BLOCKS_SNIPPET,
        RenderPipelines.MATRICES_PROJECTION_SNIPPET)
        .withVertexShader("core/block")
        .withFragmentShader("core/block")
        .withShaderDefine("EMISSIVE")
        .buildSnippet();

    public static final RenderPipeline ZAP_PIPELINE = RenderPipeline
        .builder(RenderPipelines.ENTITY_EMISSIVE_SNIPPET)
        .withLocation("pipeline/entity_translucent_emissive")
        .withShaderDefine("ALPHA_CUTOUT", 0.1F)
        .withShaderDefine("PER_FACE_LIGHTING")
        .withSampler("Sampler1")
        .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
        .withCull(false)
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .build();

    public static final RenderType ZAP = RenderType.create(
        "zap",
        RenderSetup.builder(ZAP_PIPELINE)
            .withTexture("Sampler0", ZAP_LOCATION)
            .affectsCrumbling()
            .sortOnUpload()
            .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
            .createRenderSetup());
}
