package net.vansen.commands.staff.utils.gamemode;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;

public class CreativeCommand extends Command {

    public CreativeCommand() {
        super("creative", "gmc");

        setCondition(((sender, commandString) -> Permissions.GAMEMODE.has(sender)));

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to use this command!");
                return;
            }

            player.setGameMode(GameMode.CREATIVE);
            player.sendActionBar(Component.text("You are now in creative mode!", TextColor.fromHexString("#958aff")));
        });
    }
}
