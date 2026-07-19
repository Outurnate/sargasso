package com.outurnate.sargasso.item;

import com.outurnate.sargasso.Config;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnergyItem extends Item {
    public EnergyItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int energy = stack.getOrDefault(LocalDataComponentTypes.ENERGY, 0);
        int capacity = Config.BATTERY_CAPACITY.getAsInt();

        float pct = energy / (float) capacity;
        return Mth.hsvToRgb(pct / 3.0f, 1.0f, 1.0f);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int energy = stack.getOrDefault(LocalDataComponentTypes.ENERGY, 0);
        int capacity = Config.BATTERY_CAPACITY.getAsInt();

        return Math.round(13.0f * energy / capacity);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }
}
