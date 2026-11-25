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
                .add(ModBlocks.ZIRCON_BLOCK.key())
                .add(ModBlocks.RAW_ZIRCON_BLOCK.key())
                .add(ModBlocks.ZIRCON_ORE.key())
                .add(ModBlocks.ZIRCON_DEEPSLATE_ORE.key())
                .add(ModBlocks.ZIRCON_NETHER_ORE.key())
                .add(ModBlocks.ZIRCON_END_ORE.key())
                .add(ModBlocks.ZIRCON_STAIRS.key())
                .add(ModBlocks.ZIRCON_SLAB.key())
                .add(ModBlocks.ZIRCON_PRESSURE_PLATE.key())
                .add(ModBlocks.ZIRCON_BUTTON.key())
                .add(ModBlocks.ZIRCON_FENCE.key())
                .add(ModBlocks.ZIRCON_FENCE_GATE.key())
                .add(ModBlocks.ZIRCON_WALL.key())
                .add(ModBlocks.ZIRCON_DOOR.key())
                .add(ModBlocks.ZIRCON_TRAPDOOR.key())
                .add(ModBlocks.ZIRCON_LAMP.key())
                .add(ModBlocks.CRYSTALLIZER.key())
                .add(ModBlocks.COAL_GENERATOR.key())
                .add(ModBlocks.BATTERY.key())
                .add(ModBlocks.GROWTH_CHAMBER.key())
                .add(ModBlocks.ATOMIC_SEPARATOR.key());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ZIRCON_DEEPSLATE_ORE.key());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ZIRCON_END_ORE.key());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.ZIRCON_STAIRS.key());
        tag(BlockTags.SLABS)
                .add(ModBlocks.ZIRCON_SLAB.key());

        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.ZIRCON_PRESSURE_PLATE.key());
        tag(BlockTags.BUTTONS)
                .add(ModBlocks.ZIRCON_BUTTON.key());

        tag(BlockTags.FENCES)
                .add(ModBlocks.ZIRCON_FENCE.key());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ZIRCON_FENCE_GATE.key());
        tag(BlockTags.WALLS)
                .add(ModBlocks.ZIRCON_WALL.key());

        tag(BlockTags.DOORS)
                .add(ModBlocks.ZIRCON_DOOR.key());
        tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.ZIRCON_TRAPDOOR.key());

        tag(ModTags.Blocks.NEEDS_ZIRCON_TOOL)
                .add(ModBlocks.ZIRCON_NETHER_ORE.key())
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ZIRCON_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_ZIRCON_TOOL);

        tag(ModTags.Blocks.PAXEL_MINEABLE)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL);

        tag(BlockTags.CROPS)
                .add(ModBlocks.RADISH_CROP.key())
                .add(ModBlocks.RICE_CROP.key());
        tag(BlockTags.MAINTAINS_FARMLAND)
                .add(ModBlocks.RADISH_CROP.key());

        tag(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_CATMINT.key())
                .add(ModBlocks.POTTED_EBONY_SAPLING.key());
        tag(BlockTags.FLOWERS)
                .add(ModBlocks.CATMINT.key());

        tag(BlockTags.LEAVES)
                .add(ModBlocks.EBONY_LEAVES.key());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.EBONY_PLANKS.key());

    }
}
