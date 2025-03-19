package net.vansen.commands.staff.utils.teleport;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;

public class TeleportCommand extends Command {

    public TeleportCommand() {
        super("teleport", "tp");

        setCondition(((sender, commandString) -> Permissions.TELEPORT.has(sender)));

        var arg = ArgumentType.String("player");
        arg.setSuggestionCallback((sender, context, suggestion) -> {
            for (GameMode mode : GameMode.values()) {
                if ((int) context.getInput().split(" ")[1].charAt(0) == 0) {
                    suggestion.addEntry(new SuggestionEntry(mode.name().toLowerCase()));
                    continue;
                }
                if (mode.name().toLowerCase().startsWith(context.getInput().trim().split(" ")[1].toLowerCase())) {
                    suggestion.addEntry(new SuggestionEntry(mode.name().toLowerCase(), Component.text("Gamemode " + mode.name().toLowerCase())));
                }
            }
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                if ((int) context.getInput().split(" ")[1].charAt(0) == 0) {
                    suggestion.addEntry(new SuggestionEntry(player.getUsername()));
                    continue;
                }
                if (player.getUsername().toLowerCase().startsWith(context.getInput().trim().split(" ")[1].toLowerCase())) {
                    suggestion.addEntry(new SuggestionEntry(player.getUsername(), Component.text("Click to choose player: " + player.getUsername())));
                }
            }
        });

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You can't use this command!!!");
                return;
            }
            Player target = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(context.get(arg));
            if (target == null) {
                sender.sendMessage("Player not found!");
                return;
            }
            player.teleport(target.getPosition());
        }, arg);
    }
}
