package com.outurnate.sargasso.datagen.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.Arrays;
import java.util.Optional;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;

public abstract class LootProvider implements LootTableSubProvider {
    protected static LootItem.Builder<?> generateLootItemCustomPotion(
        String name,
        int red,
        int green,
        int blue,
        TranslatableContents lore,
        MobEffectInstance... effects) {
        return LootItem.lootTableItem(Items.POTION)
            .apply(
                SetComponentsFunction.setComponent(
                    DataComponents.POTION_CONTENTS,
                    new PotionContents(
                        Optional.empty(),
                        Optional.of(ARGB.color(red, green, blue)),
                        Arrays.asList(effects),
                        Optional.of(name))))
            .apply(
                SetLoreFunction.setLore()
                    .addLine(MutableComponent.create(lore)))
            .apply(
                SetComponentsFunction.setComponent(
                    DataComponents.TOOLTIP_DISPLAY,
                    TooltipDisplay.DEFAULT
                        .withHidden(DataComponents.POTION_CONTENTS, true)));
    }

    protected static TranslatableContents l(String name) {
        return new TranslatableContents(
            "lore." + SuperSargassoSea.MODID + "." + name,
            null,
            new Object[0]);
    }

    protected static TranslatableContents n(String name) {
        return new TranslatableContents(
            "item." + SuperSargassoSea.MODID + ".custom." + name,
            null,
            new Object[0]);
    }

    protected final HolderLookup.Provider lookupProvider;

    protected final HolderGetter<TrimMaterial> trimMaterialProvider;

    protected final HolderGetter<TrimPattern> trimPatternProvider;
    protected final HolderLookup.RegistryLookup<Enchantment> enchantments;

    protected LootProvider(HolderLookup.Provider lookupProvider) {
        this.lookupProvider = lookupProvider;
        this.trimMaterialProvider = this.lookupProvider.lookupOrThrow(Registries.TRIM_MATERIAL);
        this.trimPatternProvider = this.lookupProvider.lookupOrThrow(Registries.TRIM_PATTERN);
        this.enchantments = this.lookupProvider.lookupOrThrow(Registries.ENCHANTMENT);
    }
}
