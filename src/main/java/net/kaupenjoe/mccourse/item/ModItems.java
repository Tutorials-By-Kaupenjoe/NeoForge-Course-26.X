package net.kaupenjoe.mccourse.item;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.food.ModFoodProperties;
import net.kaupenjoe.mccourse.item.custom.ChiselItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MCCourse.MOD_ID);

    public static final DeferredItem<Item> ZIRCON = ITEMS.registerSimpleItem("zircon",
            properties -> properties);
    public static final DeferredItem<Item> RAW_ZIRCON = ITEMS.registerSimpleItem("raw_zircon",
            properties -> properties);

    public static final DeferredItem<Item> CHISEL = ITEMS.registerItem("chisel",
            properties -> new ChiselItem(properties.durability(32)));

    public static final DeferredItem<Item> RADISH = ITEMS.registerItem("radish",
            properties -> new Item(properties.food(ModFoodProperties.RADISH, ModFoodProperties.RADISH_EFFECT)) {
                @Override
                public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                    builder.accept(Component.translatable("tooltip.mccourse.radish"));
                    super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> FROSTFIRE_ICE = ITEMS.registerItem("frostfire_ice", Item::new);


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
