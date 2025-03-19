package net.vansen.basic;

import net.minestom.server.MinecraftServer;
import net.vansen.commands.Commands;

import java.util.Arrays;

public class CommandsRegistrar {

    public static void register() {
        Arrays.stream(Commands.values())
                .forEach(command -> MinecraftServer.getCommandManager().register(command.command));
    }
}
