package net.kaupenjoe.mccourse;

import net.kaupenjoe.mccourse.datagen.*;
import net.kaupenjoe.mccourse.datagen.villager.ModPOITags;
import net.kaupenjoe.mccourse.datagen.villager.ModTradeSets;
import net.kaupenjoe.mccourse.datagen.villager.ModVillagerTradeTags;
import net.kaupenjoe.mccourse.datagen.villager.ModVillagerTrades;
import net.kaupenjoe.mccourse.enchantment.ModEnchantments;
import net.kaupenjoe.mccourse.worldgen.ModBiomeModifiers;
import net.kaupenjoe.mccourse.worldgen.ModFeatures;
import net.kaupenjoe.mccourse.worldgen.ModPlacedFeatures;
import net.kaupenjoe.mccourse.worldgen.biome.ModBiomes;
import net.kaupenjoe.mccourse.worldgen.dimension.ModDimensions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;


@EventBusSubscriber(modid = MCCourse.MOD_ID)
public class MCCourseDataGenerators {
    @SubscribeEvent
    public static void gatherClientRegistryData(GatherDataRegistryEntriesEvent event) {
        event
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

            .add(Registries.BIOME, ModBiomes::bootstrap)

            .add(Registries.LOOT_TABLE, new LootTableProvider(Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModEntityLootTableProvider::new, LootContextParamSets.ENTITY)
            )))
            .add(Registries.ADVANCEMENT, new AdvancementProvider(List.of(MCCourseAdvancements::create)))
            .add(ModRecipeProvider.create());
    }

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getReloadableLookupProvider();

        generator.addProvider(true, new ModModelProvider(packOutput));

        generator.addProvider(true, new ModBlockTagProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModDataMapProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModItemTagProvider(packOutput, lookupProvider));

        generator.addProvider(true, new ModEquipmentAsset(packOutput));
        generator.addProvider(true, new ModGlobalLootModifierProvider(packOutput, lookupProvider));

        generator.addProvider(true, new ModSoundsProvider(packOutput));

        generator.addProvider(true, new ModVillagerTradeTags(packOutput, lookupProvider));
        generator.addProvider(true, new ModPOITags(packOutput, lookupProvider));
        generator.addProvider(true, new ModPaintingTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModEnchantmentTagProvider(packOutput, lookupProvider));

        generator.addProvider(true, new ModFluidTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModEntityTypeTagProvider(packOutput, lookupProvider));
    }
}
