package net.vansen.commands.staff.utils.fly;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;

public class FlyCommand extends Command {

    public FlyCommand() {
        super("fly", "flying");

        setCondition(((sender, commandString) -> Permissions.FLY.has(sender)));

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to use this command!");
                return;
            }
            player.setFlying(!player.isFlying());
            player.sendActionBar(Component.text("You are now " + (player.isFlying() ? "flying" : "not flying") + "!", TextColor.fromHexString("#958aff")));
        });

        var booleanArg = ArgumentType.Boolean("enable");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to use this command!");
                return;
            }
            player.setFlying(context.get(booleanArg));
            player.sendActionBar(Component.text("You are now " + (player.isFlying() ? "flying" : "not flying") + "!", TextColor.fromHexString("#958aff")));
        }, booleanArg);
    }
}
