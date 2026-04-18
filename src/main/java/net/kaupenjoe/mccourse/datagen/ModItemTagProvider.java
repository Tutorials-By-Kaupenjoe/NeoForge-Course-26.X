package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, MCCourse.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.ZIRCON.get())
                .add(Items.RAW_IRON)
                .add(Items.COPPER_BLOCK);

        tag(ModTags.Items.ZIRCON_REPAIRABLES)
                .add(ModItems.ZIRCON.get());

        tag(ItemTags.SWORDS).add(ModItems.ZIRCON_SWORD.get());
        tag(ItemTags.PICKAXES).add(ModItems.ZIRCON_PICKAXE.get()).add(ModItems.ZIRCON_PAXEL.get()).add(ModItems.ZIRCON_HAMMER.get());
        tag(ItemTags.SHOVELS).add(ModItems.ZIRCON_SHOVEL.get()).add(ModItems.ZIRCON_PAXEL.get());
        tag(ItemTags.AXES).add(ModItems.ZIRCON_AXE.get()).add(ModItems.ZIRCON_PAXEL.get());
        tag(ItemTags.HOES).add(ModItems.ZIRCON_HOE.get());

        tag(ItemTags.HEAD_ARMOR).add(ModItems.ZIRCON_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(ModItems.ZIRCON_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR).add(ModItems.ZIRCON_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(ModItems.ZIRCON_BOOTS.get());

        tag(ItemTags.BOW_ENCHANTABLE).add(ModItems.KAUPEN_BOW.get());
        tag(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(ModItems.RADISH_SEEDS.get());

        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(ModItems.BAR_BRAWL_MUSIC_DISC.get());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.EBONY_PLANKS.asItem());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.EBONY_LOG.asItem())
                .add(ModBlocks.EBONY_WOOD.asItem())
                .add(ModBlocks.STRIPPED_EBONY_LOG.asItem())
                .add(ModBlocks.STRIPPED_EBONY_WOOD.asItem());

        tag(ModTags.Items.EBONY_LOGS)
                .add(ModBlocks.EBONY_LOG.asItem())
                .add(ModBlocks.EBONY_WOOD.asItem())
                .add(ModBlocks.STRIPPED_EBONY_LOG.asItem())
                .add(ModBlocks.STRIPPED_EBONY_WOOD.asItem());


    }
}
