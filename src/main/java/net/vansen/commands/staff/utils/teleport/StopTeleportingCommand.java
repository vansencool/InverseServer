package net.vansen.commands.staff.utils.teleport;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.vansen.permissions.Permissions;
import net.vansen.util.Variables;

public class StopTeleportingCommand extends Command {

    public StopTeleportingCommand() {
        super("stopteleporting");

        setCondition(((sender, commandString) -> Permissions.RANDOM_TELEPORT.has(sender)));

        setDefaultExecutor((sender, context) -> {
            Variables.teleporting = false;
            if (StartTeleportingPlayersAroundRandomlyCommand.teleportThread != null) {
                StartTeleportingPlayersAroundRandomlyCommand.teleportThread.interrupt();
                try {
                    StartTeleportingPlayersAroundRandomlyCommand.teleportThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    StartTeleportingPlayersAroundRandomlyCommand.teleportThread = null;
                    sender.sendMessage(Component.text("Stopped teleporting players around.").color(TextColor.fromHexString("#abafff")));
                }
            } else {
                sender.sendMessage(Component.text("It's already stopped!").color(TextColor.fromHexString("#abafff")));
            }
        });
    }
}