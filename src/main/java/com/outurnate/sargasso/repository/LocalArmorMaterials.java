/* (C)2026 */
package com.outurnate.sargasso.repository;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class LocalArmorMaterials {
    public static final ArmorMaterial STUDDED_LEATHER = new ArmorMaterial(
        5,
        makeDefense(1, 2, 3, 1, 3),
        15,
        SoundEvents.ARMOR_EQUIP_GENERIC,
        1.0F,
        0.0F,
        ItemTags.REPAIRS_LEATHER_ARMOR,
        EquipmentAssets.LEATHER);

    private static Map<ArmorType, Integer> makeDefense(int boots, int legs, int chest, int helm, int body) {
        return Maps.newEnumMap(
            Map.of(
                ArmorType.BOOTS,
                boots,
                ArmorType.LEGGINGS,
                legs,
                ArmorType.CHESTPLATE,
                chest,
                ArmorType.HELMET,
                helm,
                ArmorType.BODY,
                body));
    }
}
