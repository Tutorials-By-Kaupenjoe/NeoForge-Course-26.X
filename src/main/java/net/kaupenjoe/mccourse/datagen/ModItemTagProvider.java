package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.WeatheringCopper;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, MCCourse.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.ZIRCON.key())
                .add(ItemIds.RAW_IRON)
                .add(BlockItemIds.COPPER_BLOCK.weathering().unaffected().item());

        tag(ModTags.Items.ZIRCON_REPAIRABLES)
                .add(ModItems.ZIRCON.key());

        tag(ItemTags.SWORDS).add(ModItems.ZIRCON_SWORD.key());
        tag(ItemTags.PICKAXES).add(ModItems.ZIRCON_PICKAXE.key()).add(ModItems.ZIRCON_PAXEL.key()).add(ModItems.ZIRCON_HAMMER.key());
        tag(ItemTags.SHOVELS).add(ModItems.ZIRCON_SHOVEL.key()).add(ModItems.ZIRCON_PAXEL.key());
        tag(ItemTags.AXES).add(ModItems.ZIRCON_AXE.key()).add(ModItems.ZIRCON_PAXEL.key());
        tag(ItemTags.HOES).add(ModItems.ZIRCON_HOE.key());

        tag(ItemTags.HEAD_ARMOR).add(ModItems.ZIRCON_HELMET.key());
        tag(ItemTags.CHEST_ARMOR).add(ModItems.ZIRCON_CHESTPLATE.key());
        tag(ItemTags.LEG_ARMOR).add(ModItems.ZIRCON_LEGGINGS.key());
        tag(ItemTags.FOOT_ARMOR).add(ModItems.ZIRCON_BOOTS.key());

        tag(ItemTags.BOW_ENCHANTABLE).add(ModItems.KAUPEN_BOW.key());
        tag(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(ModItems.RADISH_SEEDS.key());

        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ModItems.BAR_BRAWL_MUSIC_DISC.key());

        tag(ItemTags.PLANKS)
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.EBONY_PLANKS.asItem()).get());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.EBONY_LOG.asItem()).get())
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.EBONY_WOOD.asItem()).get())
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.STRIPPED_EBONY_LOG.asItem()).get())
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.STRIPPED_EBONY_WOOD.asItem()).get());

        tag(ModTags.Items.EBONY_LOGS)
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.EBONY_LOG.asItem()).get())
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.EBONY_WOOD.asItem()).get())
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.STRIPPED_EBONY_LOG.asItem()).get())
                .add(BuiltInRegistries.ITEM.getResourceKey(ModBlocks.STRIPPED_EBONY_WOOD.asItem()).get());


    }
}
