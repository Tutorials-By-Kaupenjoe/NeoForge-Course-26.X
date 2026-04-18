package net.kaupenjoe.mccourse.worldgen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZIRCON_OVERWORLD_ORES_KEY = registerKey("zircon_overworld_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZIRCON_NETHER_ORES_KEY = registerKey("zircon_nether_ores");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZIRCON_END_ORES_KEY = registerKey("zircon_end_ores");

    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY_TREE_KEY = registerKey("ebony_tree_key");


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
                new BendingTrunkPlacer(3, 3, 5, 3, ConstantInt.of(2)),

                BlockStateProvider.simple(ModBlocks.EBONY_LEAVES.get()),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1)),

                new TwoLayersFeatureSize(1, 0, 2))
                .belowTrunkProvider(BlockStateProvider.simple(Blocks.STONE))
                .build());


    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
