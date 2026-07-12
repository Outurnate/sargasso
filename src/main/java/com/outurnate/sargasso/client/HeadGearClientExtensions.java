package com.outurnate.sargasso.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

@OnlyIn(Dist.CLIENT)
public class HeadGearClientExtensions implements IClientItemExtensions {
    @Override
    public Model getHumanoidArmorModel(
        ItemStack itemStack,
        EquipmentClientInfo.LayerType layerType,
        Model original) {
        HumanoidModel<?> playerModel = (HumanoidModel<?>) original;
        playerModel.getHead().skipDraw = true;
        return original;
    }
}
