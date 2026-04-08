package net.kaupenjoe.mccourse.consumeffect;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModConsumeEffects {
    public static final DeferredRegister<ConsumeEffect.Type<?>> CONSUME_EFFECT_TYPES =
            DeferredRegister.create(BuiltInRegistries.CONSUME_EFFECT_TYPE, MCCourse.MOD_ID);

    public static final Supplier<ConsumeEffect.Type<ManaAddConsumeEffect>> MANA_ADD_CONSUME_EFFECT =
            CONSUME_EFFECT_TYPES.register("mana_add", () -> new ConsumeEffect.Type<>(ManaAddConsumeEffect.CODEC,
                    ManaAddConsumeEffect.STREAM_CODEC));


    public static void register(IEventBus eventBus) {
        CONSUME_EFFECT_TYPES.register(eventBus);
    }
}
