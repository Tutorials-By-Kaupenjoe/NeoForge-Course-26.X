package net.kaupenjoe.mccourse.tag;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_ZIRCON_TOOL = createTag("needs_zircon_tool");
        public static final TagKey<Block> INCORRECT_FOR_ZIRCON_TOOL = createTag("incorrect_for_zircon_tool");

        public static final TagKey<Block> PAXEL_MINEABLE = createTag("mineable/paxel");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");
        public static final TagKey<Item> ZIRCON_REPAIRABLES = createTag("zircon_repairables");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
        }
    }

    public static class Trades {
        public static final TagKey<VillagerTrade> KAUPENGER_LEVEL_1 = createTag("kaupenger/level_1");
        public static final TagKey<VillagerTrade> KAUPENGER_LEVEL_2 = createTag("kaupenger/level_2");

        private static TagKey<VillagerTrade> createTag(String name) {
            return TagKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, name));
        }
    }
}
