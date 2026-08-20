package com.outurnate.sargasso.client.iris;

import net.irisshaders.iris.api.v0.IrisApi;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealIrisCompat extends IrisCompat {
    @Override
    protected boolean isShaderPackInUse() {
        return IrisApi.getInstance().isShaderPackInUse();
    }
}
