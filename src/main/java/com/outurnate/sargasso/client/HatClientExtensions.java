package com.outurnate.sargasso.client;

import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class HatClientExtensions implements IClientItemExtensions {
    @Override
    public Model getHumanoidArmorModel(
        ItemStack itemStack,
        EquipmentClientInfo.LayerType layerType,
        Model original) {
        original.resetPose();
        return original;
    }
}
