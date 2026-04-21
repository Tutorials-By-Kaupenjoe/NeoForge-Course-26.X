package net.kaupenjoe.mccourse.entity;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.entity.custom.ChairEntity;
import net.kaupenjoe.mccourse.entity.custom.DodoEntity;
import net.kaupenjoe.mccourse.entity.custom.PenguinEntity;
import net.kaupenjoe.mccourse.entity.custom.WarturtleEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.createEntities(MCCourse.MOD_ID);

    public static final ResourceKey<EntityType<?>> PENGUIN_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "penguin"));
    public static final ResourceKey<EntityType<?>> CHAIR_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "chair"));
    public static final ResourceKey<EntityType<?>> WARTURTLE_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "warturtle"));
    public static final ResourceKey<EntityType<?>> DODO_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "dodo"));


    public static final Supplier<EntityType<PenguinEntity>> PENGUIN = ENTITY_TYPES.register("penguin",
            () -> EntityType.Builder.of(PenguinEntity::new, MobCategory.CREATURE).sized(0.75f, 1.25f).build(PENGUIN_KEY));
    public static final Supplier<EntityType<ChairEntity>> CHAIR = ENTITY_TYPES.register("chair",
            () -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).noLootTable()
                    .sized(0.5f, 0.5f).build(CHAIR_KEY));

    public static final Supplier<EntityType<WarturtleEntity>> WARTURTLE = ENTITY_TYPES.register("warturtle",
            () -> EntityType.Builder.of(WarturtleEntity::new, MobCategory.CREATURE).sized(2.5f, 1.5f).build(WARTURTLE_KEY));
    public static final Supplier<EntityType<DodoEntity>> DODO = ENTITY_TYPES.register("dodo",
            () -> EntityType.Builder.of(DodoEntity::new, MobCategory.CREATURE).sized(1f, 2.5f).build(DODO_KEY));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
