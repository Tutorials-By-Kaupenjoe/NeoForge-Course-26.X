package net.kaupenjoe.mccourse.worldgen.tree;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFoliagePlacers {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, MCCourse.MOD_ID);

    public static final Supplier<FoliagePlacerType<InvertedPyramidFoliagePlacer>> INVERTED_PYRAMID_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("inverted_pyramid_foliage_placer", () -> new FoliagePlacerType<>(InvertedPyramidFoliagePlacer.CODEC));


    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
    }
}
