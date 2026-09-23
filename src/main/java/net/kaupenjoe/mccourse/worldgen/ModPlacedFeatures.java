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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ZIRCON_OVERWORLD_ORES_PLACED_KEY = registerKey("zircon_overworld_ores_placed");
    public static final ResourceKey<PlacedFeature> ZIRCON_NETHER_ORES_PLACED_KEY = registerKey("zircon_nether_ores_placed");
    public static final ResourceKey<PlacedFeature> ZIRCON_END_ORES_PLACED_KEY = registerKey("zircon_end_ores_placed");

    public static final ResourceKey<PlacedFeature> EBONY_TREE_PLACED_KEY = registerKey("ebony_tree_placed");

    public static final ResourceKey<PlacedFeature> CATMINT_FLOWER_PLACED_KEY = registerKey("catmint_flower_placed");
    public static final ResourceKey<PlacedFeature> GOJI_BERRY_BUSH_PLACED_KEY = registerKey("goji_berry_bush_placed");

    public static final ResourceKey<PlacedFeature> ZIRCON_GEODE_PLACED_KEY = registerKey("zircon_geode_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.FEATURE);

        register(context, ZIRCON_OVERWORLD_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModFeatures.ZIRCON_OVERWORLD_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, ZIRCON_NETHER_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModFeatures.ZIRCON_NETHER_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, ZIRCON_END_ORES_PLACED_KEY, configuredFeatures.getOrThrow(ModFeatures.ZIRCON_END_ORES_KEY),
                ModOrePlacements.commonOrePlacement(12,
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, EBONY_TREE_PLACED_KEY, configuredFeatures.getOrThrow(ModFeatures.EBONY_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        ModBlocks.EBONY_SAPLING.get()));

        register(context, CATMINT_FLOWER_PLACED_KEY, configuredFeatures.getOrThrow(ModFeatures.CATMINT_FLOWER_KEY),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, GOJI_BERRY_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(ModFeatures.GOJI_BERRY_BUSH_KEY),
                List.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, ZIRCON_GEODE_PLACED_KEY, configuredFeatures.getOrThrow(ModFeatures.ZIRCON_GEODE_KEY),
                List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(5), VerticalAnchor.absolute(50)), BiomeFilter.biome()));


    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<Feature> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
