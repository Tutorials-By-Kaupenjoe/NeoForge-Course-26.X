package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        // builder(NeoForgeDataMaps.FURNACE_FUELS)
        //         .add(ModItems.FROSTFIRE_ICE.getId(), new FurnaceFuel(2400), false);
//
        // builder(NeoForgeDataMaps.COMPOSTABLES)
        //         .add(ModItems.RADISH_SEEDS.getId(), new Compostable(0.3f), false)
        //         .add(ModItems.RADISH.getId(), new Compostable(0.65f), false);

        // ???
        builder(NeoForgeDataMaps.BLOCK_TRANSFORM_APPENDERS)
                .add(BlockTransformers.AXE, new BlockTransformAppender(
                        List.of(BlockTransformer.BlockTransformData.builder(BlockPredicate.matchesBlocks(ModBlocks.EBONY_LOG.get()), ModBlocks.STRIPPED_EBONY_LOG.get()).build(),
                                BlockTransformer.BlockTransformData.builder(BlockPredicate.matchesBlocks(ModBlocks.EBONY_WOOD.get()), ModBlocks.STRIPPED_EBONY_WOOD.get()).build())
                ), false);
    }
}
