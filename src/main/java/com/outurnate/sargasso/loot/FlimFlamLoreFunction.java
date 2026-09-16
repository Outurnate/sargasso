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
package com.outurnate.sargasso.loot;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.*;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

// concept originally from OpenBlocks
// https://github.com/OpenMods/OpenBlocks
// ported under MIT license
public class FlimFlamLoreFunction extends LootItemConditionalFunction {
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final MapCodec<FlimFlamLoreFunction> MAP_CODEC = RecordCodecBuilder.mapCodec(
			i -> commonFields(i)
					.and(LootContext.EntityTarget.CODEC.optionalFieldOf("entity").forGetter(f -> f.resolutionContext))
					.apply(i, FlimFlamLoreFunction::new));

	@SuppressWarnings("null")
	private static Component generate(
			RandomSource random,
			Map<String, ComponentContents> params) {
		try {
			return FlimFlamLore.INSTANCE.generate(random, params).stream()
					.map(MutableComponent::create)
					.reduce(Component.empty(), MutableComponent::append);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			throw e;
		}
	}

	public static LootItemConditionalFunction.Builder<?> setFlimFlam() {
		return simpleBuilder(conditions -> new FlimFlamLoreFunction(conditions, Optional.empty()));
	}

	private final Optional<LootContext.EntityTarget> resolutionContext;

	protected FlimFlamLoreFunction(
			List<LootItemCondition> predicates,
			Optional<LootContext.EntityTarget> resolutionContext) {
		super(predicates);
		this.resolutionContext = resolutionContext;
	}

	@Override
	public @NonNull MapCodec<FlimFlamLoreFunction> codec() {
		return MAP_CODEC;
	}

	@Override
	public @NonNull ItemStack run(@NonNull ItemStack itemStack, @NonNull LootContext context) {
		try {
			Map<String, ComponentContents> params = new HashMap<>();
			if (context.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Player player) {
				params.put("player", player.getName().getContents());
			}
			params.put("item", itemStack.getItemName().getContents());
			itemStack.update(
					DataComponents.LORE,
					ItemLore.EMPTY,
					oldLore -> new ItemLore(
							this.updateLore(
									oldLore,
									Collections.singletonList(generate(context.getRandom(), params)),
									context)));
			return itemStack;
		} catch (Exception e) {
			LOGGER.error(e.toString());
			throw e;
		}
	}

	private List<Component> updateLore(
			@Nullable ItemLore itemLore,
			List<Component> lore,
			LootContext context) {
		if (itemLore == null && lore.isEmpty()) {
			return List.of();
		} else {
			UnaryOperator<Component> resolver = SetNameFunction
					.createResolver(context, this.resolutionContext.orElse(null));
			return lore.stream().map(resolver).toList();
		}
	}
}
