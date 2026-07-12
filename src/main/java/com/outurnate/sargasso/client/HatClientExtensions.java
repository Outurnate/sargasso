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
        System.out.println(original.getClass().descriptorString());
        original.resetPose();
        original.root().setRotation(90.0F, 3.15F, 1.0F);
        return original;
    }
}
