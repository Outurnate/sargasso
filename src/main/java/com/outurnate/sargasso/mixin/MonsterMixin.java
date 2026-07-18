package com.outurnate.sargasso.mixin;

import com.outurnate.sargasso.registry.LocalItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Monster.class)
public abstract class MonsterMixin extends PathfinderMob {
    protected MonsterMixin(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.is(LocalItems.PYLON)) {
            heldItem.consume(1, this);
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(LocalItems.PYLON.get(), 1));
            return InteractionResult.CONSUME;
        }
        return super.mobInteract(player, hand);
    }
}
