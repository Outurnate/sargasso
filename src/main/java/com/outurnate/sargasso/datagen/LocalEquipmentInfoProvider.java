package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalEquipmentAssets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class LocalEquipmentInfoProvider implements DataProvider {
    private final PackOutput.PathProvider path;

    public LocalEquipmentInfoProvider(PackOutput output) {
        this.path = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private void add(BiConsumer<Identifier, EquipmentClientInfo> registrar) {
        registrar.accept(
            LocalEquipmentAssets.FOX_EARS,
            EquipmentClientInfo.builder()
                .addLayers(
                    EquipmentClientInfo.LayerType.HUMANOID,
                    new EquipmentClientInfo.Layer(SuperSargassoSea.ID("fox_ears/outer")),
                    new EquipmentClientInfo.Layer(
                        SuperSargassoSea.ID("fox_ears/outer_overlay"),
                        Optional
                            .of(new EquipmentClientInfo.Dyeable(Optional.of(ARGB.color(255, 255, 255, 255)))),
                        false))
                .build());
    }

    @Override
    public String getName() {
        return "Equipment Client Infos: " + SuperSargassoSea.MODID;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Map<Identifier, EquipmentClientInfo> map = new HashMap<>();
        this.add((name, info) -> {
            if (map.putIfAbsent(name, info) != null) {
                throw new IllegalStateException(
                    "Tried to register equipment client info twice for id: " + name);
            }
        });
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, this.path, map);
    }
}
