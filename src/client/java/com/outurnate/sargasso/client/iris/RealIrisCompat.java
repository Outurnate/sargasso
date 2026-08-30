package com.outurnate.sargasso.client.iris;

import net.irisshaders.iris.api.v0.IrisApi;

public class RealIrisCompat extends IrisCompat {
    @Override
    protected boolean isShaderPackInUse() {
        return IrisApi.getInstance().isShaderPackInUse();
    }
}
