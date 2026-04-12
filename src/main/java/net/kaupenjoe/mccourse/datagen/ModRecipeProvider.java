package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        List<ItemLike> ZIRCON_SMELTABLES = List.of(ModItems.RAW_ZIRCON,
                ModBlocks.ZIRCON_ORE, ModBlocks.ZIRCON_DEEPSLATE_ORE, ModBlocks.ZIRCON_NETHER_ORE, ModBlocks.ZIRCON_END_ORE);

        oreSmelting(ZIRCON_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.BLOCKS, ModItems.ZIRCON.get(), 0.25f, 200, "zircon");
        oreBlasting(ZIRCON_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.BLOCKS, ModItems.ZIRCON.get(), 0.25f, 100, "zircon");

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZIRCON_BLOCK.get())
                .pattern("ZZZ")
                .pattern("ZZZ")
                .pattern("ZZZ")
                .define('Z', ModItems.ZIRCON.get())
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, ModItems.ZIRCON.get(), 9)
                .requires(ModBlocks.ZIRCON_BLOCK)
                .unlockedBy(getHasName(ModBlocks.ZIRCON_BLOCK.get()), has(ModBlocks.ZIRCON_BLOCK.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, ModItems.ZIRCON.get(), 18)
                .requires(ModBlocks.ZIRCON_ORE)
                .requires(ModBlocks.RAW_ZIRCON_BLOCK)
                .unlockedBy(getHasName(ModBlocks.ZIRCON_ORE.get()), has(ModBlocks.ZIRCON_ORE.get()))
                .unlockedBy(getHasName(ModBlocks.RAW_ZIRCON_BLOCK.get()), has(ModBlocks.RAW_ZIRCON_BLOCK.get()))
                .save(output, MCCourse.MOD_ID + ":" + "zircon_from_ore_and_raw_block");

        stairBuilder(ModBlocks.ZIRCON_STAIRS.get(), Ingredient.of(ModBlocks.ZIRCON_BLOCK.get()))
                .group("zircon")
                .unlockedBy(getHasName(ModBlocks.ZIRCON_BLOCK.get()), has(ModBlocks.ZIRCON_BLOCK.get()))
                .save(output);
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZIRCON_SLAB.get(), ModItems.ZIRCON.get());

        buttonBuilder(ModBlocks.ZIRCON_BUTTON.get(), Ingredient.of(ModItems.ZIRCON))
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        pressurePlate(ModBlocks.ZIRCON_PRESSURE_PLATE.get(), ModItems.ZIRCON.get());

        fenceBuilder(ModBlocks.ZIRCON_FENCE.get(), Ingredient.of(ModItems.ZIRCON.get()))
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        fenceGateBuilder(ModBlocks.ZIRCON_FENCE_GATE.get(), Ingredient.of(ModItems.ZIRCON.get()))
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZIRCON_WALL.get(), ModItems.ZIRCON.get());

        doorBuilder(ModBlocks.ZIRCON_DOOR.get(), Ingredient.of(ModItems.ZIRCON.get()))
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        trapdoorBuilder(ModBlocks.ZIRCON_TRAPDOOR.get(), Ingredient.of(ModBlocks.ZIRCON_BLOCK.get()))
                .group("zircon")
                .unlockedBy(getHasName(ModBlocks.ZIRCON_BLOCK.get()), has(ModBlocks.ZIRCON_BLOCK.get()))
                .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.ZIRCON_SWORD.get())
                .pattern("Z")
                .pattern("Z")
                .pattern("S")
                .define('Z', ModItems.ZIRCON.get())
                .define('S', Items.STICK)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_PICKAXE.get())
                .pattern("ZZZ")
                .pattern(" S ")
                .pattern(" S ")
                .define('Z', ModItems.ZIRCON.get())
                .define('S', Items.STICK)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_SHOVEL.get())
                .pattern("Z")
                .pattern("S")
                .pattern("S")
                .define('Z', ModItems.ZIRCON.get())
                .define('S', Items.STICK)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_AXE.get())
                .pattern("ZZ")
                .pattern("ZS")
                .pattern(" S")
                .define('Z', ModItems.ZIRCON.get())
                .define('S', Items.STICK)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_HOE.get())
                .pattern("ZZ")
                .pattern(" S")
                .pattern(" S")
                .define('Z', ModItems.ZIRCON.get())
                .define('S', Items.STICK)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_PAXEL.get())
                .pattern("PAS")
                .define('P', ModItems.ZIRCON_PICKAXE.get())
                .define('A', ModItems.ZIRCON_AXE.get())
                .define('S', ModItems.ZIRCON_SHOVEL.get())
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON_PICKAXE.get()), has(ModItems.ZIRCON_PICKAXE.get()))
                .unlockedBy(getHasName(ModItems.ZIRCON_AXE.get()), has(ModItems.ZIRCON_AXE.get()))
                .unlockedBy(getHasName(ModItems.ZIRCON_SHOVEL.get()), has(ModItems.ZIRCON_SHOVEL.get()))
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_HAMMER.get())
                .pattern("ZZZ")
                .pattern("ZSZ")
                .pattern(" S ")
                .define('Z', ModItems.ZIRCON.get())
                .define('S', Items.STICK)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_HELMET.get())
                .pattern("ZZZ")
                .pattern("Z Z")
                .define('Z', ModItems.ZIRCON.get())
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_CHESTPLATE.get())
                .pattern(" Z ")
                .pattern("ZZZ")
                .pattern("ZZZ")
                .define('Z', ModItems.ZIRCON.get())
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_LEGGINGS.get())
                .pattern("ZZZ")
                .pattern("Z Z")
                .pattern("Z Z")
                .define('Z', ModItems.ZIRCON.get())
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);
        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_BOOTS.get())
                .pattern("Z Z")
                .pattern("Z Z")
                .define('Z', ModItems.ZIRCON.get())
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.ZIRCON_HORSE_ARMOR.get())
                .pattern("ZLZ")
                .pattern("Z Z")
                .define('Z', ModItems.ZIRCON.get())
                .define('L', Items.LEATHER)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .save(output);

        shaped(RecipeCategory.DECORATIONS, ModBlocks.ZIRCON_LAMP.get())
                .pattern("ZZZ")
                .pattern("ZRZ")
                .pattern("ZZZ")
                .define('Z', ModItems.ZIRCON.get())
                .define('R', Items.REDSTONE)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.KAUPEN_BOW.get())
                .pattern("S# ")
                .pattern("S Z")
                .pattern("S# ")
                .define('Z', ModItems.ZIRCON.get())
                .define('#', Items.STICK)
                .define('S', Items.STRING)
                .group("zircon")
                .unlockedBy(getHasName(ModItems.ZIRCON.get()), has(ModItems.ZIRCON.get()))
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(output);

        shaped(RecipeCategory.DECORATIONS, ModBlocks.CHAIR.get())
                .pattern("PPP")
                .pattern("# #")
                .pattern("# #")
                .define('#', Items.STICK)
                .define('P', Items.OAK_PLANKS)
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .unlockedBy(getHasName(Items.OAK_PLANKS), has(Items.OAK_PLANKS))
                .save(output);


    }

    @Override
    protected <T extends AbstractCookingRecipe> void oreCooking(AbstractCookingRecipe.Factory<T> factory, List<ItemLike> smeltables,
                                                                RecipeCategory craftingCategory, CookingBookCategory cookingCategory,
                                                                ItemLike result, float experience, int cookingTime, String group, String fromDesc) {
        for (ItemLike item : smeltables) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(item), craftingCategory, cookingCategory, result, experience, cookingTime, factory)
                    .group(group)
                    .unlockedBy(getHasName(item), this.has(item))
                    .save(this.output, MCCourse.MOD_ID + ":" + getItemName(result) + fromDesc + "_" + getItemName(item));
        }
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "MCCourse Recipes";
        }
    }
}
