/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.loot.LostItemsSavedData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    // The injection site for this is strange for a reason
    // ItemEntity doesn't override onBelowWord, so we
    // must inject at the parent
    @Inject(method = "onBelowWorld", at = @At("HEAD"))
    private void sargasso$onBelowWorld(CallbackInfo callbackInfo) {
        if ((Object) this instanceof ItemEntity self) {
            LostItemsSavedData.AddLostItem(self.getItem());
        }
    }
}
