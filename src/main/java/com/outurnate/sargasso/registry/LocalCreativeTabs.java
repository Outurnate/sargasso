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
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalCreativeTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, SuperSargassoSea.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = REGISTRY.register(
			"tab",
			() -> CreativeModeTab.builder().title(Component.translatable("itemGroup.sargasso"))
					.withTabsBefore(CreativeModeTabs.COMBAT)
					.icon(() -> LocalItems.LIGHTNING_BOTTLE.get().getDefaultInstance())
					.displayItems((_, output) -> {
						for (DeferredHolder<Item, ? extends Item> item : LocalItems.REGISTRY.getEntries()) {
							output.accept(item.get());
						}
					}).build());

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}
}
