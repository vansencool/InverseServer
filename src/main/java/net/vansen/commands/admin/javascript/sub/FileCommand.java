package net.vansen.commands.admin.javascript.sub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.vansen.commands.admin.javascript.JavascriptCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileCommand extends Command {

    public FileCommand() {
        super("file");

        var argument = ArgumentType.String("path");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("you can't use this command!!!");
                return;
            }
            if (!Files.exists(Path.of("scripts", context.get(argument) + ".js"))) {
                player.sendMessage(Component.text("File does not exist!", TextColor.fromHexString("#ff6183")));
                return;
            }
            try {
                JavascriptCommand.execute(player, String.join("\n", Files.readAllLines(Path.of("scripts", context.get(argument) + ".js"))));
            } catch (IOException e) {
                player.sendMessage(Component.text("Error! " + e.getMessage(), TextColor.fromHexString("#ff6183")));
            }
            }, argument);
    }
}
