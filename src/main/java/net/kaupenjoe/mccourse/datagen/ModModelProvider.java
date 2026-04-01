package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, MCCourse.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        /* ITEMS */
        itemModels.generateFlatItem(ModItems.ZIRCON.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_ZIRCON.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHISEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RADISH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FROSTFIRE_ICE.get(), ModelTemplates.FLAT_ITEM);



        /* BLOCKS */
        blockModels.createTrivialCube(ModBlocks.RAW_ZIRCON_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.ZIRCON_ORE.get());
        blockModels.createTrivialCube(ModBlocks.ZIRCON_DEEPSLATE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.ZIRCON_NETHER_ORE.get());
        blockModels.createTrivialCube(ModBlocks.ZIRCON_END_ORE.get());
        blockModels.createTrivialCube(ModBlocks.MAGIC_BLOCK.get());

        blockModels.family(ModBlocks.ZIRCON_BLOCK.get())
                .stairs(ModBlocks.ZIRCON_STAIRS.get())
                .slab(ModBlocks.ZIRCON_SLAB.get())
                .pressurePlate(ModBlocks.ZIRCON_PRESSURE_PLATE.get())
                .button(ModBlocks.ZIRCON_BUTTON.get());


    }
}
