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
                .add(ModBlocks.ZIRCON_BLOCK.getKey())
                .add(ModBlocks.RAW_ZIRCON_BLOCK.getKey())
                .add(ModBlocks.ZIRCON_ORE.getKey())
                .add(ModBlocks.ZIRCON_DEEPSLATE_ORE.getKey())
                .add(ModBlocks.ZIRCON_NETHER_ORE.getKey())
                .add(ModBlocks.ZIRCON_END_ORE.getKey())
                .add(ModBlocks.ZIRCON_STAIRS.getKey())
                .add(ModBlocks.ZIRCON_SLAB.getKey())
                .add(ModBlocks.ZIRCON_PRESSURE_PLATE.getKey())
                .add(ModBlocks.ZIRCON_BUTTON.getKey())
                .add(ModBlocks.ZIRCON_FENCE.getKey())
                .add(ModBlocks.ZIRCON_FENCE_GATE.getKey())
                .add(ModBlocks.ZIRCON_WALL.getKey())
                .add(ModBlocks.ZIRCON_DOOR.getKey())
                .add(ModBlocks.ZIRCON_TRAPDOOR.getKey())
                .add(ModBlocks.ZIRCON_LAMP.getKey())
                .add(ModBlocks.CRYSTALLIZER.getKey())
                .add(ModBlocks.COAL_GENERATOR.getKey())
                .add(ModBlocks.BATTERY.getKey())
                .add(ModBlocks.GROWTH_CHAMBER.getKey());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ZIRCON_DEEPSLATE_ORE.getKey());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ZIRCON_END_ORE.getKey());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.ZIRCON_STAIRS.getKey());
        tag(BlockTags.SLABS)
                .add(ModBlocks.ZIRCON_SLAB.getKey());

        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.ZIRCON_PRESSURE_PLATE.getKey());
        tag(BlockTags.BUTTONS)
                .add(ModBlocks.ZIRCON_BUTTON.getKey());

        tag(BlockTags.FENCES)
                .add(ModBlocks.ZIRCON_FENCE.getKey());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ZIRCON_FENCE_GATE.getKey());
        tag(BlockTags.WALLS)
                .add(ModBlocks.ZIRCON_WALL.getKey());

        tag(BlockTags.DOORS)
                .add(ModBlocks.ZIRCON_DOOR.getKey());
        tag(BlockTags.TRAPDOORS)
                .add(ModBlocks.ZIRCON_TRAPDOOR.getKey());

        tag(ModTags.Blocks.NEEDS_ZIRCON_TOOL)
                .add(ModBlocks.ZIRCON_NETHER_ORE.getKey())
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_ZIRCON_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_ZIRCON_TOOL);

        tag(ModTags.Blocks.PAXEL_MINEABLE)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL);

        tag(BlockTags.CROPS)
                .add(ModBlocks.RADISH_CROP.getKey())
                .add(ModBlocks.RICE_CROP.getKey());
        tag(BlockTags.MAINTAINS_FARMLAND)
                .add(ModBlocks.RADISH_CROP.getKey());

        tag(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_CATMINT.getKey())
                .add(ModBlocks.POTTED_EBONY_SAPLING.getKey());
        tag(BlockTags.FLOWERS)
                .add(ModBlocks.CATMINT.getKey());

        tag(BlockTags.LEAVES)
                .add(ModBlocks.EBONY_LEAVES.getKey());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.EBONY_PLANKS.getKey());

    }
}
