/* (C)2026 */
package com.outurnate.sargasso;

import com.mojang.datafixers.util.Either;
import com.outurnate.sargasso.client.ElectricMineModel;
import com.outurnate.sargasso.client.ElectricMineRenderer;
import com.outurnate.sargasso.client.FromCosmeticItemTintSource;
import com.outurnate.sargasso.client.GlitchBlockEntityRenderer;
import com.outurnate.sargasso.client.RedstoneBugRenderer;
import com.outurnate.sargasso.client.ShockTherapistEntityRenderer;
import com.outurnate.sargasso.client.SparkParticle;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalParticleTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SuperSargassoSea.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class SuperSargassoSeaClient {
    @SubscribeEvent
    public static void registerItemTintSources(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(SuperSargassoSea.ID("from_cosmetic"), FromCosmeticItemTintSource.MAP_CODEC);
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
        event.registerEntityRenderer(LocalEntities.REDSTONE_EMP.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(LocalEntities.ELECTRIC_MINE.get(), ElectricMineRenderer::new);
        event.registerEntityRenderer(LocalEntities.REDSTONE_BUG.get(), RedstoneBugRenderer::new);
    }

    @SubscribeEvent
    public static void registerTooltipAppenders(RenderTooltipEvent.GatherComponents event) {
        if (event.getItemStack()
            .get(LocalDataComponentTypes.COSMETIC_ITEM) instanceof ItemStackTemplate cosmetic) {
            event.getTooltipElements().add(Either.left(cosmetic.create().getDisplayName()));
        }
    }

    public SuperSargassoSeaClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
