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
package com.outurnate.sargasso.datagen.util;

import com.mojang.serialization.Lifecycle;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.NonNull;

public class PatchingBootstrapContext<T> implements BootstrapContext<T> {
	private final BootstrapContext<T> inner;
	private final Map<ResourceKey<T>, Function<T, T>> transformers = new HashMap<>();

	public PatchingBootstrapContext(BootstrapContext<T> inner) {
		this.inner = inner;
	}

	public void addTransformer(ResourceKey<T> key, Function<T, T> transformer) {
		this.transformers.put(key, transformer);
	}

	@Override
	public <S> @NonNull Optional<HolderLookup<S>> holderLookup(@NonNull ResourceKey<? extends Registry<? extends S>> registry) {
		return inner.holderLookup(registry);
	}

	@Override
	public <S> @NonNull HolderGetter<S> lookup(@NonNull ResourceKey<? extends Registry<? extends S>> key) {
		return inner.lookup(key);
	}

	@Override
	public @NonNull Reference<T> register(@NonNull ResourceKey<T> key, @NonNull T value, @NonNull Lifecycle lifecycle) {
		if (transformers.get(key) instanceof Function<T, T> transformer) {
			return this.inner.register(key, transformer.apply(value), lifecycle);
		} else {
			return null;
		}
	}
}
