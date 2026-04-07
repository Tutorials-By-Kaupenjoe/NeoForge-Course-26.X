package net.kaupenjoe.mccourse.networking;

import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.kaupenjoe.mccourse.networking.packet.ManaPacketS2C;
import net.neoforged.neoforge.network.handling.IPayloadContext;

// HERE WE ARE ON THE CLIENT
public class ClientboundPackets {
    public static void handleManaPacket(ManaPacketS2C manaPacket, IPayloadContext context) {
        context.player().setData(ModAttachmentTypes.MANA, manaPacket.newValue());
    }
}
