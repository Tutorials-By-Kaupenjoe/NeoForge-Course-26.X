package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.loot.AddItemStackModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, MCCourse.MOD_ID);
    }

    @Override
    protected void start() {
        add("radish_to_short_grass",
                new AddItemStackModifier(new LootItemCondition[]{
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                        LootItemRandomChanceCondition.randomChance(0.25f).build() }, new ItemStackTemplate(ModItems.RADISH.get(), 2)));
        add("radish_to_tall_grass",
                new AddItemStackModifier(new LootItemCondition[]{
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                        LootItemRandomChanceCondition.randomChance(0.25f).build() }, new ItemStackTemplate(ModItems.RADISH.get(), 2)));

        add("chisel_from_jungle_temple",
                new AddItemStackModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/jungle_temple")).build() },
                        new ItemStackTemplate(ModItems.CHISEL.get())));

        add("frostfire_ice_to_creeper",
                new AddItemStackModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("entities/creeper")).build() },
                        new ItemStackTemplate(ModItems.FROSTFIRE_ICE.get())));
    }
}
