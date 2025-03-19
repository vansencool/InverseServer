package net.vansen.commands.staff.utils.fly;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;

public class FlyingSpeedCommand extends Command {

    public FlyingSpeedCommand() {
        super("flyingspeed", "flyspeed", "fs");

        setCondition(((sender, commandString) -> Permissions.FLY.has(sender)));

        var speedArg = ArgumentType.Float("speed");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("You must be a player to use this command!");
                return;
            }

            player.setFlyingSpeed(context.get("speed"));
            player.sendActionBar(Component.text("You are now flying with a speed of " + context.get("speed"), TextColor.fromHexString("#958aff")));
        }, speedArg);
    }
}
