package net.kaupenjoe.mccourse.networking.packet;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record TestPacketC2S(String name, int value) implements CustomPacketPayload {
    public static final Type<TestPacketC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "test_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TestPacketC2S> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            TestPacketC2S::name,

            ByteBufCodecs.VAR_INT,
            TestPacketC2S::value,

            TestPacketC2S::new);


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
