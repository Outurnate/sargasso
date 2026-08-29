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
            if (self.shouldEmitParticlesAndSounds(ticksRemaining)) {
                SuperSargassoSea.LOGGER.error("use incr");
                if ((ticksRemaining % 8) == 0) {
                    livingEntity.useItemRemaining += 8;
                }
            }
        }
    }
}
