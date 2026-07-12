package com.outurnate.sargasso.client;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

public class HatClientExtensions implements IClientItemExtensions {
    @SuppressWarnings("rawtypes")
    @Override
    public Model getHumanoidArmorModel(
        ItemStack itemStack,
        EquipmentClientInfo.LayerType layerType,
        Model original) {
        PlayerModel playerModel = (PlayerModel) original;
        playerModel.getHead().skipDraw = true;

        Identifier foxEars = SuperSargassoSea.ID("hats/fox_ears");

        Model model = Minecraft.getInstance()
            .getModelManager()
            .getStandaloneModel(new StandaloneModelKey<>(foxEars::toString));
        return model;
    }
}
