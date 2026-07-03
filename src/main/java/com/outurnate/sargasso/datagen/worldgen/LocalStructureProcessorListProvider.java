/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class LocalStructureProcessorListProvider {
    public static final ResourceKey<StructureProcessorList> GLITCH = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("glitch"));

    public static void provide(BootstrapContext<StructureProcessorList> bootstrap) {
        bootstrap.register(
            GLITCH,
            new StructureProcessorList(
                List.of(
                    new RuleProcessor(
                        List.of(
                            new ProcessorRule(
                                new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.4F),
                                AlwaysTrueTest.INSTANCE,
                                LocalBlocks.GLITCH.get().defaultBlockState()))))));
    }
}
