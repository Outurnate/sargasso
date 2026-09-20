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
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public class LocalPotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister
			.create(Registries.POTION, SuperSargassoSea.MODID);

	public static final Holder<Potion> HEAD_EXPLOSION = REGISTRY.register(
			"head_explosion",
			registryName -> new Potion(
					registryName.getPath(),
					new MobEffectInstance(LocalMobEffects.HEAD_EXPLOSION, 600, 0)));

	public static final Holder<Potion> LONG_HEAD_EXPLOSION = REGISTRY.register(
			"long_head_explosion",
			registryName -> new Potion(
					registryName.getPath(),
					new MobEffectInstance(LocalMobEffects.HEAD_EXPLOSION, 1200, 0)));

	public static final Holder<Potion> STRONG_HEAD_EXPLOSION = REGISTRY.register(
			"strong_head_explosion",
			registryName -> new Potion(
					registryName.getPath(),
					new MobEffectInstance(LocalMobEffects.HEAD_EXPLOSION, 600, 1)));

	public static final Holder<Potion> EXTRA_STRONG_HEAD_EXPLOSION = REGISTRY.register(
			"extra_strong_head_explosion",
			registryName -> new Potion(
					registryName.getPath(),
					new MobEffectInstance(LocalMobEffects.HEAD_EXPLOSION, 600, 2)));

	@SubscribeEvent
	public static void buildContents(BuildCreativeModeTabContentsEvent event) {
		movePotion(event, HEAD_EXPLOSION, LONG_HEAD_EXPLOSION, STRONG_HEAD_EXPLOSION, EXTRA_STRONG_HEAD_EXPLOSION);
	}

	private static void movePotion(BuildCreativeModeTabContentsEvent event, Holder<Potion>... potions) {
		if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			for (Holder<Potion> potion : potions) {
				event.remove(PotionContents.createItemStack(Items.POTION, potion), TabVisibility.PARENT_AND_SEARCH_TABS);
			}
			for (Holder<Potion> potion : potions) {
				event.remove(PotionContents.createItemStack(Items.SPLASH_POTION, potion), TabVisibility.PARENT_AND_SEARCH_TABS);
			}
			for (Holder<Potion> potion : potions) {
				event.remove(PotionContents.createItemStack(Items.LINGERING_POTION, potion), TabVisibility.PARENT_AND_SEARCH_TABS);
			}
			for (Holder<Potion> potion : potions) {
				event.remove(PotionContents.createItemStack(Items.TIPPED_ARROW, potion), TabVisibility.PARENT_AND_SEARCH_TABS);
			}
		} else if (event.getTabKey() == LocalCreativeTabs.TAB.getKey()) {
			for (Holder<Potion> potion : potions) {
				event.accept(PotionContents.createItemStack(Items.POTION, potion), TabVisibility.PARENT_AND_SEARCH_TABS);
			}
			for (Holder<Potion> potion : potions) {
				event.accept(PotionContents.createItemStack(Items.SPLASH_POTION, potion), TabVisibility.PARENT_AND_SEARCH_TABS);
			}
			for (Holder<Potion> potion : potions) {
				event.accept(PotionContents.createItemStack(Items.LINGERING_POTION, potion), TabVisibility.PARENT_AND_SEARCH_TABS);
			}
			for (Holder<Potion> potion : potions) {
				event.accept(PotionContents.createItemStack(Items.TIPPED_ARROW, potion), TabVisibility.PARENT_AND_SEARCH_TABS);
			}
		}
	}

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}

	@SubscribeEvent
	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		PotionBrewing.Builder builder = event.getBuilder();
		builder.addMix(
				Potions.AWKWARD,
				Items.TNT,
				HEAD_EXPLOSION);
		builder.addMix(
				HEAD_EXPLOSION,
				Items.REDSTONE,
				LONG_HEAD_EXPLOSION);
		builder.addMix(
				HEAD_EXPLOSION,
				Items.GLOWSTONE_DUST,
				STRONG_HEAD_EXPLOSION);
		builder.addMix(
				STRONG_HEAD_EXPLOSION,
				Items.GLOWSTONE_DUST,
				EXTRA_STRONG_HEAD_EXPLOSION);
		builder.addMix(
				Potions.MUNDANE,
				Items.TNT,
				LONG_HEAD_EXPLOSION);
		builder.addMix(
				Potions.THICK,
				Items.TNT,
				STRONG_HEAD_EXPLOSION);
	}
}
