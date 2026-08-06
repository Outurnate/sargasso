package com.outurnate.sargasso.item;

import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SnowBootsItem extends Item {
    public SnowBootsItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return stack.is(LocalItems.STUDDED_LEATHER_BOOTS);
    }
}
