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
package com.outurnate.sargasso.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record LoreSet(String name, int size) {
	public Stream<String> keys() {
		return IntStream.range(0, size).boxed()
				.map(i -> "lore." + SuperSargassoSea.MODID + "." + name + "." + i);
	}
}
