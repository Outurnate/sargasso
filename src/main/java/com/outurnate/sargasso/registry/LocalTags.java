/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class LocalTags {
    public static final TagKey<Item> ALWAYS_LOST = TagKey
        .create(Registries.ITEM, Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "always_lost"));
}
