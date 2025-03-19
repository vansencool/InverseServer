package net.vansen.tasks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.TaskSchedule;
import net.vansen.terminal.MinestomTerminal;
import net.vansen.util.Variables;
import net.vansen.utils.mspt.MonitorManager;
import net.vansen.utils.notify.NotifyManager;

public class Tasks {

    private static void chunkSavingTask() {
        MinecraftServer.getSchedulerManager().submitTask(() -> {
            Variables.instanceContainer.saveChunksToStorage();
            NotifyManager.notifyPlayers(Component.text("Saving chunks!").color(NamedTextColor.GREEN));
            return TaskSchedule.seconds(3);
        });
    }

    @SuppressWarnings("all")
    private static void msptMonitoringTask() {
        MinecraftServer.getSchedulerManager().submitTask(() -> {
            try {
                MonitorManager.notifyPlayers(Component.text()
                        .append(Component.text("Current Players " + MinecraftServer.getConnectionManager().getOnlinePlayers().size() + " | ", TextColor.fromHexString("#abafff")))
                        .append(Component.text("Current Threads " + Thread.activeCount() + " | ", TextColor.fromHexString("#abafff")))
                        .append(Component.text("Current Memory Usage " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + " MB", TextColor.fromHexString("#abafff")))
                        .build());
            } catch (Exception ignored) {
            }
            return TaskSchedule.millis(700);
        });
    }

    private static void shutdownTask() {
        MinecraftServer.getSchedulerManager().buildShutdownTask(MinestomTerminal::stop);
    }

    public static void tasks() {
        msptMonitoringTask();
        chunkSavingTask();
        shutdownTask();
    }
}
