package net.kaupenjoe.mccourse;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.renderer.PedestalBlockEntityRenderer;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.entity.client.*;
import net.kaupenjoe.mccourse.fluid.ModFluidTypes;
import net.kaupenjoe.mccourse.fluid.ModFluids;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.keymapping.ModKeyMappings;
import net.kaupenjoe.mccourse.menu.ModMenuTypes;
import net.kaupenjoe.mccourse.menu.custom.WarturtleScreen;
import net.kaupenjoe.mccourse.networking.packet.TestPacketC2S;
import net.kaupenjoe.mccourse.particle.ModParticles;
import net.kaupenjoe.mccourse.particle.ZirconParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.List;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = MCCourse.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = MCCourse.MOD_ID, value = Dist.CLIENT)
public class MCCourseClient {
    public MCCourseClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        ModKeyMappings.register();
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.PENGUIN.get(), PenguinRenderer::new);
        EntityRenderers.register(ModEntities.CHAIR.get(), ChairRenderer::new);
        EntityRenderers.register(ModEntities.WARTURTLE.get(), WarturtleRenderer::new);
        EntityRenderers.register(ModEntities.DODO.get(), DodoRenderer::new);

        EntityRenderers.register(ModEntities.EBONY_BOAT.get(), context -> new BoatRenderer(context, ModModelLayerLocations.EBONY_BOAT));
        EntityRenderers.register(ModEntities.EBONY_CHEST_BOAT.get(), context -> new BoatRenderer(context, ModModelLayerLocations.EBONY_CHEST_BOAT));

        EntityRenderers.register(ModEntities.TOMAHAWK.get(), TomahawkRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayerLocations.PENGUIN, PenguinModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.WARTURTLE, WarturtleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.WARTURTLE_ARMOR, WarturtleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.DODO, DodoModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayerLocations.EBONY_BOAT, BoatModel::createBoatModel);
        event.registerLayerDefinition(ModModelLayerLocations.EBONY_CHEST_BOAT, BoatModel::createChestBoatModel);

        event.registerLayerDefinition(ModModelLayerLocations.TOMAHAWK, TomahawkModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
        if (event.getPlayer().isUsingItem() && event.getPlayer().getUseItem().getItem() == ModItems.KAUPEN_BOW.get()) {
            float fovModifier = 1f;
            int ticksUsingItem = event.getPlayer().getTicksUsingItem();
            float scale = Math.min(ticksUsingItem / 20.0F, 1.0F);
            fovModifier *= 1.0F - Mth.square(scale) * 0.15F;
            event.setNewFovModifier(Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get().floatValue(), 1.0F, fovModifier));
        }
    }

    @SubscribeEvent
    public static void registerKeymapping(RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.PRESS_KAUPEN_KEY.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (ModKeyMappings.PRESS_KAUPEN_KEY.get().consumeClick()) {
            // IN HERE: WE ARE ON THE CLIENT!
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("I have " + Minecraft.getInstance().player.getData(ModAttachmentTypes.MANA) + " Mana"));
            ClientPacketDistributor.sendToServer(new TestPacketC2S("Kaupenjoe", 67));
        }
    }

    @SubscribeEvent
    public static void registerHUD(RegisterGuiLayersEvent event) {
        event.registerAboveAll(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "mana_bar"), (guiGraphics, deltaTracker) -> {
            int x = guiGraphics.guiWidth() / 2;
            int y = guiGraphics.guiHeight();

            if (!Minecraft.getInstance().player.isCreative() && !Minecraft.getInstance().player.isSpectator()
                    && Minecraft.getInstance().player.hasData(ModAttachmentTypes.MANA)) {
                for (int i = 0; i < 5; i++) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "mana_icon_bg"),
                            16, 16, 0, 0, x - 95 + i * 18, y - 55, 16, 16);
                }

                for (int i = 0; i < Minecraft.getInstance().player.getData(ModAttachmentTypes.MANA); i++) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "mana_icon"),
                            16, 16, 0, 0, x - 95 + i * 18, y - 55, 16, 16);
                }
            }
        });
    }

    @SubscribeEvent
    public static void registerColoredBlocks(RegisterColorHandlersEvent.BlockTintSources event) {
        event.register(List.of(BlockTintSources.foliage()), ModBlocks.COLORED_LEAVES.get());
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.ZIRCON_PARTICLES.get(), ZirconParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerOnClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(ModFluidTypes.ZIRCON_WATER_EXTENSION, ModFluidTypes.ZIRCON_WATER_FLUID_TYPE.get());
    }

    @SubscribeEvent
    public static void registerFluidModelsEvent(RegisterFluidModelsEvent event) {
        FluidModel.Unbaked zirconWaterModel = new FluidModel.Unbaked(
                new Material(Identifier.withDefaultNamespace("block/water_still")),
                new Material(Identifier.withDefaultNamespace("block/water_flow")),
                new Material(Identifier.withDefaultNamespace("block/water_overlay")),
                state -> 0xA1eb1734);

        event.register(zirconWaterModel, ModFluids.ZIRCON_WATER_SOURCE.get());
        event.register(zirconWaterModel, ModFluids.ZIRCON_WATER_FLOWING.get());
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.WARTURTLE_MENU.get(), WarturtleScreen::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.MAIN_PEDESTAL_BE.get(), PedestalBlockEntityRenderer::new);
    }
}
