package com.outurnate.sargasso.item;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class PersonalVoltmeterItem extends Item {
    public PersonalVoltmeterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(
                Component.translatable(
                    "chat." + SuperSargassoSea.MODID + ".voltmeter",
                    serverPlayer
                        .getData(LocalAttachmentTypes.GENERATOR_COUNT).toComponent("0.##")));
        }
        return null;
    }
}
