/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
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

	public static final Identifier ELECTRIC_ZAP_LOCATION = SuperSargassoSea
			.ID("textures/entity/electric_zap.png");

	public static final RenderType ELECTRIC_ZAP = RenderTypes.entityCutout(ELECTRIC_ZAP_LOCATION);

	public static final Identifier REDSTONE_ZAP_LOCATION = SuperSargassoSea
			.ID("textures/entity/redstone_zap.png");

	public static final RenderType REDSTONE_ZAP = RenderTypes.entityCutout(REDSTONE_ZAP_LOCATION);
}
