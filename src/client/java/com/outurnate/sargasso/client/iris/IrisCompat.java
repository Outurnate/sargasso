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
package com.outurnate.sargasso.client.iris;

import com.outurnate.sargasso.ClientConfig;
import net.neoforged.fml.ModList;

public abstract class IrisCompat {
	public static final IrisCompat INSTANCE;

	static {
		if (ModList.get().isLoaded("iris")) {
			INSTANCE = new RealIrisCompat();
		} else {
			INSTANCE = new IrisCompat() {
				@Override
				protected boolean isShaderPackInUse() {
					return false;
				}
			};
		}
	}

	protected abstract boolean isShaderPackInUse();

	public final boolean shouldUseFallbackRendering() {
		return !ClientConfig.ADVANCED_RENDERING.getAsBoolean() || isShaderPackInUse();
	}
}
