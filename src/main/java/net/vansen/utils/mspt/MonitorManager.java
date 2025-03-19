package net.vansen.utils.mspt;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.vansen.noksdb.NoksDB;

import java.io.File;
import java.util.Map;

public class MonitorManager {

    private static final NoksDB db = NoksDB.builder()
            .storageFile(new File("data/player", "msptmonitor.dat"))
            .autoSaveAsync(true)
            .compressionBySerializer(true)
            .build();

    public static void addPlayer(String playerName) {
        db.update("monitoring").value(playerName, "true").applyAsync();
    }

    public static void removePlayer(String playerName) {
        db.deleteField("monitoring", playerName);
    }

    public static boolean check(String playerName) {
        Map<String, Object> monitoringPlayers = db.store().get("monitoring");
        if (monitoringPlayers == null) {
            return false;
        }
        return monitoringPlayers.containsKey(playerName);
    }

    public static void notifyPlayers(Component component) {
        Map<String, Object> monitoringPlayers = db.store().get("monitoring");
        if (!(monitoringPlayers == null)) {
            for (String name : monitoringPlayers.keySet()) {
                Player player = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(name);
                if (player != null) {
                    player.sendActionBar(component);
                }
            }
        }
    }
}
