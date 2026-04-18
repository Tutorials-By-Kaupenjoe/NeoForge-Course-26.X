package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.tag.ModTags;
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
                .add(ModBlocks.ZIRCON_END_ORE.get())
                .add(ModBlocks.ZIRCON_STAIRS.get())
                .add(ModBlocks.ZIRCON_SLAB.get())
                .add(ModBlocks.ZIRCON_PRESSURE_PLATE.get())
                .add(ModBlocks.ZIRCON_BUTTON.get())
                .add(ModBlocks.ZIRCON_FENCE.get())
                .add(ModBlocks.ZIRCON_FENCE_GATE.get())
                .add(ModBlocks.ZIRCON_WALL.get())
                .add(ModBlocks.ZIRCON_DOOR.get())
                .add(ModBlocks.ZIRCON_TRAPDOOR.get())
                .add(ModBlocks.ZIRCON_LAMP.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ZIRCON_DEEPSLATE_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ZIRCON_END_ORE.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.ZIRCON_STAIRS.get());
        tag(BlockTags.SLABS)
                .add(ModBlocks.ZIRCON_SLAB.get());

        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.ZIRCON_PRESSURE_PLATE.get());
        tag(BlockTags.BUTTONS)
                .add(ModBlocks.ZIRCON_BUTTON.get());

        tag(BlockTags.FENCES)
                .add(ModBlocks.ZIRCON_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ZIRCON_FENCE_GATE.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.ZIRCON_WALL.get());

        tag(BlockTags.DOORS)
                .add(ModBlocks.ZIRCON_DOOR.get());
        tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.ZIRCON_TRAPDOOR.get());

        tag(ModTags.Blocks.NEEDS_ZIRCON_TOOL)
                .add(ModBlocks.ZIRCON_NETHER_ORE.get())
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ZIRCON_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_ZIRCON_TOOL);

        tag(ModTags.Blocks.PAXEL_MINEABLE)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL);

        tag(BlockTags.CROPS)
                .add(ModBlocks.RADISH_CROP.get())
                .add(ModBlocks.RICE_CROP.get());
        tag(BlockTags.MAINTAINS_FARMLAND)
                .add(ModBlocks.RADISH_CROP.get());

        tag(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_CATMINT.get())
                .add(ModBlocks.POTTED_EBONY_SAPLING.get());
        tag(BlockTags.FLOWERS)
                .add(ModBlocks.CATMINT.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.EBONY_LOG.get())
                .add(ModBlocks.EBONY_WOOD.get())
                .add(ModBlocks.STRIPPED_EBONY_LOG.get())
                .add(ModBlocks.STRIPPED_EBONY_WOOD.get());

        tag(BlockTags.LEAVES)
                .add(ModBlocks.EBONY_LEAVES.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.EBONY_SAPLING.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.EBONY_PLANKS.get());

    }
}
