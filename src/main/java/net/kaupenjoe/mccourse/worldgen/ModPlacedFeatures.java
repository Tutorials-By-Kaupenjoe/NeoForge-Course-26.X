package net.kaupenjoe.mccourse.worldgen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ZIRCON_OVERWORLD_ORES_PLACED_KEY = registerKey("zircon_overworld_ores_placed");
    public static final ResourceKey<PlacedFeature> ZIRCON_NETHER_ORES_PLACED_KEY = registerKey("zircon_nether_ores_placed");
    public static final ResourceKey<PlacedFeature> ZIRCON_END_ORES_PLACED_KEY = registerKey("zircon_end_ores_placed");

    public static final ResourceKey<PlacedFeature> EBONY_TREE_PLACED_KEY = registerKey("ebony_tree_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ZIRCON_OVERWORLD_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ZIRCON_OVERWORLD_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, ZIRCON_NETHER_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ZIRCON_NETHER_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, ZIRCON_END_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ZIRCON_END_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, EBONY_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.EBONY_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        ModBlocks.EBONY_SAPLING.get()));

    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
