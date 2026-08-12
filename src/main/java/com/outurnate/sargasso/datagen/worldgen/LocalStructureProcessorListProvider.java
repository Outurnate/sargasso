/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.List;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class LocalStructureProcessorListProvider {
    public static final ResourceKey<StructureProcessorList> REMOVE_CHESTS = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("remove_chests"));

    public static void provide(BootstrapContext<StructureProcessorList> bootstrap) {
        bootstrap.register(
            REMOVE_CHESTS,
            new StructureProcessorList(
                List.of(
                    new BlockRotProcessor(
                        HolderSet.direct(BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.CHEST)),
                        0.1F))));
    }
}
