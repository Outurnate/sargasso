/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.client.ElectricMineModel;
import com.outurnate.sargasso.client.ElectricMineRenderer;
import com.outurnate.sargasso.client.GlitchBlockEntityRenderer;
import com.outurnate.sargasso.client.HeadGearRenderLayer;
import com.outurnate.sargasso.client.ShockTherapistEntityRenderer;
import com.outurnate.sargasso.client.SparkParticle;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalParticleTypes;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SuperSargassoSea.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class SuperSargassoSeaClient {
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerModelType type : event.getSkins()) {
            AvatarRenderer<AbstractClientPlayer> playerRenderer = event.getPlayerRenderer(type);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new HeadGearRenderLayer<>(playerRenderer));
            }
        }
        for (EntityType<?> entityType : event.getEntityTypes()) {
            EntityRenderer<?, ?> renderer = event.getRenderer(entityType);
            if (renderer instanceof AvatarRenderer<?> avatarRenderer) {
                avatarRenderer.addLayer(new HeadGearRenderLayer<>(avatarRenderer));
            }
            if (renderer instanceof ArmorStandRenderer armorStandRenderer) {
                armorStandRenderer.addLayer(new HeadGearRenderLayer<>(armorStandRenderer));
            }
        }
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ElectricMineModel.LAYER_LOCATION, ElectricMineModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(LocalParticleTypes.SPARK.get(), SparkParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(LocalBlockEntities.GLITCH.get(), GlitchBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(
            LocalBlockEntities.SHOCK_THERAPIST.get(),
            ShockTherapistEntityRenderer::new);
        event.registerEntityRenderer(LocalEntities.LIGHTNING_BOTTLE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(LocalEntities.ELECTRIC_MINE.get(), ElectricMineRenderer::new);
    }

    public SuperSargassoSeaClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
