package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.standalone.StandaloneModelKey;

public class LocalStandaloneModels {
    public static final StandaloneModelKey<BlockStateModelPart> FOX_EARS;
    static {
        Identifier foxEars = SuperSargassoSea.ID("hat/fox_ears");
        FOX_EARS = new StandaloneModelKey<>(foxEars::toString);
    }
}
