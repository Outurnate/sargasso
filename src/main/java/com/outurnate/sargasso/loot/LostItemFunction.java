/* (C)2026 */
package com.outurnate.sargasso.loot;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LostItemFunction extends LootItemConditionalFunction {
    public static final MapCodec<LostItemFunction> MAP_CODEC = RecordCodecBuilder
        .mapCodec(i -> commonFields(i).apply(i, LostItemFunction::new));

    public static LootItemConditionalFunction.Builder<?> createBuilder() {
        return simpleBuilder(conditions -> new LostItemFunction(conditions));
    }

    public LostItemFunction(List<LootItemCondition> predicates) {
        super(predicates);
    }

    @Override
    public MapCodec<LostItemFunction> codec() {
        return MAP_CODEC;
    }

    @Override
    public ItemStack run(ItemStack itemStack, LootContext context) {
        return MoreObjects.firstNonNull(LostItemsSavedData.GetLostItem(context.getRandom()), itemStack);
    }
}
