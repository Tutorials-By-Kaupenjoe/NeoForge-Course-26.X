package net.kaupenjoe.mccourse.event;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.kaupenjoe.mccourse.networking.ServerboundPackets;
import net.kaupenjoe.mccourse.networking.packet.TestPacketC2S;
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
    }

    @SubscribeEvent
    public static void setPlayersManaOnSpawn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if(player.hasData(ModAttachmentTypes.MANA)) {
            player.setData(ModAttachmentTypes.MANA, player.getData(ModAttachmentTypes.MANA));
        } else {
            player.setData(ModAttachmentTypes.MANA, 5);
        }
    }

    @SubscribeEvent
    public static void setPlayersManaOnClone(PlayerEvent.Clone event) {
        Player newPlayer = event.getEntity();
        newPlayer.setData(ModAttachmentTypes.MANA, event.getOriginal().getData(ModAttachmentTypes.MANA));
    }

    @SubscribeEvent
    public static void setPlayersManaOnDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        player.setData(ModAttachmentTypes.MANA, player.getData(ModAttachmentTypes.MANA));
    }

    @SubscribeEvent
    public static void setPlayersManaOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        player.setData(ModAttachmentTypes.MANA, player.getData(ModAttachmentTypes.MANA));
    }
}
