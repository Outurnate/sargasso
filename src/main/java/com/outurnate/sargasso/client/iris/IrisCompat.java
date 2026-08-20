package com.outurnate.sargasso.client.iris;

import com.outurnate.sargasso.ClientConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;

@OnlyIn(Dist.CLIENT)
public abstract class IrisCompat {
    public static final IrisCompat INSTANCE;

    static {
        if (ModList.get().isLoaded("iris")) {
            INSTANCE = new RealIrisCompat();
        } else {
            INSTANCE = new IrisCompat() {
                @Override
                protected boolean isShaderPackInUse() {
                    return false;
                }
            };
        }
    }

    protected abstract boolean isShaderPackInUse();

    public final boolean shouldUseFallbackRendering() {
        return !ClientConfig.ADVANCED_RENDERING.getAsBoolean() || isShaderPackInUse();
    }
}
