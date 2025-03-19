package net.vansen.commands.staff.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;
import net.vansen.utils.mspt.MonitorManager;

public class MonitorCommand extends Command {

    public MonitorCommand() {
        super("servermonitor", "sm", "monitor");

        setCondition(((sender, commandString) -> Permissions.MONITOR.has(sender)));

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("you can't use this command!!");
                return;
            }
            if (MonitorManager.check(player.getUsername())) {
                MonitorManager.removePlayer(player.getUsername());
                player.sendActionBar(Component
                        .text("you are no longer monitoring...")
                        .color(NamedTextColor.GREEN));
            } else {
                MonitorManager.addPlayer(player.getUsername());
            }
        });

        var arg = ArgumentType.String("boolean");

        addSyntax((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("you can't use this command!!");
                return;
            }
            switch (context.get(arg)) {
                case "true", "on" -> start(player.getUsername());
                case "false", "off" -> stop(player);
                default -> sender.sendMessage(Component
                        .text("Invalid argument! Valid arguments: on, off")
                        .color(NamedTextColor.RED));
            }
        }, arg);
    }

    public void start(String name) {
        MonitorManager.addPlayer(name);
    }

    public void stop(Player player) {
        MonitorManager.removePlayer(player.getUsername());
        player.sendActionBar(Component
                .text("you are no longer monitoring...")
                .color(NamedTextColor.GREEN));
    }
}
