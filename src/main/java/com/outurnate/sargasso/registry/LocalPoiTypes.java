package com.outurnate.sargasso.registry;

import com.google.common.collect.ImmutableSet;
import com.outurnate.sargasso.SuperSargassoSea;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalPoiTypes {
    public static final DeferredRegister<PoiType> REGISTRY = DeferredRegister
        .create(Registries.POINT_OF_INTEREST_TYPE, SuperSargassoSea.MODID);

    public static final DeferredHolder<PoiType, PoiType> SCAVENGER = REGISTRY.register(
        "scavenger",
        () -> new PoiType(getBlockStates(LocalBlocks.SORTING_BIN.get()), 1, 1));

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
