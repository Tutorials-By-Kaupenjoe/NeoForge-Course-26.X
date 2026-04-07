package net.kaupenjoe.mccourse.block;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.custom.MagicBlock;
import net.kaupenjoe.mccourse.block.custom.ZirconLampBlock;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MCCourse.MOD_ID);

    public static final DeferredBlock<Block> ZIRCON_BLOCK = registerBlock("zircon_block",
            properties -> new Block(properties.strength(4f)
                    .requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> RAW_ZIRCON_BLOCK = registerBlock("raw_zircon_block",
            properties -> new Block(properties.strength(3f)
                    .requiresCorrectToolForDrops().sound(SoundType.METAL)));

    public static final DeferredBlock<Block> ZIRCON_ORE = registerBlock("zircon_ore",
            properties -> new DropExperienceBlock(UniformInt.of(2, 4),
                    properties.strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> ZIRCON_DEEPSLATE_ORE = registerBlock("zircon_deepslate_ore",
            properties -> new DropExperienceBlock(UniformInt.of(4, 6),
                    properties.strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> ZIRCON_NETHER_ORE = registerBlock("zircon_nether_ore",
            properties -> new DropExperienceBlock(UniformInt.of(6, 7),
                    properties.strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));
    public static final DeferredBlock<Block> ZIRCON_END_ORE = registerBlock("zircon_end_ore",
            properties -> new DropExperienceBlock(UniformInt.of(7, 9),
                    properties.strength(5f).requiresCorrectToolForDrops().sound(SoundType.STONE)));


    public static final DeferredBlock<Block> MAGIC_BLOCK = registerBlock("magic_block",
            properties -> new MagicBlock(properties.strength(2f)
                    .noLootTable().sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> ZIRCON_STAIRS = registerBlock("zircon_stairs",
            properties -> new StairBlock(ModBlocks.ZIRCON_BLOCK.get().defaultBlockState(),
                    properties.strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ZIRCON_SLAB = registerBlock("zircon_slab",
            properties -> new SlabBlock(properties.strength(2f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> ZIRCON_PRESSURE_PLATE = registerBlock("zircon_pressure_plate",
            properties -> new PressurePlateBlock(BlockSetType.IRON,
                    properties.strength(2f).requiresCorrectToolForDrops().forceSolidOn().noCollision().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> ZIRCON_BUTTON = registerBlock("zircon_button",
            properties -> new ButtonBlock(BlockSetType.IRON, 20,
                    properties.strength(2f).requiresCorrectToolForDrops().noCollision().pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> ZIRCON_FENCE = registerBlock("zircon_fence",
            properties -> new FenceBlock(properties.strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ZIRCON_FENCE_GATE = registerBlock("zircon_fence_gate",
            properties -> new FenceGateBlock(WoodType.ACACIA, properties.strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ZIRCON_WALL = registerBlock("zircon_wall",
            properties -> new WallBlock(properties.strength(2f).requiresCorrectToolForDrops().forceSolidOn()));

    public static final DeferredBlock<Block> ZIRCON_DOOR = registerBlock("zircon_door",
            properties -> new DoorBlock(BlockSetType.IRON, properties.strength(2f)
                    .requiresCorrectToolForDrops().noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> ZIRCON_TRAPDOOR = registerBlock("zircon_trapdoor",
            properties -> new TrapDoorBlock(BlockSetType.IRON, properties.strength(2f)
                    .requiresCorrectToolForDrops().noOcclusion().isValidSpawn(Blocks::never)));

    public static final DeferredBlock<Block> ZIRCON_LAMP = registerBlock("zircon_lamp",
            properties -> new ZirconLampBlock(properties.strength(2f)
                    .requiresCorrectToolForDrops().lightLevel(state -> state.getValue(ZirconLampBlock.CLICKED) ? 15 : 0)));



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
