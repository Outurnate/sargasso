package com.outurnate.sargasso;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenCustomHashMap;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStackTemplate;

public class ExtraExtraCodecs {
    private record Entry(ItemStackTemplate item, long count) {
    }

    // TODO might not be needed
    public static final Hash.Strategy<ItemStackTemplate> ITEMSTACK_STRATEGY = new Hash.Strategy<ItemStackTemplate>() {
        @Override
        public boolean equals(ItemStackTemplate a, ItemStackTemplate b) {
            return a.equals(b);
        }

        @Override
        public int hashCode(ItemStackTemplate o) {
            return o.hashCode();
        }
    };

    @SuppressWarnings("null")
    private static final Codec<Entry> ENTRY_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("item").forGetter(Entry::item),
            Codec.LONG.fieldOf("count").forGetter(Entry::count)).apply(instance, Entry::new));

    public static final Codec<Object2LongOpenCustomHashMap<ItemStackTemplate>> ITEMSTACKTEMPLATE_LONG_MAP_CODEC = Codec
        .list(ENTRY_CODEC).xmap(
            entries -> {
                Object2LongOpenCustomHashMap<ItemStackTemplate> map = new Object2LongOpenCustomHashMap<>(
                    ITEMSTACK_STRATEGY);

                for (Entry entry : entries) {
                    map.put(entry.item(), entry.count());
                }

                return map;
            },
            map -> {
                List<Entry> entries = new ArrayList<>(map.size());

                for (Object2LongMap.Entry<ItemStackTemplate> entry : map.object2LongEntrySet()) {
                    entries.add(new Entry(entry.getKey(), entry.getLongValue()));
                }

                return entries;
            });
}
