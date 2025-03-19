package net.vansen.commands.disguise.sub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.vansen.utils.disguise.DisguiseManager;

public class ResetDisguiseCommand extends Command {

    public ResetDisguiseCommand() {
        super("reset");

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to use this command!");
                return;
            }
            DisguiseManager.removePlayer(player.getUsername());
            player.setDisplayName(null);
            player.setCustomName(null);
            player.sendActionBar(Component.text("Your disguise has been reset").color(TextColor.fromHexString("#e085ff")));
        });
    }
}
