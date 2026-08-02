package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.entity.RedstoneBug;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedstoneBugRenderer extends EntityRenderer<RedstoneBug, RedstoneBugRenderState> {
    public RedstoneBugRenderer(Context context) {
        super(context);
    }

    @Override
    public RedstoneBugRenderState createRenderState() {
        return new RedstoneBugRenderState();
    }

    @Override
    public void extractRenderState(RedstoneBug entity, RedstoneBugRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.bolt = new ElectricArc(entity.origin.toVector3f(), entity.getPosition(1.0F).toVector3f(), 0); // TODO
                                                                                                            // seed
    }

    @Override
    public void submit(
        RedstoneBugRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        state.bolt.submit(poseStack, submitNodeCollector);
    }
}
