package com.outurnate.sargasso;

import net.minecraft.client.resources.model.EquipmentClientInfo.LayerType;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class EnumExtensions {
    public static final EnumProxy<LayerType> SARGASSO_EARS = new EnumProxy<>(
        LayerType.class,
        SuperSargassoSea.MODID + ":ears");
}
