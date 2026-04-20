package net.kaupenjoe.mccourse.worldgen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_ZIRCON_OVERWORLD_ORES = registerKey("add_zircon_overworld_ores");
    public static final ResourceKey<BiomeModifier> ADD_ZIRCON_NETHER_ORES = registerKey("add_zircon_nether_ores");
    public static final ResourceKey<BiomeModifier> ADD_ZIRCON_END_ORES = registerKey("add_zircon_end_ores");

    public static final ResourceKey<BiomeModifier> ADD_EBONY_TREE = registerKey("add_ebony_tree");

    public static final ResourceKey<BiomeModifier> ADD_CATMINT_FLOWER = registerKey("add_catmint_flower");
    public static final ResourceKey<BiomeModifier> ADD_GOJI_BERRY_BUSH = registerKey("add_goji_berry_bush");

    public static final ResourceKey<BiomeModifier> ADD_ZIRCON_GEODE = registerKey("add_zircon_geode");

    public static final ResourceKey<BiomeModifier> SPAWN_PENGUIN = registerKey("spawn_penguin");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ZIRCON_OVERWORLD_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZIRCON_OVERWORLD_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Example for individual biomes!
        // context.register(ADD_ZIRCON_OVERWORLD_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
        //         HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.CHERRY_GROVE)),
        //         HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZIRCON_OVERWORLD_ORES_PLACED_KEY)),
        //         GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ZIRCON_NETHER_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZIRCON_NETHER_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ZIRCON_END_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZIRCON_END_ORES_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_EBONY_TREE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.STONY_SHORE)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.EBONY_TREE_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_CATMINT_FLOWER, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CATMINT_FLOWER_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_GOJI_BERRY_BUSH, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.CHERRY_GROVE), biomes.getOrThrow(Biomes.FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GOJI_BERRY_BUSH_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_ZIRCON_GEODE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZIRCON_GEODE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(SPAWN_PENGUIN, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FROZEN_RIVER),
                        biomes.getOrThrow(Biomes.FROZEN_OCEAN), biomes.getOrThrow(Biomes.FROZEN_PEAKS),
                        biomes.getOrThrow(Biomes.TAIGA)),
                WeightedList.of(List.of(new Weighted<>(new MobSpawnSettings.SpawnerData(ModEntities.PENGUIN.get(), 2, 3), 10)))));
    }


    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
    }
}
