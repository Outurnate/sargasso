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
package com.outurnate.sargasso.datagen.book.model;

import java.util.Locale;

public class ModelUtils {
	public static String identify(String text) {
		return text.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "_");
	}
}
