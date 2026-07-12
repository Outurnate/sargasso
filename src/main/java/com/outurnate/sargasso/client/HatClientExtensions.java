package com.outurnate.sargasso.client;

import com.outurnate.sargasso.registry.LocalStandaloneModels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class HatClientExtensions implements IClientItemExtensions {
    @SuppressWarnings("rawtypes")
    @Override
    public Model getHumanoidArmorModel(
        ItemStack itemStack,
        EquipmentClientInfo.LayerType layerType,
        Model original) {
        PlayerModel playerModel = (PlayerModel) original;
        playerModel.getHead().skipDraw = true;

        BlockStateModel model = Minecraft.getInstance()
            .getModelManager()
            .getStandaloneModel(LocalStandaloneModels.FOX_EARS);
        System.out.println(model.getClass().descriptorString());
        System.out.println(original.getClass().descriptorString());
        return original;
    }
}
