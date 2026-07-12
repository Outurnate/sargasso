/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.client.GlitchBlockEntityRenderer;
import com.outurnate.sargasso.client.HeadGearRenderLayer;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalStandaloneModels;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;

@Mod(value = SuperSargassoSea.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class SuperSargassoSeaClient {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerModelType type : event.getSkins()) {
            AvatarRenderer<AbstractClientPlayer> playerRenderer = event.getPlayerRenderer(type);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new HeadGearRenderLayer(playerRenderer));
            }
        }
    }

    /*
     * @SubscribeEvent public static void
     * registerClientExtensions(RegisterClientExtensionsEvent event) {
     * event.registerItem(new HatClientExtensions(), LocalItems.TEST_HAT); }
     */

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(LocalBlockEntities.GLITCH.get(), GlitchBlockEntityRenderer::new);
        event.registerEntityRenderer(LocalEntities.LIGHTNING_BOTTLE.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerStandaloneModels(ModelEvent.RegisterStandalone event) {
        event.register(
            LocalStandaloneModels.FOX_EARS,
            SimpleUnbakedStandaloneModel
                .simpleModelWrapper(Identifier.parse(LocalStandaloneModels.FOX_EARS.getName())));
    }

    public SuperSargassoSeaClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
