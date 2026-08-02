package com.outurnate.sargasso.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class ShockTherapistEntityRenderer
    implements BlockEntityRenderer<ShockTherapistBlockEntity, ShockTherapistRenderState> {
    public ShockTherapistEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public ShockTherapistRenderState createRenderState() {
        return new ShockTherapistRenderState();
    }

    @Override
    public void extractRenderState(
        ShockTherapistBlockEntity blockEntity,
        ShockTherapistRenderState state,
        float partialTicks,
        Vec3 cameraPosition,
        ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(
            blockEntity,
            state,
            partialTicks,
            cameraPosition,
            breakProgress);
        Vec3 blockPos = new Vec3(state.blockPos);
        RandomSource random = RandomSource.createThreadLocalInstance(blockEntity.seed);
        state.bolts = blockEntity.bolts.stream()
            .map(
                b -> new ElectricArc(
                    b.getFirst().pos(partialTicks).subtract(blockPos).toVector3f(),
                    b.getSecond().pos(partialTicks).subtract(blockPos).toVector3f(),
                    random.nextLong()))
            .toList();

        Minecraft mc = Minecraft.getInstance();
        SoundManager sm = mc.getSoundManager();

        if (state.bolts.size() > 0) {
            if (blockEntity.clientObj == null
                || (blockEntity.clientObj instanceof ElectricArcSoundInstance instance
                    && instance.isStopped())) {
                blockEntity.clientObj = new ElectricArcSoundInstance(mc.level, state.blockPos);
                sm.play((SoundInstance) blockEntity.clientObj);
            }
        } else if (blockEntity.clientObj != null) {
            sm.stop((SoundInstance) blockEntity.clientObj);
            blockEntity.clientObj = null;
        }
    }

    @Override
    public void submit(
        ShockTherapistRenderState state,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState camera) {
        for (ElectricArc arc : state.bolts) {
            arc.submit(poseStack, submitNodeCollector);
        }
    }
}
