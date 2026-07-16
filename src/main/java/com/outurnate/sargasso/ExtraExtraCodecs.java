package com.outurnate.sargasso;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenCustomHashMap;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class ExtraExtraCodecs {
    private record Entry(ItemStack item, long count) {
    }

    public static final Hash.Strategy<ItemStack> ITEMSTACK_STRATEGY = new Hash.Strategy<ItemStack>() {
        @Override
        public boolean equals(ItemStack a, ItemStack b) {
            if (a == null && b == null)
                return true;
            else if (a == null || b == null)
                return false;
            else
                return ItemStack.isSameItemSameComponents(a, b);
        }

        @Override
        public int hashCode(ItemStack o) {
            return ItemStack.hashItemAndComponents(o);
        }
    };

    @SuppressWarnings("null")
    private static final Codec<Entry> ENTRY_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            ItemStack.CODEC.fieldOf("item").forGetter(Entry::item),
            Codec.LONG.fieldOf("count").forGetter(Entry::count)).apply(instance, Entry::new));

    public static final Codec<Object2LongOpenCustomHashMap<ItemStack>> ITEMSTACK_LONG_MAP_CODEC = Codec
        .list(ENTRY_CODEC).xmap(
            entries -> {
                Object2LongOpenCustomHashMap<ItemStack> map = new Object2LongOpenCustomHashMap<>(
                    ITEMSTACK_STRATEGY);

                for (Entry entry : entries) {
                    map.put(entry.item(), entry.count());
                }

                return map;
            },
            map -> {
                List<Entry> entries = new ArrayList<>(map.size());

                for (Object2LongMap.Entry<ItemStack> entry : map.object2LongEntrySet()) {
                    entries.add(new Entry(entry.getKey(), entry.getLongValue()));
                }

                return entries;
            });
}
