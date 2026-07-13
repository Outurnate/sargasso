/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.client.GlitchBlockEntityRenderer;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SuperSargassoSea.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class SuperSargassoSeaClient {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(LocalBlockEntities.GLITCH.get(), GlitchBlockEntityRenderer::new);
        event.registerEntityRenderer(LocalEntities.LIGHTNING_BOTTLE.get(), ThrownItemRenderer::new);
    }

    public SuperSargassoSeaClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
