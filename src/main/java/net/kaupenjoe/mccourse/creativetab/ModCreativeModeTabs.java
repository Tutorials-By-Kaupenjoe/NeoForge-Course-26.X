package net.kaupenjoe.mccourse.creativetab;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MCCourse.MOD_ID);

    public static final Supplier<CreativeModeTab> ZIRCON_ITEMS_TAB = CREATIVE_MODE_TABS.register("zircon_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ZIRCON.get()))
                    .title(Component.translatable("creativetab.mccourse.zircon_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ZIRCON);
                        output.accept(ModItems.RAW_ZIRCON);
                        output.accept(ModItems.CHISEL);
                        output.accept(ModItems.RADISH);
                        output.accept(ModItems.FROSTFIRE_ICE);

                        output.accept(ModItems.ZIRCON_SWORD);
                        output.accept(ModItems.ZIRCON_PICKAXE);
                        output.accept(ModItems.ZIRCON_SHOVEL);
                        output.accept(ModItems.ZIRCON_AXE);
                        output.accept(ModItems.ZIRCON_HOE);
                        output.accept(ModItems.ZIRCON_PAXEL);
                        output.accept(ModItems.ZIRCON_HAMMER);

                        output.accept(ModItems.ZIRCON_HELMET);
                        output.accept(ModItems.ZIRCON_CHESTPLATE);
                        output.accept(ModItems.ZIRCON_LEGGINGS);
                        output.accept(ModItems.ZIRCON_BOOTS);

                        output.accept(ModItems.ZIRCON_HORSE_ARMOR);
                        output.accept(ModItems.KAUPEN_BOW);

                        output.accept(ModItems.RADISH_SEEDS);
                        output.accept(ModItems.GOJI_BERRIES);
                        output.accept(ModItems.RICE_SHOOT);

                        output.accept(ModItems.BAR_BRAWL_MUSIC_DISC);
                        output.accept(ModItems.RADIATION_STAFF);

                        output.accept(ModItems.ZIRCON_WATER_BUCKET);

                        output.accept(ModItems.PENGUIN_SPAWN_EGG);
                        output.accept(ModItems.WARTURTLE_SPAWN_EGG);
                        output.accept(ModItems.DODO_SPAWN_EGG);

                        output.accept(ModItems.IRON_WARTURTLE_ARMOR);
                        output.accept(ModItems.GOLD_WARTURTLE_ARMOR);
                        output.accept(ModItems.DIAMOND_WARTURTLE_ARMOR);
                        output.accept(ModItems.NETHERITE_WARTURTLE_ARMOR);
                        output.accept(ModItems.ZIRCON_WARTURTLE_ARMOR);

                        output.accept(ModItems.EBONY_BOAT);
                        output.accept(ModItems.EBONY_CHEST_BOAT);

                        output.accept(ModItems.TOMAHAWK);

                    }).build());

    public static final Supplier<CreativeModeTab> ZIRCON_BLOCKS_TAB = CREATIVE_MODE_TABS.register("zircon_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ZIRCON_BLOCK))
                    .title(Component.translatable("creativetab.mccourse.zircon_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.ZIRCON_BLOCK);
                        output.accept(ModBlocks.RAW_ZIRCON_BLOCK);

                        output.accept(ModBlocks.ZIRCON_ORE);
                        output.accept(ModBlocks.ZIRCON_DEEPSLATE_ORE);
                        output.accept(ModBlocks.ZIRCON_NETHER_ORE);
                        output.accept(ModBlocks.ZIRCON_END_ORE);

                        output.accept(ModBlocks.MAGIC_BLOCK);

                        output.accept(ModBlocks.ZIRCON_STAIRS);
                        output.accept(ModBlocks.ZIRCON_SLAB);

                        output.accept(ModBlocks.ZIRCON_PRESSURE_PLATE);
                        output.accept(ModBlocks.ZIRCON_BUTTON);

                        output.accept(ModBlocks.ZIRCON_FENCE);
                        output.accept(ModBlocks.ZIRCON_FENCE_GATE);
                        output.accept(ModBlocks.ZIRCON_WALL);

                        output.accept(ModBlocks.ZIRCON_DOOR);
                        output.accept(ModBlocks.ZIRCON_TRAPDOOR);

                        output.accept(ModBlocks.ZIRCON_LAMP);
                        output.accept(ModBlocks.CATMINT);
                        output.accept(ModBlocks.COLORED_LEAVES);

                        output.accept(ModBlocks.CHAIR);

                        output.accept(ModBlocks.EBONY_LOG);
                        output.accept(ModBlocks.EBONY_WOOD);
                        output.accept(ModBlocks.STRIPPED_EBONY_LOG);
                        output.accept(ModBlocks.STRIPPED_EBONY_WOOD);

                        output.accept(ModBlocks.EBONY_PLANKS);
                        output.accept(ModBlocks.EBONY_LEAVES);

                        output.accept(ModBlocks.EBONY_SAPLING);

                        output.accept(ModBlocks.KAUPEN_PORTAL);
                        output.accept(ModBlocks.MAIN_PEDESTAL);
                        output.accept(ModBlocks.SIDE_PEDESTAL);
                        output.accept(ModBlocks.CRYSTALLIZER);
                        output.accept(ModBlocks.COAL_GENERATOR);

                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
