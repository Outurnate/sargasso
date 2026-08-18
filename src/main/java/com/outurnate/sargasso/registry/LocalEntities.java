/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.entity.ElectricMine;
import com.outurnate.sargasso.entity.RedstoneBug;
import com.outurnate.sargasso.entity.ThrownHammer;
import com.outurnate.sargasso.entity.ThrownLightningBottle;
import com.outurnate.sargasso.entity.ThrownRedstoneEMP;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalEntities {
    public static final DeferredRegister.Entities REGISTRY = DeferredRegister
        .createEntities(SuperSargassoSea.MODID);

    public static final Supplier<EntityType<ThrownLightningBottle>> LIGHTNING_BOTTLE = REGISTRY.register(
        "lightning_bottle",
        () -> EntityType.Builder.<ThrownLightningBottle>of(
            ThrownLightningBottle::new,
            MobCategory.MISC)
            .noLootTable()
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(
                ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "lightning_bottle"))));

    public static final Supplier<EntityType<ElectricMine>> ELECTRIC_MINE = REGISTRY.register(
        "electric_mine",
        () -> EntityType.Builder.<ElectricMine>of(
            ElectricMine::new,
            MobCategory.MISC)
            .noLootTable()
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(
                ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "electric_mine"))));

    public static final Supplier<EntityType<ThrownRedstoneEMP>> REDSTONE_EMP = REGISTRY.register(
        "redstone_emp",
        () -> EntityType.Builder.<ThrownRedstoneEMP>of(
            ThrownRedstoneEMP::new,
            MobCategory.MISC)
            .noLootTable()
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(
                ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "redstone_emp"))));

    public static final Supplier<EntityType<RedstoneBug>> REDSTONE_BUG = REGISTRY.register(
        "redstone_bug",
        () -> EntityType.Builder.<RedstoneBug>of(
            RedstoneBug::new,
            MobCategory.MISC)
            .noLootTable()
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(
                ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "redstone_bug"))));

    public static final Supplier<EntityType<ThrownHammer>> HAMMER = REGISTRY.register(
        "hammer",
        () -> EntityType.Builder.<ThrownHammer>of(
            ThrownHammer::new,
            MobCategory.MISC)
            .noLootTable()
            .sized(0.5F, 0.5F)
            .eyeHeight(0.13F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .build(
                ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "hammer"))));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
