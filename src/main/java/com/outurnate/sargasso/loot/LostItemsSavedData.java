/* (C)2026 */
package com.outurnate.sargasso.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.ExtraExtraCodecs;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.Utils;
import com.outurnate.sargasso.repository.LocalTags;

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
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

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

    private static Codec<EnumMap<LostPool, Object2LongOpenCustomHashMap<ItemStackTemplate>>> INNER_CODEC = Codec
        .simpleMap(
            LostPool.CODEC,
            ExtraExtraCodecs.ITEMSTACKTEMPLATE_LONG_MAP_CODEC,
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

        ItemStackTemplate lostStackTemplate = new ItemStackTemplate(
            lostStack.getItem(),
            1,
            lostStack.getComponentsPatch());

        if (lostStackTemplate == null || lostStackTemplate.count() <= 0 || lostStackTemplate.is(Items.AIR)
            || lostStackTemplate.is(LocalTags.ALWAYS_LOST)) {
            return;
        }

        int originalCount = lostStack.getCount();
        self.lostStackPools.get(classifyItemStack(lostStackTemplate)).addTo(lostStackTemplate, originalCount);
        self.setDirty();
    }

    private static LostPool classifyItemStack(ItemStackTemplate items) {
        if (items.is(LocalTags.EQUIPMENT)) {
            return LostPool.EQUIPMENT;
        } else if (items.item() instanceof BlockItem) {
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

    private final EnumMap<LostPool, Object2LongOpenCustomHashMap<ItemStackTemplate>> lostStackPools;

    public LostItemsSavedData() {
        this.lostStackPools = new EnumMap<>(LostPool.class);
        for (LostPool pool : LostPool.values()) {
            this.lostStackPools
                .put(pool, new Object2LongOpenCustomHashMap<>(ExtraExtraCodecs.ITEMSTACK_STRATEGY));
        }
    }

    public LostItemsSavedData(EnumMap<LostPool, Object2LongOpenCustomHashMap<ItemStackTemplate>> lostStacks) {
        this.lostStackPools = lostStacks;
    }

    private ItemStack getLostItem(
        Object2LongOpenCustomHashMap<ItemStackTemplate> lostStacks,
        RandomSource random) {
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

        for (Object2LongMap.Entry<ItemStackTemplate> entry : lostStacks.object2LongEntrySet()) {
            long weight = entry.getLongValue();
            if (weight <= 0) {
                continue;
            }

            target -= weight;
            if (target < 0) {
                ItemStackTemplate result = entry.getKey();

                if (weight == 1) {
                    lostStacks.removeLong(result);
                } else {
                    entry.setValue(weight - 1);
                }

                this.setDirty();
                return result.withCount(1).create();
            }
        }

        return null;
    }
}
