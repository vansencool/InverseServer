package net.vansen.utils.notify;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.vansen.noksdb.NoksDB;

import java.io.File;
import java.util.Map;

public class NotifyManager {

    private static final NoksDB db = NoksDB.builder()
            .storageFile(new File("data/player", "notifications.dat"))
            .autoSaveAsync(true)
            .compressionBySerializer(true)
            .build();

    public static void addPlayer(String playerName) {
        db.update("notified").value(playerName, "true").applyAsync();
    }

    public static void removePlayer(String playerName) {
        db.deleteField("notified", playerName);
    }

    public static boolean check(String playerName) {
        Map<String, Object> notifiedPlayers = db.store().get("notified");
        if (notifiedPlayers == null) {
            return false;
        }
        return notifiedPlayers.containsKey(playerName);
    }

    public static void notifyPlayers(Component component) {
        Map<String, Object> notifiedPlayers = db.store().get("notified");
        if (!(notifiedPlayers == null)) {
            for (String name : notifiedPlayers.keySet()) {
                Player player = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(name);
                if (player != null) {
                    player.sendActionBar(component);
                }
            }
        }
    }
}