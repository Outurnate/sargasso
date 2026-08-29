package com.outurnate.sargasso.item;

import com.outurnate.sargasso.SuperSargassoSea;

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
            SuperSargassoSea.LOGGER.error("use " + livingEntity.useItemRemaining);
            // n.b. bastardized version of shouldEmitParticlesAndSounds
            // does NOT take into account any effects that slow consumption
            // food item bobbing has an 8 tick period
            // this SHOULD loop it, but also wait until the food item is
            // in front of the mouth before looping
            // by default the full animation takes 32 ticks
            int waitTicksBeforeUseEffects = (int) (self.consumeTicks() * (14.0F / 32.0F));
            if ((self.consumeTicks() - ticksRemaining) > waitTicksBeforeUseEffects
                && ticksRemaining % 8 == 0) {
                SuperSargassoSea.LOGGER.error("use incr");
                livingEntity.useItemRemaining += 8;
            }
        }
    }
}
