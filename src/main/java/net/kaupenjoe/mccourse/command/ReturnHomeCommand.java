package net.kaupenjoe.mccourse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ReturnHomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home")
                .then(Commands.literal("return").executes(ReturnHomeCommand::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        if(player == null) {
            context.getSource().sendFailure(Component.literal("Command wasn't send by a Player!"));
            return -1;
        }
        BlockPos homePos = player.getData(ModAttachmentTypes.HOME_POS);

        if(homePos != BlockPos.ZERO) {
            String positionString = "(" + homePos.getX() + ", " + homePos.getY() + ", " + homePos.getZ() + ")";
            player.teleportTo(homePos.getX(), homePos.getY(), homePos.getZ());
            context.getSource().sendSuccess(() -> Component.literal("Player return Home at " + positionString), false);
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("No Home Position has been set!"));
            return -1;
        }
    }
}
