package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, MCCourse.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ZIRCON_BLOCK.get())
                .add(ModBlocks.RAW_ZIRCON_BLOCK.get())
                .add(ModBlocks.ZIRCON_ORE.get())
                .add(ModBlocks.ZIRCON_DEEPSLATE_ORE.get())
                .add(ModBlocks.ZIRCON_NETHER_ORE.get())
                .add(ModBlocks.ZIRCON_END_ORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ZIRCON_DEEPSLATE_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ZIRCON_END_ORE.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.ZIRCON_STAIRS.get());
        tag(BlockTags.SLABS)
                .add(ModBlocks.ZIRCON_SLAB.get());
    }
}
