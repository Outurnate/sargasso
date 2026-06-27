/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class LocalStructureTemplatePoolsProvider {
    public static final ResourceKey<StructureTemplatePool> LIBRARY = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("library"));

    public static void provide(BootstrapContext<StructureTemplatePool> bootstrap) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolsRegistry = bootstrap
            .lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = structureTemplatePoolsRegistry.getOrThrow(Pools.EMPTY);

        bootstrap.register(
            LIBRARY,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(SinglePoolElement.single(SuperSargassoSea.MODID + ":bookshelf_test"), 1)),
                StructureTemplatePool.Projection.RIGID));
    }
}
