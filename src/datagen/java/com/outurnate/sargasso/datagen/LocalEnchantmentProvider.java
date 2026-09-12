package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.datagen.util.PatchingBootstrapContext;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.EnchantmentDefinition;
import net.minecraft.world.item.enchantment.Enchantments;

public class LocalEnchantmentProvider {
    public static void provide(BootstrapContext<Enchantment> bootstrap) {
        HolderGetter<Item> items = bootstrap.lookup(Registries.ITEM);
        PatchingBootstrapContext<Enchantment> patchingBootstrap = new PatchingBootstrapContext<>(bootstrap);
        patchingBootstrap.addTransformer(Enchantments.LOYALTY, original -> {
            return new Enchantment(
                original.description(),
                new EnchantmentDefinition(
                    items.getOrThrow(LocalItemTagsProvider.ENCHANTABLE_LOYALTY),
                    original.definition().primaryItems(),
                    original.definition().weight(),
                    original.definition().maxLevel(),
                    original.definition().minCost(),
                    original.definition().maxCost(),
                    original.definition().anvilCost(),
                    original.definition().slots()),
                original.exclusiveSet(),
                original.effects());
        });
        Enchantments.bootstrap(patchingBootstrap);
    }
}
