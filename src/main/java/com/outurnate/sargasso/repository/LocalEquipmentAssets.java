/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.repository;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class LocalEquipmentAssets {
	private static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey
			.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));

	public static final ResourceKey<EquipmentAsset> STUDDED_LEATHER = ResourceKey
			.create(ROOT_ID, SuperSargassoSea.ID("studded_leather"));
}
