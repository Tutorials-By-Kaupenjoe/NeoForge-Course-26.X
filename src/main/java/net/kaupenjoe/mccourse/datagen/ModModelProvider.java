package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.custom.ZirconLampBlock;
import net.kaupenjoe.mccourse.component.ModDataComponentTypes;
import net.kaupenjoe.mccourse.item.ModArmorMaterials;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ConditionalItemModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.HasComponent;
import net.minecraft.data.PackOutput;

import java.util.Optional;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, MCCourse.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        /* ITEMS */
        itemModels.generateFlatItem(ModItems.ZIRCON.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_ZIRCON.get(), ModelTemplates.FLAT_ITEM);
        // itemModels.generateFlatItem(ModItems.CHISEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RADISH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.FROSTFIRE_ICE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.ZIRCON_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ZIRCON_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ZIRCON_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ZIRCON_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ZIRCON_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ZIRCON_PAXEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.ZIRCON_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModels.generateTrimmableItem(ModItems.ZIRCON_HELMET.get(), ModArmorMaterials.ZIRCON_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModels.generateTrimmableItem(ModItems.ZIRCON_CHESTPLATE.get(), ModArmorMaterials.ZIRCON_KEY, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModels.generateTrimmableItem(ModItems.ZIRCON_LEGGINGS.get(), ModArmorMaterials.ZIRCON_KEY, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModels.generateTrimmableItem(ModItems.ZIRCON_BOOTS.get(), ModArmorMaterials.ZIRCON_KEY, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);

        itemModels.generateFlatItem(ModItems.ZIRCON_HORSE_ARMOR.get(), ModelTemplates.FLAT_ITEM);

        ItemModel.Unbaked unbakedChisel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(ModItems.CHISEL.get(), ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked unbakedUsedChisel = ItemModelUtils.plainModel(itemModels.createFlatItemModel(ModItems.CHISEL.get(), "_used", ModelTemplates.FLAT_ITEM));
        itemModels.itemModelOutput.register(ModItems.CHISEL.get(),
                new ClientItem(new ConditionalItemModel.Unbaked(Optional.empty(), new HasComponent(ModDataComponentTypes.COORDINATES.get(), false),
                        unbakedUsedChisel, unbakedChisel), new ClientItem.Properties(false, false, 1f)));


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
                .button(ModBlocks.ZIRCON_BUTTON.get())
                .fence(ModBlocks.ZIRCON_FENCE.get())
                .fenceGate(ModBlocks.ZIRCON_FENCE_GATE.get())
                .wall(ModBlocks.ZIRCON_WALL.get())
                .door(ModBlocks.ZIRCON_DOOR.get())
                .trapdoor(ModBlocks.ZIRCON_TRAPDOOR.get());

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.ZIRCON_LAMP.get())
                        .with(BlockModelGenerators.createBooleanModelDispatch(ZirconLampBlock.CLICKED,
                                BlockModelGenerators.plainVariant(blockModels.createSuffixedVariant(ModBlocks.ZIRCON_LAMP.get(), "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube)),
                                BlockModelGenerators.plainVariant(TexturedModel.CUBE.create(ModBlocks.ZIRCON_LAMP.get(), blockModels.modelOutput)))));



    }
}
