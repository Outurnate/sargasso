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
package com.outurnate.sargasso;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue ADVANCED_RENDERING = BUILDER
			.comment(
					"Whether to enable advanced rendering effects")
			.define("advancedRendering", true);

	public static final ModConfigSpec SPEC = BUILDER.build();
}
