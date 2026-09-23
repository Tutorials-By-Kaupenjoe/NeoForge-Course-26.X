package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.effect.ModEffects;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.triggers.EffectsChangedTrigger;
import net.minecraft.advancements.triggers.ItemUsedOnLocationTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.minecraft.advancements.triggers.InventoryChangeTrigger.TriggerInstance.hasItems;
import static net.minecraft.advancements.triggers.ItemUsedOnLocationTrigger.TriggerInstance.placedBlock;

public class MCCourseAdvancements extends AdvancementSubProvider {
    private final HolderGetter<Item> items;
    private final HolderGetter<Block> blocks;

    public MCCourseAdvancements(BootstrapContext<Advancement> output) {
        super(output);
        this.items = output.lookup(Registries.ITEM);
        this.blocks = output.lookup(Registries.BLOCK);
    }

    public static AdvancementSubProvider create(BootstrapContext<Advancement> context) {
        return new MCCourseAdvancements(context);
    }

    @Override
    public void generate() {
        AdvancementHolder root = Advancement.Builder.advancement()
                .rootDisplay(ModItems.ZIRCON.get(),
                        Component.translatable("advancements.mccourse.root.title"),
                        Component.translatable("advancements.mccourse.root.description"),
                        Identifier.withDefaultNamespace("gui/advancements/backgrounds/adventure"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false)
                .addCriterion("has_zircon", hasItems(ItemPredicate.Builder.item().of(items, ModItems.ZIRCON.asItem())))
                .save(output, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "mccourse/root"));

        AdvancementHolder plantSeed = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.RICE_SHOOT.get(),
                        Component.translatable("advancements.mccourse.plant_custom.title"),
                        Component.translatable("advancements.mccourse.plant_custom.description"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("berries", placedBlock(blocks, ModBlocks.GOJI_BERRY_BUSH.get()))
                .addCriterion("rice", placedBlock(blocks, ModBlocks.RICE_CROP.get()))
                .addCriterion("radish", placedBlock(blocks, ModBlocks.RADISH_CROP.get()))
                .save(output, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "mccourse/plant_custom"));

        AdvancementHolder useChisel = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.CHISEL.get(),
                        Component.translatable("advancements.mccourse.chisel_stone.title"),
                        Component.translatable("advancements.mccourse.chisel_stone.description"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("chisel_stone", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location()
                        .setBlock(BlockPredicate.Builder.block().of(blocks, Blocks.STONE)), ItemPredicate.Builder.item().of(items, ModItems.CHISEL.asItem())))
                .save(output, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "mccourse/chisel_stone"));

        AdvancementHolder stinkyAdv = Advancement.Builder.advancement()
                .parent(useChisel)
                .display(Items.DIRT,
                        Component.translatable("advancements.mccourse.be_stinky.title"),
                        Component.translatable("advancements.mccourse.be_stinky.description"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        true
                )
                .addCriterion("be_stinky", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(ModEffects.STINKY_EFFECT)))
                .save(output, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "mccourse/stinky"));


    }
}
