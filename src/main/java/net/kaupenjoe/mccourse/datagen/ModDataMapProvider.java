package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModItems.FROSTFIRE_ICE.getId(), new FurnaceFuel(2400), false);

        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.RADISH_SEEDS.getId(), new Compostable(0.3f), false)
                .add(ModItems.RADISH.getId(), new Compostable(0.65f), false);

        builder(NeoForgeDataMaps.STRIPPABLES)
                .add(ModBlocks.EBONY_LOG, new Strippable(ModBlocks.STRIPPED_EBONY_LOG.get()), false)
                .add(ModBlocks.EBONY_WOOD, new Strippable(ModBlocks.STRIPPED_EBONY_WOOD.get()), false);
    }
}
