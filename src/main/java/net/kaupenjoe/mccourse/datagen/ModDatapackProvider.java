package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.datagen.villager.ModTradeSets;
import net.kaupenjoe.mccourse.datagen.villager.ModVillagerTrades;
import net.kaupenjoe.mccourse.enchantment.ModEnchantments;
import net.kaupenjoe.mccourse.worldgen.ModBiomeModifiers;
import net.kaupenjoe.mccourse.worldgen.ModFeatures;
import net.kaupenjoe.mccourse.worldgen.ModPlacedFeatures;
import net.kaupenjoe.mccourse.worldgen.biome.ModBiomes;
import net.kaupenjoe.mccourse.worldgen.dimension.ModDimensions;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;

public class ModDatapackProvider {
    public static final RegistrySetBuilder WORLD_BUILDER = new RegistrySetBuilder()
            .add(Registries.JUKEBOX_SONG, ModJukeboxSongs::bootstrap)
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap)
            .add(Registries.VILLAGER_TRADE, ModVillagerTrades::bootstrap)
            .add(Registries.TRADE_SET, ModTradeSets::bootstrap)
            .add(Registries.PAINTING_VARIANT, ModPaintings::bootstrap)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)

            .add(Registries.FEATURE, ModFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)

            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)

            .add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem)

            .add(Registries.BIOME, ModBiomes::bootstrap);

    public static final RegistrySetBuilder RELOADABLE_BUILDER = new RegistrySetBuilder()
            .add(Registries.LOOT_TABLE, new LootTableProvider(Set.of(), List.of(
                    new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                    new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new, LootContextParamSets.ENTITY)
            )))
            .add(Registries.ADVANCEMENT, new AdvancementProvider(List.of(MCCourseAdvancements::create)))
            .add(ModRecipeProvider.create());
}
