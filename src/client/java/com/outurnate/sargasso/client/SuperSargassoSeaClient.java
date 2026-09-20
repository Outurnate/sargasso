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
package com.outurnate.sargasso.client;

import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.mojang.datafixers.util.Either;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.book.page.BookMultispotlightPage;
import com.outurnate.sargasso.client.model.ElectricMineModel;
import com.outurnate.sargasso.client.particle.BeamParticle;
import com.outurnate.sargasso.client.particle.SparkParticle;
import com.outurnate.sargasso.client.renderer.ElectricMineRenderer;
import com.outurnate.sargasso.client.renderer.GlitchBlockEntityRenderer;
import com.outurnate.sargasso.client.renderer.RedstoneBugRenderer;
import com.outurnate.sargasso.client.renderer.ShockTherapistEntityRenderer;
import com.outurnate.sargasso.client.renderer.SizeRayBeamRenderer;
import com.outurnate.sargasso.client.renderer.ThrownHammerRenderer;
import com.outurnate.sargasso.client.renderer.page.BookMultispotlightPageRenderer;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalMobEffects;
import com.outurnate.sargasso.registry.LocalParticleTypes;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SuperSargassoSea.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class SuperSargassoSeaClient {
	@SubscribeEvent
	public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
		var player = event.getPlayer();
		if (player.hasEffect(LocalMobEffects.GROW) || player.hasEffect(LocalMobEffects.SHRINK)) {
			event.setNewFovModifier(1.0F);
		}
	}

	@SubscribeEvent
	public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		event.registerItem(new HammerClientItemExtensions(), LocalItems.HAMMER.get());
	}

	@SubscribeEvent
	public static void registerColorHandlers(RegisterColorHandlersEvent.BlockTintSources event) {
		// 9bca6b
		event.register(List.of(state -> ARGB.color(0x9B, 255, 0x6B)), LocalBlocks.ALPHA_GRASS.get());
	}

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
		event.registerSpriteSet(LocalParticleTypes.BEAM.get(), BeamParticle.Provider::new);
	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(LocalBlockEntities.GLITCH.get(), GlitchBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(
				LocalBlockEntities.SHOCK_THERAPIST.get(),
				ShockTherapistEntityRenderer::new);
		event.registerEntityRenderer(LocalEntities.LIGHTNING_BOTTLE.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(LocalEntities.SPIDER_BOTTLE.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(LocalEntities.REDSTONE_EMP.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(LocalEntities.ELECTRIC_MINE.get(), ElectricMineRenderer::new);
		event.registerEntityRenderer(LocalEntities.REDSTONE_BUG.get(), RedstoneBugRenderer::new);
		event.registerEntityRenderer(LocalEntities.HAMMER.get(), ThrownHammerRenderer::new);
		event.registerEntityRenderer(LocalEntities.SIZE_RAY_BEAM.get(), SizeRayBeamRenderer::new);
	}

	@SubscribeEvent
	public static void registerTooltipAppenders(RenderTooltipEvent.GatherComponents event) {
		if (event.getItemStack()
				.get(LocalDataComponentTypes.COSMETIC_ITEM) instanceof ItemStackTemplate cosmetic) {
			event.getTooltipElements().add(
					1,
					Either.left(
							cosmetic.create().getItemName().copy()
									.setStyle(Style.EMPTY.withItalic(true).withColor(ChatFormatting.GRAY))));
		}
	}

	public SuperSargassoSeaClient(ModContainer container) {
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		PageRendererRegistry.registerPageRenderer(BookMultispotlightPage.ID, p -> new BookMultispotlightPageRenderer((BookMultispotlightPage) p));
	}
}
