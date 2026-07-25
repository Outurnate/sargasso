package com.outurnate.sargasso.client;

import com.outurnate.sargasso.entity.ElectricMine;
import java.util.List;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class ShockTherapistRenderState extends BlockEntityRenderState {
    public List<ElectricMine> mines;
    public float partialTicks;
}
