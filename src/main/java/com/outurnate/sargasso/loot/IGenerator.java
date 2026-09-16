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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.PlainTextContents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.RandomSource;

// concept originally from OpenBlocks
// https://github.com/OpenMods/OpenBlocks
// ported under MIT license
public interface IGenerator {
	static IGenerator alt(IGenerator... alts) {
		return (random, params) -> {
			if (alts.length == 0) {
				return List.of(PlainTextContents.EMPTY);
			}
			int choice = random.nextInt(alts.length);
			return alts[choice].generate(random, params);
		};
	}

	static IGenerator alt(LoreSet defs, IGenerator... appends) {
		return alt(
				Stream.concat(
						defs.keys().map(key -> new TranslatableContents(key, null, new Object[0]))
								.map(IGenerator::terminal),
						Arrays.stream(appends)).toArray(IGenerator[]::new));
	}

	private static List<ComponentContents> flatten(List<ComponentContents> components) {
		List<ComponentContents> result = new ArrayList<>();
		boolean previousWasSpace = false;

		for (ComponentContents component : components) {
			boolean isSpace = component instanceof LiteralContents(String text)
					&& " ".equals(text);
			if (isSpace && previousWasSpace) {
				continue;
			}
			result.add(component);
			previousWasSpace = isSpace;
		}

		return result;
	}

	private static boolean hasContents(List<ComponentContents> component) {
		return !component.isEmpty() && !component.stream().allMatch(PlainTextContents.EMPTY::equals);
	}

	private static <T> List<T> intersperse(List<T> input, T separator) {
		if (input.isEmpty()) {
			return List.of();
		}

		List<T> result = new ArrayList<>(input.size() * 2 - 1);

		for (int i = 0; i < input.size(); i++) {
			if (i > 0) {
				result.add(separator);
			}
			result.add(input.get(i));
		}

		return result;
	}

	static IGenerator opt(float probability, IGenerator gen) {
		return opt(probability, gen, terminal(PlainTextContents.EMPTY));
	}

	static IGenerator opt(float probability, IGenerator left, IGenerator right) {
		return (random, params) -> (random.nextFloat() < probability) ? left.generate(random, params) : right.generate(random, params);
	}

	static IGenerator range(int start, int end) {
		return new IGenerator() {
			private final int range = end - start;

			@Override
			public List<ComponentContents> generate(
					RandomSource random,
					Map<String, ComponentContents> params) {
				return List.of(PlainTextContents.create(Integer.toString(start + random.nextInt(range))));
			}
		};
	}

	static IGenerator seq(IGenerator... parts) {
		return (random, params) -> {
			List<ComponentContents> results = new ArrayList<>();

			for (IGenerator part : parts) {
				List<ComponentContents> result = part.generate(random, params);
				if (hasContents(result))
					results.addAll(result);
			}

			return flatten(intersperse(results, PlainTextContents.create(" ")));
		};
	}

	static IGenerator sub(String key) {
		return sub(key, (TranslatableContents) null);
	}

	static IGenerator sub(String key, TranslatableContents defaultValue) {
		return (_, params) -> {
			if (params.get(key) != null)
				return List.of(params.get(key));
			else if (defaultValue != null)
				return List.of(defaultValue);
			return List.of(PlainTextContents.EMPTY);
		};
	}

	static IGenerator terminal(ComponentContents object) {
		return (_, _) -> List.of(object);
	}

	static IGenerator terminal(String object) {
		return terminal(PlainTextContents.create(object));
	}

	static IGenerator word(IGenerator... parts) {
		return (random, params) -> {
			List<ComponentContents> results = new ArrayList<>();

			for (IGenerator part : parts) {
				List<ComponentContents> result = part.generate(random, params);
				if (hasContents(result))
					results.addAll(result);
			}

			return flatten(results);
		};
	}

	List<ComponentContents> generate(RandomSource random, Map<String, ComponentContents> params);
}
