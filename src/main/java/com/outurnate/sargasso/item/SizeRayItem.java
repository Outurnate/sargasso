package com.outurnate.sargasso.item;

import com.outurnate.sargasso.entity.SizeRayBeam;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandlerUtil;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class SizeRayItem extends Item {
    private static boolean drawPower(Player player, int amount) {
        SimpleEnergyHandler ray = new SimpleEnergyHandler(amount, amount, 0);
        Inventory inventory = player.getInventory();
        try (Transaction tx = Transaction.openRoot()) {
            for (ItemStack itemStack : inventory) {
                if (!itemStack.isEmpty()) {
                    ItemAccess slot = ItemAccess.forStack(itemStack);
                    EnergyHandler dischargableItem = slot.getCapability(Capabilities.Energy.ITEM);
                    EnergyHandlerUtil.move(dischargableItem, ray, amount, tx);
                    if (ray.getAmountAsInt() == amount) {
                        tx.commit();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private final Holder<MobEffect> effect;

    public SizeRayItem(Properties properties, Holder<MobEffect> effect) {
        super(properties);
        this.effect = effect;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (drawPower(player, 110)) {
            level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.SPLASH_POTION_THROW, // TODO
                SoundSource.PLAYERS,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (level instanceof ServerLevel serverLevel) {
                Projectile.spawnProjectileFromRotation(
                    (l, e, i) -> new SizeRayBeam(l, e, this.effect),
                    serverLevel,
                    null,
                    player,
                    0.0F,
                    0.5F,
                    1.0F);
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }
}
