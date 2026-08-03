package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalAdvancements;
import com.outurnate.sargasso.registry.LocalDimensions;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.criterion.ChangeDimensionTrigger;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

public class LocalAdvancementProvider extends AdvancementProvider {
    public static class DefaultAdvancementSubProvider implements AdvancementSubProvider {
        private static final Map<String, String> englishTranslations = new HashMap<>();

        private AdvancementHolder builder(
            Identifier name,
            String title,
            String description,
            ItemStackTemplate icon,
            Function<Advancement.Builder, Advancement.Builder> build) {
            String titleKey = "advancements." + SuperSargassoSea.MODID + "." + name.getPath() + ".title";
            String descriptionKey = "advancements." + SuperSargassoSea.MODID + "." + name.getPath()
                + ".description";
            englishTranslations.put(titleKey, title);
            englishTranslations.put(descriptionKey, description);
            Advancement.Builder init = Advancement.Builder.advancement()
                .display(
                    icon,
                    Component.translatable(titleKey),
                    Component.translatable(descriptionKey),
                    SuperSargassoSea.ID("block/flotsam"),
                    AdvancementType.TASK,
                    true,
                    true,
                    false);
            build.apply(init);
            AdvancementHolder advancementholder = init.build(name);
            return advancementholder;
        }

        @Override
        public void generate(Provider registries, Consumer<AdvancementHolder> output) {
            AdvancementHolder enter = builder(
                LocalAdvancements.ENTER,
                "The Super Sargasso Sea",
                "Not all those who wander are lost...but you sure are",
                new ItemStackTemplate(LocalItems.FLOTSAM),
                b -> b
                    .addCriterion(
                        "enter_sea",
                        ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(LocalDimensions.SEA)));
            AdvancementHolder leave = builder(
                LocalAdvancements.LEAVE,
                "Through the Nether",
                "Twisting, turning...",
                new ItemStackTemplate(Items.OBSIDIAN),
                b -> b
                    .parent(enter)
                    .addCriterion(
                        "leave_sea",
                        ChangeDimensionTrigger.TriggerInstance.changedDimensionFrom(LocalDimensions.SEA)));
            AdvancementHolder toast = builder(
                LocalAdvancements.TOAST,
                "Time Travel!",
                "Experience a temporal anomaly",
                new ItemStackTemplate(LocalItems.TOASTER.get()),
                b -> b
                    .parent(enter)
                    .addCriterion(
                        "impossible",
                        CriteriaTriggers.IMPOSSIBLE
                            .createCriterion(new ImpossibleTrigger.TriggerInstance())));
            AdvancementHolder pylon = builder(
                LocalAdvancements.PYLON,
                "Groove Crusader",
                "Acquire a cool hat",
                new ItemStackTemplate(LocalItems.PYLON.get()),
                b -> b
                    .parent(enter)
                    .addCriterion(
                        "pylon",
                        InventoryChangeTrigger.TriggerInstance.hasItems(LocalItems.PYLON.get())));
            output.accept(enter);
            output.accept(leave);
            output.accept(toast);
            output.accept(pylon);
        }
    }

    public static Map<String, String> getEnglishTranslations() {
        return DefaultAdvancementSubProvider.englishTranslations;
    }

    public LocalAdvancementProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, List.of(new DefaultAdvancementSubProvider()));
    }
}
