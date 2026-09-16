/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.entity.BetaChestBlockEntity;
import com.outurnate.sargasso.block.entity.GlitchBlockEntity;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
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
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChestBlockEntity>> BETA_CHEST = REGISTRY
			.register(
					"beta_chest",
					() -> new BlockEntityType<>(
							BetaChestBlockEntity::new,
							false,
							LocalBlocks.BETA_CHEST.get()));

	public static void register(IEventBus modEventBus) {
		REGISTRY.register(modEventBus);
	}
}
