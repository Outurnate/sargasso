package com.outurnate.sargasso.registry;

import com.google.common.collect.ImmutableSet;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.repository.LocalTradeSets;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalVillagerProfessions {
    public static final DeferredRegister<VillagerProfession> REGISTRY = DeferredRegister
        .create(Registries.VILLAGER_PROFESSION, SuperSargassoSea.MODID);

    public static final DeferredHolder<VillagerProfession, VillagerProfession> SCAVENGER_CIRCUITS = create(
        "scavenger_circuits",
        LocalPoiTypes.SCAVENGER_CIRCUITS.getKey());
    public static final DeferredHolder<VillagerProfession, VillagerProfession> SCAVENGER_CLOCKSPRINGS = create(
        "scavenger_clocksprings",
        LocalPoiTypes.SCAVENGER_CLOCKSPRINGS.getKey());
    public static final DeferredHolder<VillagerProfession, VillagerProfession> SCAVENGER_BOLTS = create(
        "scavenger_bolts",
        LocalPoiTypes.SCAVENGER_BOLTS.getKey());
    public static final DeferredHolder<VillagerProfession, VillagerProfession> SCAVENGER_BUCKETS = create(
        "scavenger_buckets",
        LocalPoiTypes.SCAVENGER_BUCKETS.getKey());
    public static final DeferredHolder<VillagerProfession, VillagerProfession> SCAVENGER_COGS = create(
        "scavenger_cogs",
        LocalPoiTypes.SCAVENGER_COGS.getKey());
    public static final DeferredHolder<VillagerProfession, VillagerProfession> SCAVENGER_WIRES = create(
        "scavenger_wires",
        LocalPoiTypes.SCAVENGER_WIRES.getKey());

    private static DeferredHolder<VillagerProfession, VillagerProfession> create(
        String profession,
        ResourceKey<PoiType> worksite) {
        return REGISTRY.register(
            profession,
            () -> {
                ResourceKey<VillagerProfession> name = ResourceKey
                    .create(Registries.VILLAGER_PROFESSION, SuperSargassoSea.ID(profession));
                return new VillagerProfession(
                    Component.translatable(
                        "entity." + name.identifier().getNamespace() + ".villager."
                            + name.identifier().getPath()),
                    poiType -> poiType.is(worksite),
                    poiType -> poiType.is(worksite),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_ARMORER, // TODO
                    LocalTradeSets.ALL_TRADESETS.get(profession));
            });
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
