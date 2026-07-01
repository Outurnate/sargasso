/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.mojang.serialization.Codec;
import com.outurnate.sargasso.SuperSargassoSea;
import java.time.Instant;
import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LocalAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister
        .create(NeoForgeRegistries.ATTACHMENT_TYPES, SuperSargassoSea.MODID);

    public static final Codec<Instant> INSTANT_CODEC = Codec.LONG
        .xmap(Instant::ofEpochMilli, Instant::toEpochMilli);

    public static final Supplier<AttachmentType<Instant>> BREAD_EATEN = REGISTRY.register(
        "bread_eaten",
        () -> AttachmentType.builder(() -> Instant.now()).serialize(INSTANT_CODEC.fieldOf("bread_eaten"))
            .build());

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
