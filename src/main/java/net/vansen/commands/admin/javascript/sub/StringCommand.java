package net.vansen.commands.admin.javascript.sub;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.vansen.commands.admin.javascript.JavascriptCommand;

public class StringCommand extends Command {

    public StringCommand() {
        super("string");

        var argument = ArgumentType.StringArray("code");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("you can't use this command!!");
                return;
            }
            JavascriptCommand.execute(player, String.join(" ", context.get(argument)));
        }, argument);
    }
}
