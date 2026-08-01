package com.outurnate.sargasso.client;

import com.outurnate.sargasso.client.ShockTherapistEntityRenderer.ElectricArc;

import java.util.List;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShockTherapistRenderState extends BlockEntityRenderState {
    public List<ElectricArc> bolts;
}
