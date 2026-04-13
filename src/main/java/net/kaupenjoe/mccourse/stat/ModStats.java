package net.kaupenjoe.mccourse.stat;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModStats {
    public static final DeferredRegister<Identifier> CUSTOM_STATS =
            DeferredRegister.create(BuiltInRegistries.CUSTOM_STAT, MCCourse.MOD_ID);

    public static final Supplier<Identifier> CHISEL_USED_STAT = makeCustomStat("chisel_used");
    public static final Supplier<Identifier> MANA_USED_TOTAL_STAT = makeCustomStat("mana_used_total");


    private static Supplier<Identifier> makeCustomStat(String key) {
        Identifier statIdentifier = Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, key);
        return CUSTOM_STATS.register(key, () -> statIdentifier);
    }

    public static void register(IEventBus eventBus) {
        CUSTOM_STATS.register(eventBus);
    }
}
