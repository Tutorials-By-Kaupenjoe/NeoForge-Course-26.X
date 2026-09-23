package net.kaupenjoe.mccourse;

import com.mojang.logging.LogUtils;
import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.component.ModDataComponentTypes;
import net.kaupenjoe.mccourse.consumeffect.ModConsumeEffects;
import net.kaupenjoe.mccourse.creativetab.ModCreativeModeTabs;
import net.kaupenjoe.mccourse.effect.ModEffects;
import net.kaupenjoe.mccourse.enchantment.ModEnchantmentEffects;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.fluid.ModFluidTypes;
import net.kaupenjoe.mccourse.fluid.ModFluids;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.loot.ModLootModifiers;
import net.kaupenjoe.mccourse.menu.ModMenuTypes;
import net.kaupenjoe.mccourse.particle.ModParticles;
import net.kaupenjoe.mccourse.potion.ModPotions;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.kaupenjoe.mccourse.stat.ModStats;
import net.kaupenjoe.mccourse.villager.ModVillagers;
import net.kaupenjoe.mccourse.worldgen.biome.ModBiomes;
import net.kaupenjoe.mccourse.worldgen.biome.ModMaterialRules;
import net.kaupenjoe.mccourse.worldgen.tree.ModFoliagePlacers;
import net.kaupenjoe.mccourse.worldgen.tree.ModTrunkPlacerTypes;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import terrablender.api.MaterialRuleManager;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
// Very important comment!
@Mod(MCCourse.MOD_ID)
public class MCCourse {
    public static final String MOD_ID = "mccourse";
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public MCCourse(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModDataComponentTypes.register(modEventBus);
        ModAttachmentTypes.register(modEventBus);

        ModConsumeEffects.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        ModSounds.register(modEventBus);
        ModEffects.register(modEventBus);

        ModPotions.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModParticles.register(modEventBus);
        ModEnchantmentEffects.register(modEventBus);

        ModStats.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        ModFluids.register(modEventBus);
        ModTrunkPlacerTypes.register(modEventBus);

        ModFoliagePlacers.register(modEventBus);
        ModEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModRecipes.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, MCConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);
            // ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.EBONY_SAPLING.getId(), ModBlocks.POTTED_EBONY_SAPLING);

            Stats.CUSTOM.get(ModStats.MANA_USED_TOTAL_STAT.get(), value -> value + " Mana");

            ModBiomes.registerBiomes();

            MaterialRuleManager.addRules(MaterialRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModMaterialRules::makeKaupenValleyRules);
            MaterialRuleManager.addRules(MaterialRuleManager.RuleCategory.NETHER, MOD_ID, ModMaterialRules::makeGlowstonePlainsRules);
            MaterialRuleManager.addRules(MaterialRuleManager.RuleCategory.END, MOD_ID, ModMaterialRules::makeEndRotRules);
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ZIRCON);
            event.accept(ModItems.RAW_ZIRCON);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}