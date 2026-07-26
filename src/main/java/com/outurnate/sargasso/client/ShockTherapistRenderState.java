package com.outurnate.sargasso.client;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.phys.Vec3;

public class ShockTherapistRenderState extends BlockEntityRenderState {
    public List<Pair<Vec3, Vec3>> bolts;
}
