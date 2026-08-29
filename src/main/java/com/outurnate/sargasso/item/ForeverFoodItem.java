package com.outurnate.sargasso.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

public class ForeverFoodItem extends Item {
    public ForeverFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining) {
        super.onUseTick(level, livingEntity, itemStack, ticksRemaining);
        if (itemStack.get(DataComponents.CONSUMABLE) instanceof Consumable self) {
            if (self.shouldEmitParticlesAndSounds(ticksRemaining)) {
                livingEntity.useItemRemaining -= 4;
            }
        }
    }
}
