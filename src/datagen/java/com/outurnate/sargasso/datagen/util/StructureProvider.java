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

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public abstract class StructureProvider {
	protected static class PoolBuilder {
		private final List<ResourceKey<StructureProcessorList>> processorLists;
		private final String name;
		private final int weight;

		private PoolBuilder(
				String name,
				int weight,
				List<ResourceKey<StructureProcessorList>> processorLists) {
			this.processorLists = processorLists;
			this.name = name;
			this.weight = weight;
		}

		private List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> construct(
				BootstrapContext<StructureTemplatePool> bootstrap,
				ResourceKey<StructureProcessorList> defaultProcessorList) {
			String qualifiedName = SuperSargassoSea.MODID + ":" + this.name;
			HolderGetter<StructureProcessorList> processorListsLookup = bootstrap
					.lookup(Registries.PROCESSOR_LIST);
			if (defaultProcessorList != null) {
				return List.of(
						Pair.of(
								SinglePoolElement
										.single(qualifiedName, processorListsLookup.getOrThrow(defaultProcessorList)),
								this.weight));
			} else {
				if (this.processorLists.isEmpty()) {
					return List.of(Pair.of(SinglePoolElement.single(qualifiedName), this.weight));
				} else if (this.processorLists.size() == 1) {
					return List.of(
							Pair.of(
									SinglePoolElement.single(
											qualifiedName,
											processorListsLookup.getOrThrow(this.processorLists.get(0))),
									this.weight));
				} else {
					return this.processorLists.stream().map(
							processorList -> Pair.<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>of(
									SinglePoolElement
											.single(qualifiedName, processorListsLookup.getOrThrow(processorList)),
									this.weight))
							.toList();
				}
			}
		}
	}

	protected static class TemplatePoolBuilder {
		private final BootstrapContext<StructureTemplatePool> bootstrap;

		private TemplatePoolBuilder(BootstrapContext<StructureTemplatePool> bootstrap) {
			this.bootstrap = bootstrap;
		}

		public void register(ResourceKey<StructureTemplatePool> key, PoolBuilder... templates) {
			register(key, Pools.EMPTY, templates);
		}

		public void register(
				ResourceKey<StructureTemplatePool> key,
				ResourceKey<StructureTemplatePool> fallback,
				PoolBuilder... templates) {
			register(key, fallback, null, templates);
		}

		public void register(
				ResourceKey<StructureTemplatePool> key,
				ResourceKey<StructureTemplatePool> fallback,
				ResourceKey<StructureProcessorList> processorList,
				PoolBuilder... templates) {
			HolderGetter<StructureTemplatePool> templatePools = bootstrap.lookup(Registries.TEMPLATE_POOL);
			this.bootstrap.register(
					key,
					new StructureTemplatePool(
							templatePools.getOrThrow(fallback),
							Arrays.stream(templates)
									.flatMap(template -> template.construct(bootstrap, processorList).stream()).toList(),
							StructureTemplatePool.Projection.RIGID));
		}

		public void register(ResourceKey<StructureTemplatePool> key, String template) {
			register(key, entry(template));
		}
	}

	protected static PoolBuilder entry(String name) {
		return new PoolBuilder(name, 1, List.of());
	}

	protected static PoolBuilder entry(String name, int weight) {
		return new PoolBuilder(name, weight, List.of());
	}

	protected static PoolBuilder entry(
			String name,
			int weight,
			List<ResourceKey<StructureProcessorList>> processorLists) {
		return new PoolBuilder(name, weight, processorLists);
	}

	protected static PoolBuilder entry(
			String name,
			int weight,
			ResourceKey<StructureProcessorList> processorList) {
		return new PoolBuilder(name, weight, List.of(processorList));
	}

	protected static PoolBuilder entry(
			String name,
			List<ResourceKey<StructureProcessorList>> processorLists) {
		return new PoolBuilder(name, 1, processorLists);
	}

	protected static PoolBuilder entry(String name, ResourceKey<StructureProcessorList> processorList) {
		return new PoolBuilder(name, 1, List.of(processorList));
	}

	protected abstract void provide(TemplatePoolBuilder bootstrap);

	public final void provideTemplatePools(BootstrapContext<StructureTemplatePool> bootstrap) {
		provide(new TemplatePoolBuilder(bootstrap));
	}
}
