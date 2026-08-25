/* (C)2026 */
package com.outurnate.sargasso.loot;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.loot.LostItemsSavedData.LostPool;
import com.outurnate.sargasso.repository.LocalTags;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;

public class LostItemFunction extends LootItemConditionalFunction {
    public static final MapCodec<LostItemFunction> MAP_CODEC = RecordCodecBuilder
        .mapCodec(i -> commonFields(i).apply(i, LostItemFunction::new));

    public static LootItemConditionalFunction.Builder<?> createBuilder() {
        return simpleBuilder(conditions -> new LostItemFunction(conditions));
    }

    private static Holder<Biome> getBiome(LootContext context) {
        Vec3 origin = context.getOptionalParameter(LootContextParams.ORIGIN);
        if (origin == null) {
            return null;
        }

        return context.getLevel().getBiome(BlockPos.containing(origin));
    }

    private static LostPool getPreferredPool(LootContext context) {
        Holder<Biome> biome = getBiome(context);
        if (biome != null) {
            if (biome.is(LocalTags.LOST_EQUIPMENT)) {
                return LostPool.EQUIPMENT;
            }
            if (biome.is(LocalTags.LOST_BLOCKS)) {
                return LostPool.BLOCKS;
            }
            if (biome.is(LocalTags.LOST_ITEMS)) {
                return LostPool.ITEMS;
            }
        }
        return LostPool.ITEMS;
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
        return MoreObjects.firstNonNull(
            LostItemsSavedData.GetLostItem(getPreferredPool(context), context.getRandom()),
            itemStack);
    }
}
