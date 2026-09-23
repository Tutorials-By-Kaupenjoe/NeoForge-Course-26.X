package net.kaupenjoe.mccourse.datagen.villager;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;

import java.util.List;
import java.util.Optional;

public class ModVillagerTrades {
    public static final ResourceKey<VillagerTrade> FARMER_1_EMERALD_RADISH = createKey("farmer/1/emerald_radish");
    public static final ResourceKey<VillagerTrade> FARMER_1_DIAMOND_RADISH_SEEDS = createKey("farmer/1/diamond_radish_seeds");

    public static final ResourceKey<VillagerTrade> FARMER_2_EMERALD_RICE_SHOOT = createKey("farmer/2/emerald_rice_shoot");

    public static final ResourceKey<VillagerTrade> MASON_1_ZIRCON_CHISEL = createKey("mason/1/zircon_chisel");
    public static final ResourceKey<VillagerTrade> LIBRARIAN_1_ZIRCON_ENCHANTED_ZIRCON_SWORD = createKey("librarian/1/zircon_enchanted_zircon_sword");


    public static final ResourceKey<VillagerTrade> KAUPENGER_1_EMERALD_CHISEL = createKey("kaupenger/1/emerald_chisel");
    public static final ResourceKey<VillagerTrade> KAUPENGER_1_EMERALD_RAW_ZIRCON = createKey("kaupenger/1/emerald_raw_zircon");

    public static final ResourceKey<VillagerTrade> KAUPENGER_2_ZIRCON_MAGIC_BLOCK = createKey("kaupenger/2/zircon_magic_block");
    public static final ResourceKey<VillagerTrade> KAUPENGER_2_DIAMOND_RADIATION_STAFF = createKey("kaupenger/2/diamond_radiation_staff");


    public static void bootstrap(BootstrapContext<VillagerTrade> context) {
        var items = context.lookup(Registries.ITEM);
        var enchantments = context.lookup(Registries.ENCHANTMENT);

        register(context, FARMER_1_EMERALD_RADISH, VillagerTrade.builder(
                new TradeCost(Items.EMERALD, 4),
                new ItemStackTemplate(ModItems.RADISH, 2),
                12, 12, 0.05f).build());
        register(context, FARMER_1_DIAMOND_RADISH_SEEDS, VillagerTrade.builder(
                new TradeCost(Items.DIAMOND, 12),
                new ItemStackTemplate(ModItems.RADISH_SEEDS, 9),
                12, 12, 0.05f).build());

        register(context, FARMER_2_EMERALD_RICE_SHOOT, VillagerTrade.builder(
                new TradeCost(Items.EMERALD, 18),
                new ItemStackTemplate(ModItems.RICE_SHOOT, 2),
                12, 12, 0.05f).build());

        register(context, MASON_1_ZIRCON_CHISEL, VillagerTrade.builder(
                new TradeCost(ModItems.ZIRCON, 32),
                new ItemStackTemplate(ModItems.CHISEL, 1),
                12, 12, 0.05f).build());

        register(context, LIBRARIAN_1_ZIRCON_ENCHANTED_ZIRCON_SWORD, VillagerTrade.builder(
                new TradeCost(ModItems.ZIRCON, 64),
                new ItemStackTemplate(ModItems.ZIRCON_SWORD, 1),
                3, 15, 0.07f)
                .addModifiers(VillagerTrades.enchantedItem(items, enchantments.getOrThrow(Enchantments.SHARPNESS), 1, ModItems.ZIRCON_SWORD.asItem()))
                .build());

        register(context, KAUPENGER_1_EMERALD_CHISEL, VillagerTrade.builder(
                new TradeCost(Items.EMERALD, 12),
                new ItemStackTemplate(ModItems.CHISEL, 1),
                12, 12, 0.05f).build());
        register(context, KAUPENGER_1_EMERALD_RAW_ZIRCON, VillagerTrade.builder(
                new TradeCost(Items.EMERALD, 3),
                new ItemStackTemplate(ModItems.RAW_ZIRCON, 15),
                12, 12, 0.05f).build());

        register(context, KAUPENGER_2_DIAMOND_RADIATION_STAFF, VillagerTrade.builder(
                new TradeCost(Items.DIAMOND, 3),
                new ItemStackTemplate(ModItems.RADIATION_STAFF, 1),
                12, 12, 0.05f).build());
        register(context, KAUPENGER_2_ZIRCON_MAGIC_BLOCK, VillagerTrade.builder(
                new TradeCost(ModItems.ZIRCON, 3),
                new ItemStackTemplate(ModBlocks.MAGIC_BLOCK.asItem(), 1),
                12, 12, 0.05f).build());

    }

    private static ResourceKey<VillagerTrade> createKey(String id) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, id));
    }

    private static void register(BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> key, VillagerTrade trade) {
        context.register(key, trade);
    }
}
