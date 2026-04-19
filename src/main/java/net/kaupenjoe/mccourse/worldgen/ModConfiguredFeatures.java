package net.kaupenjoe.mccourse.worldgen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.worldgen.tree.InvertedPyramidFoliagePlacer;
import net.kaupenjoe.mccourse.worldgen.tree.SpiralTrunkPlacer;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZIRCON_OVERWORLD_ORES_KEY = registerKey("zircon_overworld_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZIRCON_NETHER_ORES_KEY = registerKey("zircon_nether_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZIRCON_END_ORES_KEY = registerKey("zircon_end_ores");

    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY_TREE_KEY = registerKey("ebony_tree_key");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CATMINT_FLOWER_KEY = registerKey("catmint_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOJI_BERRY_BUSH_KEY = registerKey("goji_berry_bush");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ZIRCON_GEODE_KEY = registerKey("zircon_geode");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        List<OreConfiguration.TargetBlockState> overworldZirconOres = List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ModBlocks.ZIRCON_ORE.get().defaultBlockState()),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), ModBlocks.ZIRCON_DEEPSLATE_ORE.get().defaultBlockState()));

        register(context, ZIRCON_OVERWORLD_ORES_KEY, Feature.ORE, new OreConfiguration(overworldZirconOres, 12));
        register(context, ZIRCON_NETHER_ORES_KEY, Feature.ORE, new OreConfiguration(
                new BlockMatchTest(Blocks.NETHERRACK), ModBlocks.ZIRCON_NETHER_ORE.get().defaultBlockState(), 12));
        register(context, ZIRCON_END_ORES_KEY, Feature.ORE, new OreConfiguration(
                new BlockMatchTest(Blocks.END_STONE), ModBlocks.ZIRCON_END_ORE.get().defaultBlockState(), 12));

        register(context, EBONY_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.EBONY_LOG.get()),
                new SpiralTrunkPlacer(4, 3, 5),

                BlockStateProvider.simple(ModBlocks.EBONY_LEAVES.get()),
                new InvertedPyramidFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 3),

                new TwoLayersFeatureSize(1, 0, 2))
                .belowTrunkProvider(BlockStateProvider.simple(Blocks.STONE))
                .build());

        register(context, CATMINT_FLOWER_KEY, Feature.SIMPLE_RANDOM_SELECTOR,
                new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(PlacementUtils.inlinePlaced(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CATMINT.get())),
                                CountPlacement.of(32),
                                RandomOffsetPlacement.ofTriangle(6, 3),
                                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)))));

        register(context, GOJI_BERRY_BUSH_KEY, Feature.SIMPLE_RANDOM_SELECTOR,
                new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(PlacementUtils.inlinePlaced(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.GOJI_BERRY_BUSH.get()
                                        .defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3))),
                                CountPlacement.of(32),
                                RandomOffsetPlacement.ofTriangle(6, 3),
                                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)))));

        register(context, ZIRCON_GEODE_KEY, Feature.GEODE,
                new GeodeConfiguration(new GeodeBlockSettings(
                                BlockStateProvider.simple(Blocks.AIR),
                                BlockStateProvider.simple(Blocks.DEEPSLATE),
                                BlockStateProvider.simple(ModBlocks.RAW_ZIRCON_BLOCK.get()),
                                BlockStateProvider.simple(Blocks.EMERALD_BLOCK),
                                BlockStateProvider.simple(Blocks.DIRT),
                                List.of(
                                        ModBlocks.ZIRCON_BLOCK.get().defaultBlockState(),
                                        ModBlocks.ZIRCON_ORE.get().defaultBlockState(),
                                        ModBlocks.MAGIC_BLOCK.get().defaultBlockState()
                                ),
                                BlockTags.FEATURES_CANNOT_REPLACE,
                                BlockTags.GEODE_INVALID_BLOCKS
                        ),
                        new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackSettings(0.95, 2.0, 2),
                        0.35,
                        0.083,
                        true,
                        UniformInt.of(4, 6),
                        UniformInt.of(3, 4),
                        UniformInt.of(1, 2),
                        -16,
                        16,
                        0.05,
                        1));


    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
