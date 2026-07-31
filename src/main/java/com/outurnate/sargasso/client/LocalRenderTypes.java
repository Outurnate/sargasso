/* (C)2026 */
package com.outurnate.sargasso.client;

import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
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

    public static final RenderType ZAP = RenderTypes.entityCutout(ZAP_LOCATION);
}
