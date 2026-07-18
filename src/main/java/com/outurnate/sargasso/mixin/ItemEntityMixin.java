/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.loot.LostItemsSavedData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onBelowWorld() {
        if ((Object) this instanceof ItemEntity self) {
            LostItemsSavedData.AddLostItem(self.getItem());
        }
        this.discard();
    }
}
