package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.custom.RadishCropBlock;
import net.kaupenjoe.mccourse.block.custom.RiceCropBlock;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

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
                ModItems.RADISH_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.RADISH_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RadishCropBlock.AGE, 3))));

        dropSelf(ModBlocks.CATMINT.get());
        add(ModBlocks.POTTED_CATMINT.get(), createPotFlowerItemTable(ModBlocks.CATMINT.get()));

        dropSelf(ModBlocks.COLORED_LEAVES.get());

        this.add(ModBlocks.GOJI_BERRY_BUSH.get(), block -> this.applyExplosionDecay(
                block, LootTable.lootTable().withPool(LootPool.lootPool().when(
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.GOJI_BERRY_BUSH.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3)))
                        .add(LootItem.lootTableItem(ModItems.GOJI_BERRIES))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                ).withPool(LootPool.lootPool().when(
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.GOJI_BERRY_BUSH.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2)))
                        .add(LootItem.lootTableItem(ModItems.GOJI_BERRIES))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
                )));

        add(ModBlocks.RICE_CROP.get(), createCropDrops(ModBlocks.RICE_CROP.get(), ModItems.RICE_SHOOT.get(),
                ModItems.RICE_SHOOT.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.RICE_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RiceCropBlock.AGE, 7))));

        dropSelf(ModBlocks.CHAIR.get());

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
