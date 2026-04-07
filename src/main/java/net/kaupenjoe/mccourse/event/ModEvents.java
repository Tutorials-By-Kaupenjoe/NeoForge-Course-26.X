package net.kaupenjoe.mccourse.event;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.networking.ServerboundPackets;
import net.kaupenjoe.mccourse.networking.packet.TestPacketC2S;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = MCCourse.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(TestPacketC2S.TYPE, TestPacketC2S.STREAM_CODEC, ServerboundPackets::handleTestPacket);
    }
}
