package com.outurnate.sargasso.datagen.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalPotions;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ShipwreckLoot extends LootProvider {
    public static final ResourceKey<LootTable> SHIPWRECK = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/shipwreck")); // TODO

    public static final TranslatableContents LORE_BHJ = l("bhj");
    public static final String POTION_BHJ = "bhj";

    public ShipwreckLoot(Provider lookupProvider) {
        super(lookupProvider);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
        output.accept(
            SHIPWRECK,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1, 2))
                    .add(
                        LootItem.lootTableItem(Items.POTION)
                            .apply(SetPotionFunction.setPotion(LocalPotions.HEAD_EXPLOSION))
                            .setWeight(1))
                    .add(LootItem.lootTableItem(LocalItems.BEDROCK_CREAM).setWeight(10))
                    .add(
                        generateLootItemCustomPotion(
                            POTION_BHJ,
                            255,
                            60,
                            0,
                            LORE_BHJ,
                            new MobEffectInstance(
                                MobEffects.SPEED,
                                36000,
                                2),
                            new MobEffectInstance(
                                MobEffects.STRENGTH,
                                36000,
                                3),
                            new MobEffectInstance(
                                MobEffects.REGENERATION,
                                36000,
                                2),
                            new MobEffectInstance(
                                MobEffects.SLOWNESS,
                                36000,
                                3),
                            new MobEffectInstance(
                                MobEffects.WEAKNESS,
                                36000,
                                2),
                            new MobEffectInstance(
                                MobEffects.POISON,
                                36000,
                                1)).setWeight(1))));
    }
}
