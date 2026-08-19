package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.MultiblockProvider;
import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;

public class NetherPortalMultiblock extends MultiblockProvider {
    public NetherPortalMultiblock(PackOutput packOutput) {
        super(packOutput, SuperSargassoSea.MODID);
    }

    @Override
    public void buildMultiblocks() {
        this.add(
            this.modLoc("nether_portal"),
            new DenseMultiblockBuilder()
                .layer(
                    "OOOOO",
                    "OPPPO",
                    "OP0PO",
                    "OPPPO",
                    "OOOOO")
                .block('O', () -> Blocks.OBSIDIAN)
                .block('0', () -> Blocks.NETHER_PORTAL)
                .block('P', () -> Blocks.NETHER_PORTAL));
    }
}
