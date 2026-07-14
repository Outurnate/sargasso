package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalDimensions;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStackTemplate;

public class LocalAdvancementProvider extends AdvancementProvider {
    private static class DefaultAdvancementSubProvider implements AdvancementSubProvider {
        @Override
        public void generate(Provider registries, Consumer<AdvancementHolder> output) {
            Advancement.Builder.advancement()
                .display(
                    new ItemStackTemplate(LocalItems.FLOTSAM),
                    Component.translatable("advancements." + SuperSargassoSea.MODID + ".enter.title"),
                    Component.translatable("advancements." + SuperSargassoSea.MODID + ".enter.description"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false)
                .addCriterion(
                    "enter_sea",
                    ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(LocalDimensions.SEA));
        }
    }

    public LocalAdvancementProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, List.of(new DefaultAdvancementSubProvider()));
    }
}
