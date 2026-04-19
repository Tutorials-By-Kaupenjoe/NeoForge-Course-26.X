package net.kaupenjoe.mccourse.worldgen.tree;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, MCCourse.MOD_ID);

    public static final Supplier<TrunkPlacerType<SpiralTrunkPlacer>> SPIRAL_TRUNK_PLACER =
            TRUNK_PLACERS.register("spiral_trunk_placer", () -> new TrunkPlacerType<>(SpiralTrunkPlacer.CODEC));


    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
    }
}
