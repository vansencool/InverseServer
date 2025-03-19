package net.vansen.commands.skin.sub;

import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.*;
import net.minestom.server.command.builder.*;
import net.minestom.server.entity.*;
import net.vansen.utils.skin.*;

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