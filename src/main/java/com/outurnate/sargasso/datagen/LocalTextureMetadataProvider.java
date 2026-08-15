package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.supermartijn642.fusion.api.provider.FusionTextureMetadataProvider;
import com.supermartijn642.fusion.api.texture.DefaultTextureTypes;
import com.supermartijn642.fusion.api.texture.types.connecting.ConnectingTextureData;
import com.supermartijn642.fusion.api.texture.types.connecting.ConnectingTextureData.Layout;

import net.minecraft.data.PackOutput;

public class LocalTextureMetadataProvider extends FusionTextureMetadataProvider {
    public LocalTextureMetadataProvider(PackOutput output) {
        super(SuperSargassoSea.MODID, output);
    }

    @Override
    protected void generate() {
        ConnectingTextureData compactLayout = ConnectingTextureData.builder()
            .layout(Layout.COMPACT)
            .build();
        this.addTextureMetadata(
            SuperSargassoSea.ID("block/starmetal_block"),
            DefaultTextureTypes.CONNECTING,
            compactLayout);
        this.addTextureMetadata(
            SuperSargassoSea.ID("block/reinforced_starmetal_block"),
            DefaultTextureTypes.CONNECTING,
            compactLayout);
    }
}
