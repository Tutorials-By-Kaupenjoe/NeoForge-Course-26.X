package net.kaupenjoe.mccourse;

import com.mojang.logging.LogUtils;
import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.component.ModDataComponentTypes;
import net.kaupenjoe.mccourse.consumeffect.ModConsumeEffects;
import net.kaupenjoe.mccourse.creativetab.ModCreativeModeTabs;
import net.kaupenjoe.mccourse.effect.ModEffects;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.loot.ModLootModifiers;
import net.kaupenjoe.mccourse.potion.ModPotions;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
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


        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);

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