package net.kaupenjoe.mccourse.fluid;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector4f;

import java.util.function.Supplier;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, MCCourse.MOD_ID);

    public static final Supplier<FluidType> ZIRCON_WATER_FLUID_TYPE = FLUID_TYPES.register("zircon_water_fluid_type",
            () -> new FluidType(FluidType.Properties.create().isWaterLike(false)));

    public static IClientFluidTypeExtensions ZIRCON_WATER_EXTENSION = new IClientFluidTypeExtensions() {
        @Override
        public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
            fluidFogColor.set(0.83f, 0.16f, 0.16f);
            IClientFluidTypeExtensions.super.modifyFogColor(camera, partialTick, level, renderDistance, darkenWorldAmount, fluidFogColor);
        }
    };


    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
