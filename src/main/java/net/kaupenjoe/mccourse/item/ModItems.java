package net.kaupenjoe.mccourse.item;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.datagen.ModJukeboxSongs;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.fluid.ModFluids;
import net.kaupenjoe.mccourse.food.ModFoodProperties;
import net.kaupenjoe.mccourse.item.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MCCourse.MOD_ID);

    public static final DeferredItem<Item> ZIRCON = ITEMS.registerSimpleItem("zircon",
            properties -> properties);
    public static final DeferredItem<Item> RAW_ZIRCON = ITEMS.registerSimpleItem("raw_zircon",
            properties -> properties);

    public static final DeferredItem<Item> CHISEL = ITEMS.registerItem("chisel",
            properties -> new ChiselItem(properties.durability(32)));

    public static final DeferredItem<Item> RADISH = ITEMS.registerItem("radish",
            properties -> new Item(properties.food(ModFoodProperties.RADISH, ModFoodProperties.RADISH_EFFECT)) {
                @Override
                public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                    builder.accept(Component.translatable("tooltip.mccourse.radish"));
                    super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> FROSTFIRE_ICE = ITEMS.registerItem("frostfire_ice", Item::new);

    public static final DeferredItem<Item> ZIRCON_SWORD = ITEMS.registerItem("zircon_sword",
            properties -> new Item(properties.sword(ModToolMaterials.ZIRCON, 3f, -2.4f)));
    public static final DeferredItem<Item> ZIRCON_PICKAXE = ITEMS.registerItem("zircon_pickaxe",
            properties -> new Item(properties.pickaxe(ModToolMaterials.ZIRCON, 1f, -2.8f)));
    public static final DeferredItem<Item> ZIRCON_SHOVEL = ITEMS.registerItem("zircon_shovel",
            properties -> new ShovelItem(ModToolMaterials.ZIRCON, 1.5f, -3f, properties));
    public static final DeferredItem<Item> ZIRCON_AXE = ITEMS.registerItem("zircon_axe",
            properties -> new AxeItem(ModToolMaterials.ZIRCON, 6f, -3.2f, properties));
    public static final DeferredItem<Item> ZIRCON_HOE = ITEMS.registerItem("zircon_hoe",
            properties -> new HoeItem(ModToolMaterials.ZIRCON, 0f, -3f, properties));

    public static final DeferredItem<Item> ZIRCON_PAXEL = ITEMS.registerItem("zircon_paxel",
            properties -> new PaxelItem(ModToolMaterials.ZIRCON, 4f, -2.6f, properties));
    public static final DeferredItem<Item> ZIRCON_HAMMER = ITEMS.registerItem("zircon_hammer",
            properties -> new HammerItem(properties.pickaxe(ModToolMaterials.ZIRCON, 7f, -3.4f)));


    public static final DeferredItem<Item> ZIRCON_HELMET = ITEMS.registerItem("zircon_helmet",
            properties -> new ModArmorItem(properties.humanoidArmor(ModArmorMaterials.ZIRCON_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> ZIRCON_CHESTPLATE = ITEMS.registerItem("zircon_chestplate",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.ZIRCON_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> ZIRCON_LEGGINGS = ITEMS.registerItem("zircon_leggings",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.ZIRCON_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> ZIRCON_BOOTS = ITEMS.registerItem("zircon_boots",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.ZIRCON_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> ZIRCON_HORSE_ARMOR = ITEMS.registerItem("zircon_horse_armor",
            properties -> new Item(properties.horseArmor(ModArmorMaterials.ZIRCON_ARMOR_MATERIAL)));
    public static final DeferredItem<Item> KAUPEN_BOW = ITEMS.registerItem("kaupen_bow",
            properties -> new BowItem(properties.durability(500)));

    public static final DeferredItem<Item> RADISH_SEEDS = ITEMS.registerItem("radish_seeds",
            properties -> new BlockItem(ModBlocks.RADISH_CROP.get(), properties.useItemDescriptionPrefix()));
    public static final DeferredItem<Item> GOJI_BERRIES = ITEMS.registerItem("goji_berries",
            properties -> new BlockItem(ModBlocks.GOJI_BERRY_BUSH.get(), properties.useItemDescriptionPrefix().food(ModFoodProperties.GOJI_BERRIES)));
    public static final DeferredItem<Item> RICE_SHOOT = ITEMS.registerItem("rice_shoot",
            properties -> new PlaceOnWaterBlockItem(ModBlocks.RICE_CROP.get(), properties.useItemDescriptionPrefix()));

    public static final DeferredItem<Item> BAR_BRAWL_MUSIC_DISC = ITEMS.registerItem("bar_brawl_music_disc",
            properties -> new Item(properties.jukeboxPlayable(ModJukeboxSongs.BAR_BRAWL_KEY).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> RADIATION_STAFF = ITEMS.registerItem("radiation_staff",
            properties -> new Item(properties.stacksTo(1).rarity(Rarity.RARE)));

    public static final DeferredItem<Item> ZIRCON_WATER_BUCKET = ITEMS.registerItem("zircon_water_bucket",
            properties -> new BucketItem(ModFluids.ZIRCON_WATER_SOURCE.get(), properties.stacksTo(1).craftRemainder(Items.BUCKET)));

    public static final DeferredItem<Item> PENGUIN_SPAWN_EGG = ITEMS.registerItem("penguin_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.PENGUIN.get())));
    public static final DeferredItem<Item> WARTURTLE_SPAWN_EGG = ITEMS.registerItem("warturtle_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.WARTURTLE.get())));
    public static final DeferredItem<Item> DODO_SPAWN_EGG = ITEMS.registerItem("dodo_spawn_egg",
            properties -> new SpawnEggItem(properties.spawnEgg(ModEntities.DODO.get())));

    public static final DeferredItem<Item> IRON_WARTURTLE_ARMOR = ITEMS.registerItem("iron_warturtle_armor",
            properties -> new WarturtleArmorItem(properties.durability(200)));
    public static final DeferredItem<Item> GOLD_WARTURTLE_ARMOR = ITEMS.registerItem("gold_warturtle_armor",
            properties -> new WarturtleArmorItem(properties.durability(400)));
    public static final DeferredItem<Item> DIAMOND_WARTURTLE_ARMOR = ITEMS.registerItem("diamond_warturtle_armor",
            properties -> new WarturtleArmorItem(properties.durability(600)));
    public static final DeferredItem<Item> NETHERITE_WARTURTLE_ARMOR = ITEMS.registerItem("netherite_warturtle_armor",
            properties -> new WarturtleArmorItem(properties.durability(800)));
    public static final DeferredItem<Item> ZIRCON_WARTURTLE_ARMOR = ITEMS.registerItem("zircon_warturtle_armor",
            properties -> new WarturtleArmorItem(properties.durability(1000)));

    public static final DeferredItem<Item> EBONY_BOAT = ITEMS.registerItem("ebony_boat",
            properties -> new BoatItem(ModEntities.EBONY_BOAT.get(), properties.stacksTo(1)));
    public static final DeferredItem<Item> EBONY_CHEST_BOAT = ITEMS.registerItem("ebony_chest_boat",
            properties -> new BoatItem(ModEntities.EBONY_CHEST_BOAT.get(), properties.stacksTo(1)));

    public static final DeferredItem<Item> TOMAHAWK = ITEMS.registerItem("tomahawk",
            properties -> new TomahawkItem(properties.stacksTo(16)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
