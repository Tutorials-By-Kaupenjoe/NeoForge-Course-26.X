package net.kaupenjoe.mccourse.block.entity;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MCCourse.MOD_ID);

    public static final Supplier<BlockEntityType<PedestalBlockEntity>> MAIN_PEDESTAL_BE =
            BLOCK_ENTITIES.register("main_pedestal_be", () -> new BlockEntityType<>(
                    PedestalBlockEntity::new, ModBlocks.MAIN_PEDESTAL.get()));



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
