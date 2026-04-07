package net.kaupenjoe.mccourse.attachmenttype;

import com.mojang.serialization.Codec;
import net.kaupenjoe.mccourse.MCCourse;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MCCourse.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> MANA = ATTACHMENT_TYPES.register("mana",
            () -> AttachmentType.builder(() -> 0) // .sync(ByteBufCodecs.INT) // this auto-syncs HOWEVER, I wanna teach Networking!
                    .serialize(Codec.INT.fieldOf("mana")).build());


    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
