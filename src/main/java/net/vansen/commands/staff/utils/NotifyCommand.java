package net.vansen.commands.staff.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;
import net.vansen.utils.notify.NotifyManager;

import java.util.Objects;

public class NotifyCommand extends Command {

    public NotifyCommand() {
        super("notify");

        setCondition(((sender, commandString) -> Permissions.MONITOR.has(sender)));

        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("you can't use this command!!");
                return;
            }
            if (NotifyManager.check(player.getUsername())) {
                NotifyManager.removePlayer(player.getUsername());
                player.sendActionBar(Component.text("you will no longer be notified everytime a chunk is saved!").color(NamedTextColor.GREEN));
            } else {
                NotifyManager.addPlayer(player.getUsername());
                player.sendActionBar(Component.text("you will be notified everytime a chunk is saved!").color(NamedTextColor.GREEN));
            }
        });

        var arg = ArgumentType.String("boolean");

        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            switch (context.get(arg)) {
                case "true", "on" -> start(player.getUsername());
                case "false", "off" -> stop(player.getUsername());
                default -> sender.sendMessage(Component
                        .text("Invalid argument! Valid arguments: on, off")
                        .color(NamedTextColor.RED));
            }
        }, arg);
    }

    public void start(String name) {
        NotifyManager.addPlayer(name);
        Objects.requireNonNull(MinecraftServer
                        .getConnectionManager()
                        .getOnlinePlayerByUsername(name))
                .sendActionBar(Component
                        .text("you will be notified everytime a chunk is saved!")
                        .color(NamedTextColor.GREEN));
    }

    public void stop(String name) {
        NotifyManager.removePlayer(name);
        Objects.requireNonNull(MinecraftServer
                        .getConnectionManager()
                        .getOnlinePlayerByUsername(name))
                .sendActionBar(Component
                        .text("you will no longer be notified everytime a chunk is saved!")
                        .color(NamedTextColor.GREEN));
    }
}
