package net.kaupenjoe.mccourse;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class MCConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CHISEL_SHOW_TOOLTIP = BUILDER
            .comment("Whether the Chisel should show its tooltips")
            .define("showChiselTooltip", true);

    public static final ModConfigSpec.IntValue CHISEL_MANA_USAGE = BUILDER
            .comment("How much mana the Chisel uses per use")
            .defineInRange("chiselManaUsage", 1, 0, 5);


    static final ModConfigSpec SPEC = BUILDER.build();
}
