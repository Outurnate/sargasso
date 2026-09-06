package com.outurnate.sargasso.client.renderer;

import com.outurnate.sargasso.entity.SizeRayBeam;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class SizeRayBeamRenderer extends EntityRenderer<SizeRayBeam, EntityRenderState> {
    public SizeRayBeamRenderer(Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
