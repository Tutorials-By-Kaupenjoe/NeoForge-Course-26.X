package net.kaupenjoe.mccourse.recipe;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.recipe.custom.AtomicSeparatorRecipe;
import net.kaupenjoe.mccourse.recipe.custom.CrystallizerRecipe;
import net.kaupenjoe.mccourse.recipe.custom.GrowthChamberRecipe;
import net.kaupenjoe.mccourse.recipe.custom.PedestalRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MCCourse.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, MCCourse.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrystallizerRecipe>> CRYSTALLIZER_SERIALIZER =
            SERIALIZERS.register("crystallizing", () -> new RecipeSerializer<>(CrystallizerRecipe.CODEC, CrystallizerRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<CrystallizerRecipe>> CRYSTALLIZER_TYPE =
            TYPES.register("crystallizing", () -> new RecipeType<CrystallizerRecipe>() {
                @Override
                public String toString() {
                    return "crystallizing";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PedestalRecipe>> PEDESTAL_SERIALIZER =
            SERIALIZERS.register("pedestal_crafting", () -> new RecipeSerializer<>(PedestalRecipe.CODEC, PedestalRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<PedestalRecipe>> PEDESTAL_TYPE =
            TYPES.register("pedestal_crafting", () -> new RecipeType<PedestalRecipe>() {
                @Override
                public String toString() {
                    return "pedestal_crafting";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GrowthChamberRecipe>> GROWTH_CHAMBER_SERIALIZER =
            SERIALIZERS.register("growing", () -> new RecipeSerializer<>(GrowthChamberRecipe.CODEC, GrowthChamberRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<GrowthChamberRecipe>> GROWTH_CHAMBER_TYPE =
            TYPES.register("growing", () -> new RecipeType<GrowthChamberRecipe>() {
                @Override
                public String toString() {
                    return "growing";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AtomicSeparatorRecipe>> ATOMIC_SEPARATOR_SERIALIZER =
            SERIALIZERS.register("atomic_separation", () -> new RecipeSerializer<>(AtomicSeparatorRecipe.CODEC, AtomicSeparatorRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AtomicSeparatorRecipe>> ATOMIC_SEPARATOR_TYPE =
            TYPES.register("atomic_separation", () -> new RecipeType<AtomicSeparatorRecipe>() {
                @Override
                public String toString() {
                    return "atomic_separation";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
