package net.kaupenjoe.mccourse.event;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.kaupenjoe.mccourse.attachmenttype.handler.ManaHandler;
import net.kaupenjoe.mccourse.command.ReturnHomeCommand;
import net.kaupenjoe.mccourse.command.SetHomeCommand;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.entity.custom.DodoEntity;
import net.kaupenjoe.mccourse.entity.custom.PenguinEntity;
import net.kaupenjoe.mccourse.entity.custom.WarturtleEntity;
import net.kaupenjoe.mccourse.networking.ClientboundPackets;
import net.kaupenjoe.mccourse.networking.ServerboundPackets;
import net.kaupenjoe.mccourse.networking.packet.ManaPacketS2C;
import net.kaupenjoe.mccourse.networking.packet.TestPacketC2S;
import net.kaupenjoe.mccourse.potion.ModPotions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = MCCourse.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(TestPacketC2S.TYPE, TestPacketC2S.STREAM_CODEC, ServerboundPackets::handleTestPacket);
        registrar.playToClient(ManaPacketS2C.TYPE, ManaPacketS2C.STREAM_CODEC, ClientboundPackets::handleManaPacket);
    }

    @SubscribeEvent
    public static void setPlayersManaOnSpawn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if(player.hasData(ModAttachmentTypes.MANA)) {
            ManaHandler.setMana(((ServerPlayer) player), player.getData(ModAttachmentTypes.MANA));
        } else {
            ManaHandler.setMana(((ServerPlayer) player), 5);
        }
    }

    @SubscribeEvent
    public static void setPlayersManaOnClone(PlayerEvent.Clone event) {
        Player newPlayer = event.getEntity();
        ManaHandler.setMana(((ServerPlayer) newPlayer), event.getOriginal().getData(ModAttachmentTypes.MANA));
        newPlayer.setData(ModAttachmentTypes.HOME_POS, event.getOriginal().getData(ModAttachmentTypes.HOME_POS));
    }

    @SubscribeEvent
    public static void setPlayersManaOnDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ManaHandler.setMana(((ServerPlayer) player), player.getData(ModAttachmentTypes.MANA));
        player.setData(ModAttachmentTypes.HOME_POS, player.getData(ModAttachmentTypes.HOME_POS));
    }

    @SubscribeEvent
    public static void setPlayersManaOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        ManaHandler.setMana(((ServerPlayer) player), player.getData(ModAttachmentTypes.MANA));
        player.setData(ModAttachmentTypes.HOME_POS, player.getData(ModAttachmentTypes.HOME_POS));
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if(event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + " just hit this sheep with an End Rod? YOU SICK FRICK!"));
                player.getMainHandItem().shrink(1);
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 6));
            }
        }
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        SetHomeCommand.register(event.getDispatcher());
        ReturnHomeCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addMix(Potions.AWKWARD, Blocks.DIRT.asItem(), ModPotions.STINKY_POTION);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.PENGUIN.get(), PenguinEntity.createPenguinAttributes().build());
        event.put(ModEntities.WARTURTLE.get(), WarturtleEntity.createAttributes().build());
        event.put(ModEntities.DODO.get(), DodoEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.PENGUIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PathfinderMob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
