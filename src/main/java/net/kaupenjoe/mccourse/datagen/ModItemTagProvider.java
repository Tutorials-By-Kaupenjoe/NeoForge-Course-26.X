package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
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
        tag(ItemTags.PICKAXES).add(ModItems.ZIRCON_PICKAXE.get()).add(ModItems.ZIRCON_PAXEL.get());
        tag(ItemTags.SHOVELS).add(ModItems.ZIRCON_SHOVEL.get()).add(ModItems.ZIRCON_PAXEL.get());
        tag(ItemTags.AXES).add(ModItems.ZIRCON_AXE.get()).add(ModItems.ZIRCON_PAXEL.get());
        tag(ItemTags.HOES).add(ModItems.ZIRCON_HOE.get());

    }
}
