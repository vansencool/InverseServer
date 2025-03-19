package net.vansen.commands.skin.sub;

import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.*;
import net.minestom.server.command.builder.*;
import net.minestom.server.command.builder.arguments.*;
import net.minestom.server.entity.*;
import net.vansen.utils.skin.*;

public class SetSubCommand extends Command {

    public SetSubCommand() {
        super("set");

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player)) {
                sender.sendMessage("you can't use this command!!");
                return;
            }
            sender.sendMessage(Component.text("Please specify a player name").color(NamedTextColor.RED));
        });

        var playerArg = ArgumentType.String("player");

        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            String playerName = context.get(playerArg);
            SkinManager.copySkin(player, playerName);
            player.sendActionBar(Component.text("Your skin has been set to " + playerName + "'s skin").color(TextColor.fromHexString("#e085ff")));
        }, playerArg);
    }
}