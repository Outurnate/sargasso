/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.entity.GlitchBlockEntity;
import com.outurnate.sargasso.block.entity.ReinforcedStarMetalBlockEntity;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister
        .create(Registries.BLOCK_ENTITY_TYPE, SuperSargassoSea.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GlitchBlockEntity>> GLITCH = REGISTRY
        .register(
            "glitch",
            () -> new BlockEntityType<>(
                GlitchBlockEntity::new,
                false,
                LocalBlocks.GLITCH.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShockTherapistBlockEntity>> SHOCK_THERAPIST = REGISTRY
        .register(
            "shock_therapist",
            () -> new BlockEntityType<>(
                ShockTherapistBlockEntity::new,
                false,
                LocalBlocks.SHOCK_THERAPIST.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ReinforcedStarMetalBlockEntity>> REINFORCED_STAR_METAL = REGISTRY
        .register(
            "reinforced_star_metal",
            () -> new BlockEntityType<>(
                ReinforcedStarMetalBlockEntity::new,
                false,
                LocalBlocks.REINFORCED_STARMETAL_BLOCK.get()));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
