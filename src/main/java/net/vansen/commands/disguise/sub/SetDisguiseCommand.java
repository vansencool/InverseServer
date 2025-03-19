package net.vansen.commands.disguise.sub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.vansen.utils.disguise.DisguiseManager;
import net.vansen.utils.skin.SkinManager;

public class SetDisguiseCommand extends Command {

    public SetDisguiseCommand() {
        super("set");

        var playerArg = ArgumentType.String("player");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("you can't use this command!!");
                return;
            }
            String playerName = context.get(playerArg);
            DisguiseManager.addPlayer(player.getUsername(), playerName);
            SkinManager.copySkin(player, context.get(playerArg));
            player.setDisplayName(Component.text(playerName));
            player.setCustomName(Component.text(playerName));
            player.sendActionBar(Component.text("Your disguise has been set to " + playerName + "'s disguise").color(TextColor.fromHexString("#e085ff")));
        }, playerArg);
    }
}
