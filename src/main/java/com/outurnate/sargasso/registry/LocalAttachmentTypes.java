/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import java.time.Instant;
import java.util.function.Supplier;

import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LocalAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister
        .create(NeoForgeRegistries.ATTACHMENT_TYPES, SuperSargassoSea.MODID);

    public static final Supplier<AttachmentType<Instant>> BREAD_EATEN = REGISTRY.register(
        "bread_eaten",
        () -> AttachmentType.builder(() -> Instant.now())
            .serialize(ExtraCodecs.INSTANT_ISO8601.fieldOf("bread_eaten"))
            .build());

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
