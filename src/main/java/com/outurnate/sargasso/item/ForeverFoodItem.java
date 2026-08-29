package com.outurnate.sargasso.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
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
            // n.b. bastardized version of shouldEmitParticlesAndSounds
            // does NOT take into account any effects that slow consumption
            // food item bobbing has an 8 tick period
            // this SHOULD loop it, but also wait until the food item is
            // in front of the mouth before looping
            // by default the full animation takes 32 ticks
            int waitTicksBeforeUseEffects = (int) (self.consumeTicks() * (14.0F / 32.0F));
            if ((self.consumeTicks() - ticksRemaining) > waitTicksBeforeUseEffects
                && ticksRemaining % 8 == 0) {
                if (livingEntity instanceof Player player
                    && itemStack.get(DataComponents.FOOD) instanceof FoodProperties food) {
                    FoodData foodData = player.getFoodData();
                    float originalSaturation = foodData.getSaturationLevel();
                    foodData.eat(food);
                    foodData.setSaturation(originalSaturation / 2.0F);
                    foodData.addExhaustion(0.1F);
                }
                livingEntity.useItemRemaining += 8;
            }
        }
    }
}
