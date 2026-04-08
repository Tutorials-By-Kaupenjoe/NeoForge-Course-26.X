package net.kaupenjoe.mccourse.loot;

import com.mojang.serialization.MapCodec;
import net.kaupenjoe.mccourse.MCCourse;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MCCourse.MOD_ID);

    public static final Supplier<MapCodec<AddItemStackModifier>> ADD_ITEMSTACK =
            LOOT_MODIFIERS.register("add_itemstack", () -> AddItemStackModifier.CODEC);


    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}
