package com.outurnate.sargasso.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(Monster.class)
public abstract class MonsterMixin extends PathfinderMob {
    protected MonsterMixin(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        /*
         * ItemStack heldItem = player.getItemInHand(hand); if
         * (heldItem.is(LocalItems.PYLON) && this.canUseSlot(EquipmentSlot.HEAD) &&
         * this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) { heldItem.consume(1,
         * this); this.setItemSlot(EquipmentSlot.HEAD, new
         * ItemStack(LocalItems.PYLON.get(), 1)); if (player instanceof ServerPlayer
         * serverPlayer) { LocalAdvancements.Award(serverPlayer,
         * LocalAdvancements.PYLON, "impossible"); } return InteractionResult.CONSUME; }
         */
        return super.mobInteract(player, hand);
    }
}
