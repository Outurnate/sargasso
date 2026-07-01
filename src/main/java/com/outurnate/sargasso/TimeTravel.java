/* (C)2026 */
package com.outurnate.sargasso;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;
import com.outurnate.sargasso.registry.LocalTags;
import java.time.Instant;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.slf4j.Logger;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public class TimeTravel {
    public static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onEntityFinishUsing(LivingEntityUseItemEvent.Finish event) {
        LOGGER.debug("entity finish and " + event.getItem() + " is " + event.getItem().is(LocalTags.BREAD));
        if (event.getItem().is(LocalTags.BREAD) && event.getEntity() instanceof ServerPlayer player) {
            player.setData(LocalAttachmentTypes.BREAD_EATEN, Instant.now());
        }
    }
}
