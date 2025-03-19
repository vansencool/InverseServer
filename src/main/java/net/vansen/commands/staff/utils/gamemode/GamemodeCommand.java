package net.vansen.commands.staff.utils.gamemode;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand extends Command {
    public GamemodeCommand() {
        super("gamemode");

        setCondition(((sender, commandString) -> Permissions.GAMEMODE.has(sender)));

        var gamemodeArg = word();

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to use this command!");
                return;
            }
            try {
                player.setGameMode(GameMode.valueOf(context.get(gamemodeArg).toUpperCase()));
                player.sendActionBar(Component.text("You are now in " + Character.toUpperCase(context.get(gamemodeArg).charAt(0)) + context.get(gamemodeArg).toLowerCase().substring(1) + " mode!", TextColor.fromHexString("#958aff")));
            } catch (Exception e) {
                sender.sendMessage(Component.text("Invalid gamemode!", TextColor.fromHexString("#ff0000")));
            }
        }, gamemodeArg);
    }

    private static @NotNull ArgumentWord word() {
        var gamemodeArg = ArgumentType.Word("gamemode");
        gamemodeArg.setSuggestionCallback(((sender, context, suggestion) -> {
            for (GameMode mode : GameMode.values()) {
                if ((int) context.getInput().split(" ")[1].charAt(0) == 0) {
                    suggestion.addEntry(new SuggestionEntry(mode.name().toLowerCase()));
                    continue;
                }
                if (mode.name().toLowerCase().startsWith(context.getInput().trim().split(" ")[1].toLowerCase())) {
                    suggestion.addEntry(new SuggestionEntry(mode.name().toLowerCase(), Component.text("Click to choose gamemode: " + mode.name().toLowerCase())));
                }
            }
        }));
        return gamemodeArg;
    }
}
