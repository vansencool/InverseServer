package net.vansen.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.vansen.permissions.Permissions;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop");

        setDefaultExecutor((sender, context) -> {
            if (!Permissions.OP.has(sender)) {
                sender.sendMessage("You don't have permission to use this command!");
                return;
            }
            MinecraftServer.stopCleanly();
        });
    }
}
