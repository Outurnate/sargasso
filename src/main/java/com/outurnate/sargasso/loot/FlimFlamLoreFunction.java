/* (C)2026 */
package com.outurnate.sargasso.loot;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class FlimFlamLoreFunction extends LootItemConditionalFunction {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final MapCodec<FlimFlamLoreFunction> MAP_CODEC = RecordCodecBuilder.mapCodec(
        i -> commonFields(i)
            .and(LootContext.EntityTarget.CODEC.optionalFieldOf("entity").forGetter(f -> f.resolutionContext))
            .apply(i, FlimFlamLoreFunction::new));

    private static Component generate(
        RandomSource random,
        IGenerator generator,
        Map<String, ComponentContents> params) {
        try {
            return generator.generate(random, params).stream()
                .map(content -> {
                    if (content instanceof TranslatableContents translatableContents) {
                        // we do this to strip out the fallbacks - shorter NBT
                        return MutableComponent.create(
                            new TranslatableContents(
                                translatableContents.getKey(),
                                null,
                                TranslatableContents.NO_ARGS));
                    }
                    return MutableComponent.create(content);
                })
                .reduce(Component.empty(), MutableComponent::append);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw e;
        }
    }

    public static <T> LootItemConditionalFunction.Builder<?> setFlimFlam() {
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
    public MapCodec<FlimFlamLoreFunction> codec() {
        return MAP_CODEC;
    }

    @Override
    public ItemStack run(ItemStack itemStack, LootContext context) {
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
                        Arrays.asList(generate(context.getRandom(), FlimFlamLore.INSTANCE, params)),
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
            List<Component> resolvedLines = lore.stream().map(resolver).toList();
            return resolvedLines;
        }
    }
}
