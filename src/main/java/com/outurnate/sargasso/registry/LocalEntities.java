/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.entity.ThrownLightningBottle;
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

    public static final Supplier<EntityType<ThrownLightningBottle>> EGG = REGISTRY.register(
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

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
