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
package com.outurnate.sargasso;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalConsumeEffects;
import com.outurnate.sargasso.registry.LocalCreativeTabs;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalEntityDataSerializers;
import com.outurnate.sargasso.registry.LocalFeatures;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalLootItemFunctions;
import com.outurnate.sargasso.registry.LocalMobEffects;
import com.outurnate.sargasso.registry.LocalParticleTypes;
import com.outurnate.sargasso.registry.LocalPoiTypes;
import com.outurnate.sargasso.registry.LocalPotions;
import com.outurnate.sargasso.registry.LocalRecipeSerializers;
import com.outurnate.sargasso.registry.LocalSoundEvents;
import com.outurnate.sargasso.registry.LocalVillagerProfessions;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(SuperSargassoSea.MODID)
public class SuperSargassoSea {
	public static final String MODID = "sargasso";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static Identifier ID(String id) {
		return Identifier.fromNamespaceAndPath(MODID, id);
	}

	public SuperSargassoSea(IEventBus modEventBus, ModContainer modContainer) {
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
		modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
		LocalBlocks.register(modEventBus);
		LocalItems.register(modEventBus);
		LocalLootItemFunctions.register(modEventBus);
		LocalCreativeTabs.register(modEventBus);
		LocalBlockEntities.register(modEventBus);
		LocalEntities.register(modEventBus);
		LocalSoundEvents.register(modEventBus);
		LocalMobEffects.register(modEventBus);
		LocalPotions.register(modEventBus);
		LocalAttachmentTypes.register(modEventBus);
		LocalDataComponentTypes.register(modEventBus);
		LocalConsumeEffects.register(modEventBus);
		LocalParticleTypes.register(modEventBus);
		LocalRecipeSerializers.register(modEventBus);
		LocalFeatures.register(modEventBus);
		LocalVillagerProfessions.register(modEventBus);
		LocalPoiTypes.register(modEventBus);
		LocalEntityDataSerializers.register(modEventBus);
	}
}
