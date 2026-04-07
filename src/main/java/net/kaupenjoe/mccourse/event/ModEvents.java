package net.kaupenjoe.mccourse.event;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.kaupenjoe.mccourse.attachmenttype.handler.ManaHandler;
import net.kaupenjoe.mccourse.networking.ClientboundPackets;
import net.kaupenjoe.mccourse.networking.ServerboundPackets;
import net.kaupenjoe.mccourse.networking.packet.ManaPacketS2C;
import net.kaupenjoe.mccourse.networking.packet.TestPacketC2S;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

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
    }

    @SubscribeEvent
    public static void setPlayersManaOnDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ManaHandler.setMana(((ServerPlayer) player), player.getData(ModAttachmentTypes.MANA));
    }

    @SubscribeEvent
    public static void setPlayersManaOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        ManaHandler.setMana(((ServerPlayer) player), player.getData(ModAttachmentTypes.MANA));
    }
}
