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

                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
