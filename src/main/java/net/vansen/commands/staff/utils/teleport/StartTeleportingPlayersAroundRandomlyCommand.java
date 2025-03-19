package net.vansen.commands.staff.utils.teleport;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.vansen.permissions.Permissions;
import net.vansen.util.Variables;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StartTeleportingPlayersAroundRandomlyCommand extends Command {
    public static Thread teleportThread;

    public StartTeleportingPlayersAroundRandomlyCommand() {
        super("startteleporting");

        setCondition(((sender, commandString) -> Permissions.RANDOM_TELEPORT.has(sender)));

        setDefaultExecutor((sender, context) -> {
            startTeleportingPlayersAroundRandomly();
            sender.sendMessage(Component.text("Teleporting players around randomly...").color(TextColor.fromHexString("#c39cff")));
        });
    }

    private void startTeleportingPlayersAroundRandomly() {
        Variables.teleporting = true;
        // TODO: somehow make this better
        teleportThread = new Thread(() -> {
            try {
                Map<Player, Double> playerAngles = new ConcurrentHashMap<>();

                while (Variables.teleporting) {
                    for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                        double angle = playerAngles.computeIfAbsent(player, p -> Math.random() * 2 * Math.PI);
                        angle += Math.random() * Math.PI / 10 - Math.PI / 20;
                        angle = angle % (2 * Math.PI);
                        if (angle < 0) angle += 2 * Math.PI;
                        playerAngles.put(player, angle);
                        double dx = Math.cos(angle);
                        double dz = Math.sin(angle);
                        player.teleport(player.getPosition().add(dx, 0, dz));
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (Exception e) {
                System.err.println("Error while teleporting players around randomly: " + e.getMessage());
                Variables.teleporting = false;
                Thread.currentThread().interrupt();
            }
        });
        teleportThread.start();
    }
}