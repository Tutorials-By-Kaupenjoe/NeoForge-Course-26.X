package net.kaupenjoe.mccourse.item;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.food.ModFoodProperties;
import net.kaupenjoe.mccourse.item.custom.ChiselItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
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

    public static final DeferredItem<Item> ZIRCON_SWORD = ITEMS.registerItem("zircon_sword",
            properties -> new Item(properties.sword(ModToolMaterials.ZIRCON, 3f, -2.4f)));
    public static final DeferredItem<Item> ZIRCON_PICKAXE = ITEMS.registerItem("zircon_pickaxe",
            properties -> new Item(properties.pickaxe(ModToolMaterials.ZIRCON, 1f, -2.8f)));
    public static final DeferredItem<Item> ZIRCON_SHOVEL = ITEMS.registerItem("zircon_shovel",
            properties -> new ShovelItem(ModToolMaterials.ZIRCON, 1.5f, -3f, properties));
    public static final DeferredItem<Item> ZIRCON_AXE = ITEMS.registerItem("zircon_axe",
            properties -> new AxeItem(ModToolMaterials.ZIRCON, 6f, -3.2f, properties));
    public static final DeferredItem<Item> ZIRCON_HOE = ITEMS.registerItem("zircon_hoe",
            properties -> new HoeItem(ModToolMaterials.ZIRCON, 0f, -3f, properties));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
