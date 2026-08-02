package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.entity.RedstoneBug;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

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
        // TODO seed
        state.bolt = new ElectricArc(
            entity.getPosition(partialTicks).toVector3f().mul(-1.0F).add(entity.origin),
            new Vector3f(),
            0,
            4,
            0.5F,
            2.0F);
    }

    protected AABB getBoundingBoxForCulling(RedstoneBug entity) {
        return new AABB(new Vec3(entity.origin), entity.getPosition(1.0F));
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
