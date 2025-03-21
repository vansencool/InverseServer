package net.vansen.commands.skin.sub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.vansen.utils.skin.SkinManager;

public class ResetSubCommand extends Command {

    public ResetSubCommand() {
        super("reset");

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("you can't use this command!!");
                return;
            }
            SkinManager.reset(player);
            player.sendActionBar(Component.text("Your skin has been reset").color(TextColor.fromHexString("#abafff")));
        });
    }
}