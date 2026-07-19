package com.outurnate.sargasso.client;

import com.outurnate.sargasso.entity.ElectricMine;
import com.outurnate.sargasso.entity.ElectricMineRenderState;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class ElectricMineRenderer extends EntityRenderer<ElectricMine, ElectricMineRenderState> {
    public ElectricMineRenderer(Context context) {
        super(context);
    }

    @Override
    public ElectricMineRenderState createRenderState() {
        return new ElectricMineRenderState();
    }
}
