package net.kaupenjoe.mccourse.menu;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.menu.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, MCCourse.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<WarturtleMenu>> WARTURTLE_MENU =
            registerMenuType("warturtle_menu", WarturtleMenu::create);

    public static final DeferredHolder<MenuType<?>, MenuType<PedestalMenu>> PEDESTAL_MENU =
            registerMenuType("pedestal_menu", PedestalMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<CrystallizerMenu>> CRYSTALLIZER_MENU =
            registerMenuType("crystallizer_menu", CrystallizerMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU =
            registerMenuType("coal_generator_menu", CoalGeneratorMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<BatteryMenu>> BATTERY_MENU =
            registerMenuType("battery_menu", BatteryMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<GrowthChamberMenu>> GROWTH_CHAMBER_MENU =
            registerMenuType("growth_chamber_menu", GrowthChamberMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<AtomicSeparatorMenu>> ATOMIC_SEPARATOR_MENU =
            registerMenuType("atomic_separator_menu", AtomicSeparatorMenu::new);


    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                              IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
