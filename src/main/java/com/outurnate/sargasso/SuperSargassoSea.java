/* (C)2026 */
package com.outurnate.sargasso;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalConsumeEffects;
import com.outurnate.sargasso.registry.LocalCreativeTabs;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalLootItemFunctions;
import com.outurnate.sargasso.registry.LocalMobEffects;
import com.outurnate.sargasso.registry.LocalParticleTypes;
import com.outurnate.sargasso.registry.LocalPotions;
import com.outurnate.sargasso.registry.LocalRecipeSerializers;
import com.outurnate.sargasso.registry.LocalSoundEvents;
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
        // LocalRecipeTypes.register(modEventBus); // might be removed
        LocalRecipeSerializers.register(modEventBus);
    }
}
