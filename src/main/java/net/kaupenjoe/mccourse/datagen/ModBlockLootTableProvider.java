package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.custom.RadishCropBlock;
import net.kaupenjoe.mccourse.block.custom.RiceCropBlock;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(LootTableSubProvider.Context output) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
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

        dropSelf(ModBlocks.ZIRCON_LAMP.get());

        add(ModBlocks.RADISH_CROP.get(), createCropDrops(ModBlocks.RADISH_CROP.get(), ModItems.RADISH.get(),
                ModItems.RADISH_SEEDS.get(), MatchBlock.blockMatches(blocks, ModBlocks.RADISH_CROP.get(),
                        StatePropertiesPredicate.Builder.properties().hasProperty(RadishCropBlock.AGE, 3))));

        dropSelf(ModBlocks.CATMINT.get());
        add(ModBlocks.POTTED_CATMINT.get(), createPotFlowerItemTable(ModBlocks.CATMINT.get()));

        dropSelf(ModBlocks.COLORED_LEAVES.get());

        this.add(ModBlocks.GOJI_BERRY_BUSH.get(), block -> this.applyExplosionDecay(
                block, LootTable.lootTable().withPool(LootPool.lootPool().when(MatchBlock.blockMatches(blocks, ModBlocks.GOJI_BERRY_BUSH.get(),
                                StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3)))
                        .add(LootItem.lootTableItem(ModItems.GOJI_BERRIES))
                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                ).withPool(LootPool.lootPool().when(MatchBlock.blockMatches(blocks, ModBlocks.GOJI_BERRY_BUSH.get(),
                                StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2)))
                        .add(LootItem.lootTableItem(ModItems.GOJI_BERRIES))
                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                )));

        add(ModBlocks.RICE_CROP.get(), createCropDrops(ModBlocks.RICE_CROP.get(), ModItems.RICE_SHOOT.get(),
                ModItems.RICE_SHOOT.get(), MatchBlock.blockMatches(blocks, ModBlocks.RICE_CROP.get(),
                        StatePropertiesPredicate.Builder.properties().hasProperty(RiceCropBlock.AGE, 7))));

        dropSelf(ModBlocks.CHAIR.get());

        dropSelf(ModBlocks.EBONY_LOG.get());
        dropSelf(ModBlocks.EBONY_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_EBONY_LOG.get());
        dropSelf(ModBlocks.STRIPPED_EBONY_WOOD.get());

        dropSelf(ModBlocks.EBONY_PLANKS.get());
        dropSelf(ModBlocks.EBONY_SAPLING.get());

        add(ModBlocks.POTTED_EBONY_SAPLING.get(), createPotFlowerItemTable(ModBlocks.EBONY_SAPLING.get()));
        add(ModBlocks.EBONY_LEAVES.get(), block -> createLeavesDrops(block, ModBlocks.EBONY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropSelf(ModBlocks.KAUPEN_PORTAL.get());
        dropSelf(ModBlocks.MAIN_PEDESTAL.get());
        dropSelf(ModBlocks.SIDE_PEDESTAL.get());
        dropSelf(ModBlocks.CRYSTALLIZER.get());
        dropSelf(ModBlocks.COAL_GENERATOR.get());
        dropSelf(ModBlocks.BATTERY.get());
        dropSelf(ModBlocks.GROWTH_CHAMBER.get());
        dropSelf(ModBlocks.ATOMIC_SEPARATOR.get());

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, int minDrops, int maxDrops) {
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
