package net.vansen.commands.staff.utils.gamemode;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;

public class SurvivalCommand extends Command {

    public SurvivalCommand() {
        super("survival", "gms");

        setCondition(((sender, commandString) -> Permissions.GAMEMODE.has(sender)));

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to use this command!");
                return;
            }

            player.setGameMode(GameMode.SURVIVAL);
            player.sendActionBar(Component.text("You are now in survival mode!", TextColor.fromHexString("#958aff")));
        });
    }
}
