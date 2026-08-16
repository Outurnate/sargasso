package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

public class LocalPaintingVariantsProvider {
    public static final ResourceKey<PaintingVariant> GENE = ResourceKey
        .create(Registries.PAINTING_VARIANT, SuperSargassoSea.ID("gene"));

    public static void provide(BootstrapContext<PaintingVariant> bootstrap) {
        bootstrap.register(
            GENE,
            new PaintingVariant(
                3,
                4,
                GENE.identifier(),
                Optional.of(
                    Component.translatable(GENE.identifier().toLanguageKey("painting", "title"))
                        .withStyle(ChatFormatting.YELLOW)),
                Optional.empty()));
    }
}
