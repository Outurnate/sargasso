package com.outurnate.sargasso.registry;

import com.google.common.collect.ImmutableSet;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.repository.LocalTradeSets;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalVillagerProfessions {
    public static final DeferredRegister<VillagerProfession> REGISTRY = DeferredRegister
        .create(Registries.VILLAGER_PROFESSION, SuperSargassoSea.MODID);

    public static final DeferredHolder<VillagerProfession, VillagerProfession> SCAVENGER = REGISTRY.register(
        "scavenger",
        () -> {
            ResourceKey<VillagerProfession> name = ResourceKey
                .create(Registries.VILLAGER_PROFESSION, SuperSargassoSea.ID("scavenger"));
            return new VillagerProfession(
                Component.translatable(
                    "entity." + name.identifier().getNamespace() + ".villager."
                        + name.identifier().getPath()),
                poiType -> poiType.is(LocalPoiTypes.SCAVENGER.getKey()),
                poiType -> poiType.is(LocalPoiTypes.SCAVENGER.getKey()),
                ImmutableSet.of(),
                ImmutableSet.of(),
                SoundEvents.VILLAGER_WORK_ARMORER, // TODO
                LocalTradeSets.SCAVENGER);
        });

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
