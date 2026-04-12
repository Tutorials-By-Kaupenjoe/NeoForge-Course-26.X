package net.kaupenjoe.mccourse.effect;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MCCourse.MOD_ID);

    public static final Holder<MobEffect> STINKY_EFFECT = MOB_EFFECTS.register("stinky",
            () -> new StinkyEffect(MobEffectCategory.NEUTRAL, 0xb8571c));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
