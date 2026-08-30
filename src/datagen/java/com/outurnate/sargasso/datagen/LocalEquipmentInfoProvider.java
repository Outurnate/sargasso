package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.repository.LocalEquipmentAssets;
import java.util.function.BiConsumer;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class LocalEquipmentInfoProvider extends EquipmentAssetProvider {
    public LocalEquipmentInfoProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(
            LocalEquipmentAssets.STUDDED_LEATHER,
            EquipmentClientInfo.builder()
                .addLayers(
                    EquipmentClientInfo.LayerType.HUMANOID,
                    new EquipmentClientInfo.Layer(SuperSargassoSea.ID("studded_leather/inner")))
                .addLayers(
                    EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS,
                    new EquipmentClientInfo.Layer(SuperSargassoSea.ID("studded_leather/inner")))
                .build());
    }
}
