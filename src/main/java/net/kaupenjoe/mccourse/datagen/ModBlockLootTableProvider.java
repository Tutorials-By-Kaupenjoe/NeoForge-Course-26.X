package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ZIRCON_BLOCK.get());
        dropSelf(ModBlocks.RAW_ZIRCON_BLOCK.get());

        add(ModBlocks.ZIRCON_ORE.get(), block -> createOreDrop(block, ModItems.RAW_ZIRCON.get()));
        add(ModBlocks.ZIRCON_DEEPSLATE_ORE.get(), block -> createMultipleOreDrops(block, ModItems.RAW_ZIRCON.get(), 2, 5));
        add(ModBlocks.ZIRCON_NETHER_ORE.get(), block -> createMultipleOreDrops(block, ModItems.RAW_ZIRCON.get(), 4, 6));
        add(ModBlocks.ZIRCON_END_ORE.get(), block -> createMultipleOreDrops(block, ModItems.RAW_ZIRCON.get(), 5, 9));

        dropSelf(ModBlocks.ZIRCON_STAIRS.get());
        add(ModBlocks.ZIRCON_SLAB.get(), this::createSlabItemTable);

        dropSelf(ModBlocks.ZIRCON_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.ZIRCON_BUTTON.get());

        dropSelf(ModBlocks.ZIRCON_FENCE.get());
        dropSelf(ModBlocks.ZIRCON_FENCE_GATE.get());
        dropSelf(ModBlocks.ZIRCON_WALL.get());

        dropSelf(ModBlocks.ZIRCON_TRAPDOOR.get());
        add(ModBlocks.ZIRCON_DOOR.get(), this::createDoorTable);

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
