package net.kaupenjoe.mccourse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.kaupenjoe.mccourse.attachmenttype.ModAttachmentTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SetHomeCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home")
                .then(Commands.literal("set").executes(SetHomeCommand::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context) {
        Player player = context.getSource().getPlayer();
        if(player == null) {
            context.getSource().sendFailure(Component.literal("Command wasn't send by a Player!"));
            return -1;
        }

        BlockPos playerPos = player.blockPosition() != BlockPos.ZERO ? player.blockPosition() : BlockPos.ZERO.above(1);
        String positionString = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";
        player.setData(ModAttachmentTypes.HOME_POS, playerPos);

        context.getSource().sendSuccess(() -> Component.literal("Set Home at " + positionString), false);
        return 1;
    }
}
