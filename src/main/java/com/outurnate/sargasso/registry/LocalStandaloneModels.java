package com.outurnate.sargasso.registry;

import com.mojang.math.OctahedralGroup;
import com.mojang.math.Transformation;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.client.HeadGearClientExtensions;
import com.outurnate.sargasso.client.HeadGearRenderLayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.model.ComposedModelState;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class LocalStandaloneModels {
    public static final StandaloneModelKey<BlockStateModelPart> BLACK_FOX_EARS;
    public static final StandaloneModelKey<BlockStateModelPart> TWO_COLOR_FOX_EARS;
    static {
        BLACK_FOX_EARS = new StandaloneModelKey<>(SuperSargassoSea.ID("hat/black_fox_ears")::toString);
        TWO_COLOR_FOX_EARS = new StandaloneModelKey<>(
            SuperSargassoSea.ID("hat/two_color_fox_ears")::toString);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new HeadGearClientExtensions(), LocalItems.BLACK_FOX_EARS);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {
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

    private static void registerSimpleModel(
        ModelEvent.RegisterStandalone event,
        StandaloneModelKey<BlockStateModelPart> key) {
        event.register(
            key,
            SimpleUnbakedStandaloneModel
                .simpleModelWrapper(
                    Identifier.parse(key.getName()),
                    new ComposedModelState(
                        BlockModelRotation.get(OctahedralGroup.ROT_180_FACE_XY),
                        new Transformation(new Vector3f(1.0F, 1.0F, 0.0F), null, null, null))));
    }

    @SubscribeEvent
    public static void registerStandaloneModels(ModelEvent.RegisterStandalone event) {
        registerSimpleModel(event, LocalStandaloneModels.BLACK_FOX_EARS);
        event.register(
            LocalStandaloneModels.BLACK_FOX_EARS,
            SimpleUnbakedStandaloneModel
                .simpleModelWrapper(
                    Identifier.parse(LocalStandaloneModels.BLACK_FOX_EARS.getName()),
                    new ComposedModelState(
                        BlockModelRotation.get(OctahedralGroup.ROT_180_FACE_XY),
                        new Transformation(new Vector3f(1.0F, 1.0F, 0.0F), null, null, null))));
    }
}
