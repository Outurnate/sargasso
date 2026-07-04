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
    public static final ResourceKey<StructureProcessorList> HEAVY_GLITCH = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("heavy_glitch"));
    public static final ResourceKey<StructureProcessorList> MEDIUM_GLITCH = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("medium_glitch"));
    public static final ResourceKey<StructureProcessorList> MILD_GLITCH = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("mild_glitch"));

    private static StructureProcessorList buildGlitch(float prob) {
        return new StructureProcessorList(
            List.of(
                new RuleProcessor(
                    List.of(
                        new ProcessorRule(
                            new RandomBlockMatchTest(Blocks.STONE_BRICKS, prob),
                            AlwaysTrueTest.INSTANCE,
                            LocalBlocks.GLITCH.get().defaultBlockState())))));
    }

    public static void provide(BootstrapContext<StructureProcessorList> bootstrap) {
        bootstrap.register(
            HEAVY_GLITCH,
            buildGlitch(0.4F));
        bootstrap.register(
            MEDIUM_GLITCH,
            buildGlitch(0.2F));
        bootstrap.register(
            MILD_GLITCH,
            buildGlitch(0.1F));
    }
}
