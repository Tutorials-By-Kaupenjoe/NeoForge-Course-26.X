package net.kaupenjoe.mccourse.fluid;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, MCCourse.MOD_ID);

    public static final Supplier<FlowingFluid> ZIRCON_WATER_SOURCE = FLUIDS.register("zircon_water_source",
            () -> new BaseFlowingFluid.Source(ModFluids.ZIRCON_WATER_PROPERTIES));
    public static final Supplier<FlowingFluid> ZIRCON_WATER_FLOWING = FLUIDS.register("zircon_water_flowing",
            () -> new BaseFlowingFluid.Flowing(ModFluids.ZIRCON_WATER_PROPERTIES));


    private static final BaseFlowingFluid.Properties ZIRCON_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.ZIRCON_WATER_FLUID_TYPE, ZIRCON_WATER_SOURCE, ZIRCON_WATER_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(ModBlocks.ZIRCON_WATER_LIQUID_BLOCK).bucket(ModItems.ZIRCON_WATER_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
