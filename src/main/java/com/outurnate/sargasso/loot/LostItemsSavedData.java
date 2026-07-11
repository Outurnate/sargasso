/* (C)2026 */
package com.outurnate.sargasso.loot;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.ExtraExtraCodecs;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.Utils;
import com.outurnate.sargasso.registry.LocalTags;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenCustomHashMap;

import java.util.EnumMap;
import java.util.Locale;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
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
    public static enum LostPool implements StringRepresentable {
        EQUIPMENT, BLOCKS, ITEMS;

        public static final Codec<LostPool> CODEC = StringRepresentable.fromEnum(LostPool::values);

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private static Codec<EnumMap<LostPool, Object2LongOpenCustomHashMap<ItemStack>>> INNER_CODEC = Codec
        .simpleMap(
            LostPool.CODEC,
            ExtraExtraCodecs.ITEMSTACK_LONG_MAP_CODEC,
            StringRepresentable.keys(LostPool.values()))
        .codec().xmap(
            map -> new EnumMap<>(map),
            map -> map);

    public static final SavedDataType<LostItemsSavedData> ID = new SavedDataType<>(
        Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "lostitems"),
        LostItemsSavedData::new,
        RecordCodecBuilder.create(
            instance -> instance
                .group(
                    INNER_CODEC.fieldOf("items")
                        .forGetter(sd -> sd.lostStackPools))
                .apply(instance, LostItemsSavedData::new)));

    public static void AddLostItem(ItemStack lostStack) {
        LostItemsSavedData self = instance();

        if (lostStack == null || lostStack.count() <= 0 || lostStack.is(Items.AIR)
            || lostStack.is(LocalTags.ALWAYS_LOST)) {
            return;
        }

        LOGGER.debug("adding " + lostStack.toString() + " " + lostStack.getCount());

        int originalCount = lostStack.getCount();
        lostStack.setCount(1);
        self.lostStackPools.get(classifyItemStack(lostStack)).addTo(lostStack, originalCount);
        self.setDirty();
    }

    private static LostPool classifyItemStack(ItemStack items) {
        if (items.is(LocalTags.EQUIPMENT)) {
            return LostPool.EQUIPMENT;
        } else if (items.getItem() instanceof BlockItem) {
            return LostPool.BLOCKS;
        }
        return LostPool.ITEMS;
    }

    public static ItemStack GetLostItem(LostPool preferredPool, RandomSource random) {
        LostItemsSavedData self = instance();
        ItemStack result = self.getLostItem(self.lostStackPools.get(preferredPool), random);
        if (result != null) {
            return result;
        }
        for (LostPool pool : LostPool.values()) {
            if (!pool.equals(preferredPool)) {
                result = self.getLostItem(self.lostStackPools.get(pool), random);
                if (result != null) {
                    return result;
                }
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

    private final EnumMap<LostPool, Object2LongOpenCustomHashMap<ItemStack>> lostStackPools;

    public LostItemsSavedData() {
        this.lostStackPools = new EnumMap<>(LostPool.class);
        for (LostPool pool : LostPool.values()) {
            this.lostStackPools
                .put(pool, new Object2LongOpenCustomHashMap<>(ExtraExtraCodecs.ITEMSTACK_STRATEGY));
        }
    }

    public LostItemsSavedData(EnumMap<LostPool, Object2LongOpenCustomHashMap<ItemStack>> lostStacks) {
        this.lostStackPools = lostStacks;
    }

    private ItemStack getLostItem(Object2LongOpenCustomHashMap<ItemStack> lostStacks, RandomSource random) {
        long totalWeight = 0;
        for (long weight : lostStacks.values()) {
            if (weight > 0) {
                totalWeight += weight;
            }
        }

        if (totalWeight <= 0) {
            return null;
        }

        long target = Utils.nextLong(random, totalWeight);

        for (Object2LongMap.Entry<ItemStack> entry : lostStacks.object2LongEntrySet()) {
            long weight = entry.getLongValue();
            if (weight <= 0) {
                continue;
            }

            target -= weight;
            if (target < 0) {
                ItemStack result = entry.getKey();

                if (weight == 1) {
                    lostStacks.removeLong(result);
                } else {
                    entry.setValue(weight - 1);
                }

                this.setDirty();
                return result;
            }
        }

        return null;
    }
}
