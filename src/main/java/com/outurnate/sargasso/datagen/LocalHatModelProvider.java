package com.outurnate.sargasso.datagen;

import com.mojang.serialization.Codec;
import com.outurnate.sargasso.SuperSargassoSea;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;
import net.neoforged.neoforge.common.data.JsonCodecProvider;

public class LocalHatModelProvider extends JsonCodecProvider<String> {
    public LocalHatModelProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(
            output, Target.RESOURCE_PACK, SuperSargassoSea.MODID + "/models/hats", Codec.STRING,
            lookupProvider, SuperSargassoSea.MODID);
        // ObjModelBuilder
    }

    @Override
    protected void gather() {
        unconditional(SuperSargassoSea.ID("fox_ears"), "test");
    }

}
