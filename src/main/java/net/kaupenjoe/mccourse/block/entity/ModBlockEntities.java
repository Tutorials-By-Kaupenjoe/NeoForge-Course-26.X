package net.kaupenjoe.mccourse.block.entity;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.custom.CoalGeneratorBlock;
import net.kaupenjoe.mccourse.block.entity.custom.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MCCourse.MOD_ID);

    public static final Supplier<BlockEntityType<MainPedestalBlockEntity>> MAIN_PEDESTAL_BE =
            BLOCK_ENTITIES.register("main_pedestal_be", () -> new BlockEntityType<>(
                    MainPedestalBlockEntity::new, ModBlocks.MAIN_PEDESTAL.get()));
    public static final Supplier<BlockEntityType<SidePedestalBlockEntity>> SIDE_PEDESTAL_BE =
            BLOCK_ENTITIES.register("side_pedestal_be", () -> new BlockEntityType<>(
                    SidePedestalBlockEntity::new, ModBlocks.SIDE_PEDESTAL.get()));

    public static final Supplier<BlockEntityType<CrystallizerBlockEntity>> CRYSTALLIZER_BE =
            BLOCK_ENTITIES.register("crystallizer_be", () -> new BlockEntityType<>(
                    CrystallizerBlockEntity::new, ModBlocks.CRYSTALLIZER.get()));
    public static final Supplier<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR_BE =
            BLOCK_ENTITIES.register("coal_generator_be", () -> new BlockEntityType<>(
                    CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR.get()));
    public static final Supplier<BlockEntityType<BatteryBlockEntity>> BATTERY_BE =
            BLOCK_ENTITIES.register("battery_be", () -> new BlockEntityType<>(
                    BatteryBlockEntity::new, ModBlocks.BATTERY.get()));

    public static final Supplier<BlockEntityType<GrowthChamberBlockEntity>> GROWTH_CHAMBER_BE =
            BLOCK_ENTITIES.register("growth_chamber_be", () -> new BlockEntityType<>(
                    GrowthChamberBlockEntity::new, ModBlocks.GROWTH_CHAMBER.get()));

    public static final Supplier<BlockEntityType<AtomicSeparatorBlockEntity>> ATOMIC_SEPARATOR_BE =
            BLOCK_ENTITIES.register("atomic_separator_be", () -> new BlockEntityType<>(
                    AtomicSeparatorBlockEntity::new, ModBlocks.ATOMIC_SEPARATOR.get()));



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
