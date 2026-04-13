package net.kaupenjoe.mccourse.attachmenttype.handler;

import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.kaupenjoe.mccourse.networking.packet.ManaPacketS2C;
import net.kaupenjoe.mccourse.stat.ModStats;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public class ManaHandler {
    public static void setMana(ServerPlayer player, int value) {
        player.setData(ModAttachmentTypes.MANA, value);
        // PacketDistributor.sendToPlayer(player, new ManaPacketS2C(0, value)); // Custom Syncing System disabled because .sync
    }

    public static void addMana(ServerPlayer player, int value) {
        int newManaValue = player.getData(ModAttachmentTypes.MANA) + value;
        if(newManaValue > 5) {
            newManaValue = 5;
        }

        setMana(player, newManaValue);
    }

    public static void removeMana(ServerPlayer player, int value) {
        int newManaValue = player.getData(ModAttachmentTypes.MANA) - value;
        if(newManaValue < 0) {
            newManaValue = 0;
        }

        player.awardStat(ModStats.MANA_USED_TOTAL_STAT.get(), value);
        setMana(player, newManaValue);
    }

    public static boolean hasPlayerOneManaLeft(Player player) {
        return hasPlayerManaLeft(player, 1);
    }

    public static boolean hasPlayerManaLeft(Player player, int value) {
        return player.hasData(ModAttachmentTypes.MANA) && player.getData(ModAttachmentTypes.MANA) >= value;
    }

    public static int getMana(Player player) {
        return player.getData(ModAttachmentTypes.MANA);
    }
}
