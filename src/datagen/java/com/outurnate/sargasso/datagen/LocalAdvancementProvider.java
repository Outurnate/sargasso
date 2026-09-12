package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.loot.AdvancementLoot;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.repository.LocalAdvancements;
import com.outurnate.sargasso.repository.LocalDimensions;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
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
import net.minecraft.world.level.Level;

public class LocalAdvancementProvider extends AdvancementProvider {
    private static AdvancementHolder advancement(
        Identifier name,
        ItemStackTemplate icon,
        boolean hidden,
        Function<Advancement.Builder, Advancement.Builder> build) {
        Advancement.Builder init = Advancement.Builder.advancement()
            .display(
                icon,
                Component.translatable(name.toLanguageKey("advancements", "title")),
                Component.translatable(name.toLanguageKey("advancements", "description")),
                SuperSargassoSea.ID("block/flotsam"),
                AdvancementType.TASK,
                true,
                true,
                hidden);
        build.apply(init);
        return init.build(name);
    }

    private static AdvancementHolder advancement(
        Identifier name,
        ItemStackTemplate icon,
        Function<Advancement.Builder, Advancement.Builder> build) {
        return advancement(name, icon, false, build);
    }

    public LocalAdvancementProvider(PackOutput output, CompletableFuture<Provider> registries) {
        AdvancementHolder enter = advancement(
            LocalAdvancements.ENTER,
            new ItemStackTemplate(LocalItems.FLOTSAM),
            b -> b
                .addCriterion(
                    "enter_sea",
                    ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(LocalDimensions.SEA)));
        AdvancementHolder leave = advancement(
            LocalAdvancements.LEAVE,
            new ItemStackTemplate(Items.OBSIDIAN),
            b -> b
                .parent(enter)
                .addCriterion(
                    "leave_sea",
                    ChangeDimensionTrigger.TriggerInstance.changedDimensionFrom(LocalDimensions.SEA))
                .addCriterion(
                    "enter_nether",
                    ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.NETHER)));
        AdvancementHolder leaveOther = advancement(
            LocalAdvancements.LEAVE_OTHER,
            new ItemStackTemplate(Items.OAK_DOOR),
            true,
            b -> b
                .parent(enter)
                .addCriterion(
                    "leave_sea",
                    ChangeDimensionTrigger.TriggerInstance.changedDimensionFrom(LocalDimensions.SEA))
                .addCriterion(
                    "enter_overworld",
                    ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.OVERWORLD))
                .rewards(AdvancementRewards.Builder.loot(AdvancementLoot.ATLAS)));
        AdvancementHolder toast = advancement(
            LocalAdvancements.TOAST,
            new ItemStackTemplate(LocalItems.TOASTER.get()),
            b -> b
                .parent(enter)
                .addCriterion(
                    "impossible",
                    CriteriaTriggers.IMPOSSIBLE
                        .createCriterion(new ImpossibleTrigger.TriggerInstance())));
        AdvancementHolder pylon = advancement(
            LocalAdvancements.PYLON,
            new ItemStackTemplate(LocalItems.PYLON.get()),
            b -> b
                .parent(enter)
                .addCriterion(
                    "pylon",
                    InventoryChangeTrigger.TriggerInstance.hasItems(LocalItems.PYLON.get())));
        AdvancementHolder strike = advancement(
            LocalAdvancements.STRIKE,
            new ItemStackTemplate(LocalItems.HAMMER.get()),
            b -> b
                .parent(enter)
                .addCriterion(
                    "impossible",
                    CriteriaTriggers.IMPOSSIBLE
                        .createCriterion(new ImpossibleTrigger.TriggerInstance())));
        super(output, registries, List.of(new AdvancementSubProvider() {
            @Override
            public void generate(Provider registries, Consumer<AdvancementHolder> output) {
                output.accept(enter);
                output.accept(leave);
                output.accept(leaveOther);
                output.accept(toast);
                output.accept(pylon);
                output.accept(strike);
            }
        }));
    }
}
