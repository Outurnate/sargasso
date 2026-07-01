/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.registry.LocalAttachmentTypes;
import com.outurnate.sargasso.registry.LocalTags;
import java.time.Instant;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public class TimeTravel {
    @SubscribeEvent
    public static void onEntityFinishUsing(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().is(LocalTags.BREAD) && event.getEntity() instanceof ServerPlayer player) {
            player.setData(LocalAttachmentTypes.BREAD_EATEN, Instant.now());
        }
    }
}
