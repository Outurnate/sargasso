/* (C)2026 */
package com.outurnate.sargasso.loot;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.Utils;
import com.outurnate.sargasso.registry.LocalTags;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenCustomHashMap;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public class LostItemsSavedData extends SavedData {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final SavedDataType<LostItemsSavedData> ID = new SavedDataType<>(
        Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "lostitems"),
        LostItemsSavedData::new,
        RecordCodecBuilder.create(
            instance -> instance
                .group(
                    Codec.list(ItemStack.CODEC).fieldOf("items")
                        .forGetter(sd -> new ArrayList<>(sd.lostStacks.keySet())),
                    Codec.list(Codec.LONG).fieldOf("counts")
                        .forGetter(sd -> new ArrayList<>(sd.lostStacks.values())))
                .apply(instance, LostItemsSavedData::new)));

    private static final Hash.Strategy<ItemStack> STRATEGY = new Hash.Strategy<ItemStack>() {
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

    public static void AddLostItem(ItemStack lostStack) {
        LostItemsSavedData self = instance();

        if (lostStack == null || lostStack.count() <= 0 || lostStack.is(Items.AIR)
            || lostStack.is(LocalTags.ALWAYS_LOST)) {
            return;
        }

        LOGGER.debug("adding" + lostStack.toString() + " " + lostStack.getCount());

        int originalCount = lostStack.getCount();
        lostStack.setCount(1);
        self.lostStacks.addTo(lostStack, originalCount);
        self.setDirty();
    }

    public static ItemStack GetLostItem(RandomSource random) {
        LostItemsSavedData instance = instance();

        long totalWeight = 0;
        for (long weight : instance.lostStacks.values()) {
            if (weight > 0) {
                totalWeight += weight;
            }
        }

        if (totalWeight <= 0) {
            return null;
        }

        long target = Utils.nextLong(random, totalWeight);

        for (Object2LongMap.Entry<ItemStack> entry : instance.lostStacks.object2LongEntrySet()) {
            long weight = entry.getLongValue();
            if (weight <= 0) {
                continue;
            }

            target -= weight;
            if (target < 0) {
                ItemStack result = entry.getKey();

                if (weight == 1) {
                    instance.lostStacks.removeLong(result);
                } else {
                    entry.setValue(weight - 1);
                }

                instance.setDirty();
                return result;
            }
        }

        return null;
    }

    private static LostItemsSavedData instance() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return server.getDataStorage().computeIfAbsent(ID);
    }

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        AddLostItem(event.getEntity().getItem());
    }

    private final Object2LongOpenCustomHashMap<ItemStack> lostStacks;

    public LostItemsSavedData() {
        this.lostStacks = new Object2LongOpenCustomHashMap<>(STRATEGY);
    }

    public LostItemsSavedData(List<ItemStack> items, List<Long> counts) {
        this.lostStacks = new Object2LongOpenCustomHashMap<ItemStack>(
            items.toArray(new ItemStack[0]),
            counts.stream().mapToLong(Long::longValue).toArray(),
            Hash.DEFAULT_LOAD_FACTOR,
            STRATEGY);
    }
}
