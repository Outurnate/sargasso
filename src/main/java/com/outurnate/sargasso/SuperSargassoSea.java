/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalCreativeTabs;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalLootItemFunctions;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(SuperSargassoSea.MODID)
public class SuperSargassoSea {
    public static final String MODID = "sargasso";

    public SuperSargassoSea(IEventBus modEventBus, ModContainer modContainer) {
        LocalBlocks.register(modEventBus);
        LocalItems.register(modEventBus);
        LocalLootItemFunctions.register(modEventBus);
        LocalCreativeTabs.register(modEventBus);
        LocalBlockEntities.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
