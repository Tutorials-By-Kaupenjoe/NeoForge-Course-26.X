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
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModFeatures {
    public static final ResourceKey<Feature> ZIRCON_OVERWORLD_ORES_KEY = registerKey("zircon_overworld_ores");
    public static final ResourceKey<Feature> ZIRCON_NETHER_ORES_KEY = registerKey("zircon_nether_ores");
    public static final ResourceKey<Feature> ZIRCON_END_ORES_KEY = registerKey("zircon_end_ores");

    public static final ResourceKey<Feature> EBONY_TREE_KEY = registerKey("ebony_tree_key");

    public static final ResourceKey<Feature> CATMINT_FLOWER_KEY = registerKey("catmint_flower");
    public static final ResourceKey<Feature> GOJI_BERRY_BUSH_KEY = registerKey("goji_berry_bush");

    public static final ResourceKey<Feature> ZIRCON_GEODE_KEY = registerKey("zircon_geode");


    public static void bootstrap(BootstrapContext<Feature> context) {
        var blocks = context.lookup(Registries.BLOCK);

        List<BlockReplacement> overworldZirconOres = List.of(
                BlockReplacement.replace(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ModBlocks.ZIRCON_ORE.get().defaultBlockState()),
                BlockReplacement.replace(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), ModBlocks.ZIRCON_DEEPSLATE_ORE.get().defaultBlockState()));

        context.register(ZIRCON_OVERWORLD_ORES_KEY, new OreFeature(overworldZirconOres, 12));
        context.register(ZIRCON_NETHER_ORES_KEY, new OreFeature(
                new BlockMatchTest(Blocks.NETHERRACK), ModBlocks.ZIRCON_NETHER_ORE.get().defaultBlockState(), 12));
        context.register(ZIRCON_END_ORES_KEY, new OreFeature(
                new BlockMatchTest(Blocks.END_STONE), ModBlocks.ZIRCON_END_ORE.get().defaultBlockState(), 12));

        context.register(EBONY_TREE_KEY, new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.EBONY_LOG.get()),
                new SpiralTrunkPlacer(4, 3, 5),

                BlockStateProvider.of(ModBlocks.EBONY_LEAVES.get()),
                new InvertedPyramidFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), 3),

                new TwoLayersFeatureSize(1, 0, 2),
                BlockStateProvider.holderOf(Blocks.STONE))
                .build());

        context.register(CATMINT_FLOWER_KEY, new SimpleRandomSelectorFeature(
                        HolderSet.direct(PlacementUtils.inlinePlaced(
                                new SimpleBlockFeature(BlockStateProvider.of(ModBlocks.CATMINT.get())),
                                CountPlacement.of(32),
                                OffsetPlacement.ofTriangle(6, 3),
                                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)))));

        context.register(GOJI_BERRY_BUSH_KEY, new SimpleRandomSelectorFeature(
                        HolderSet.direct(PlacementUtils.inlinePlaced(
                                new SimpleBlockFeature(BlockStateProvider.of(ModBlocks.GOJI_BERRY_BUSH.get()
                                        .defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3))),
                                CountPlacement.of(32),
                                OffsetPlacement.ofTriangle(6, 3),
                                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)))));

        context.register(ZIRCON_GEODE_KEY, new GeodeFeature(new GeodeBlockSettings(
                                BlockStateProvider.holderOf(Blocks.AIR),
                                BlockStateProvider.holderOf(Blocks.DEEPSLATE),
                                BlockStateProvider.holderOf(ModBlocks.RAW_ZIRCON_BLOCK.get()),
                                BlockStateProvider.holderOf(Blocks.EMERALD_BLOCK),
                                BlockStateProvider.holderOf(Blocks.DIRT),
                                List.of(
                                        ModBlocks.ZIRCON_BLOCK.get().defaultBlockState(),
                                        ModBlocks.ZIRCON_ORE.get().defaultBlockState(),
                                        ModBlocks.MAGIC_BLOCK.get().defaultBlockState()
                                ),
                        blocks.getOrThrow(BlockTags.FEATURES_CANNOT_REPLACE),
                        blocks.getOrThrow(BlockTags.GEODE_INVALID_BLOCKS)
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

    public static ResourceKey<Feature> registerKey(String name) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
    }
}
