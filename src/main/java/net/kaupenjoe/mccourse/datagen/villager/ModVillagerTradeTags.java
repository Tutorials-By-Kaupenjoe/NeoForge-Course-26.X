package net.kaupenjoe.mccourse.datagen.villager;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.VillagerTradeTags;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTags extends VillagerTradesTagsProvider {
    public ModVillagerTradeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(VillagerTradeTags.FARMER_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.FARMER_1_EMERALD_RADISH.identifier()))
                .add(TagEntry.element(ModVillagerTrades.FARMER_1_DIAMOND_RADISH_SEEDS.identifier()));

        getOrCreateRawBuilder(VillagerTradeTags.FARMER_LEVEL_2)
                .add(TagEntry.element(ModVillagerTrades.FARMER_2_EMERALD_RICE_SHOOT.identifier()));

        getOrCreateRawBuilder(VillagerTradeTags.MASON_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.MASON_1_ZIRCON_CHISEL.identifier()));

        getOrCreateRawBuilder(VillagerTradeTags.LIBRARIAN_LEVEL_1)
                .add(TagEntry.element(ModVillagerTrades.LIBRARIAN_1_ZIRCON_ENCHANTED_ZIRCON_SWORD.identifier()));
    }
}
