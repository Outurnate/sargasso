package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class LocalAdvancements {
    public static final Identifier ENTER = SuperSargassoSea.ID("enter");
    public static final Identifier LEAVE = SuperSargassoSea.ID("leave");
    public static final Identifier LEAVE_OTHER = SuperSargassoSea.ID("leave_other");
    public static final Identifier TOAST = SuperSargassoSea.ID("toast");
    public static final Identifier PYLON = SuperSargassoSea.ID("pylon");

    public static void Award(ServerPlayer serverPlayer, Identifier advancement, String criteria) {
        serverPlayer.getAdvancements().award(
            serverPlayer.level().getServer().getAdvancements()
                .get(advancement),
            criteria);
    }
}
