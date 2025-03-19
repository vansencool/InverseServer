package net.vansen.utils.disguise;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.vansen.noksdb.NoksDB;

import java.io.File;
import java.util.Map;

public class DisguiseManager {

    private static final NoksDB db = NoksDB.builder()
            .storageFile(new File("data/player", "disguises.dat"))
            .autoSaveAsync(true)
            .compressionBySerializer(true)
            .build();

    public static void addPlayer(String playerName, String target) {
        db.update("disguises").value(playerName, target).applyAsync();
    }

    public static void removePlayer(String playerName) {
        db.deleteField("disguises", playerName);
    }

    public static boolean check(String playerName) {
        Map<String, Object> disguises = db.store().get("disguises");
        if (disguises == null) {
            return false;
        }
        return disguises.containsKey(playerName);
    }

    public static void checkForUpdate(Player player) {
        if (check(player.getUsername())) {
            String disguise = (String) db.store().get("disguises").get(player.getUsername());
            player.setDisplayName(Component.text(disguise));
            player.setCustomName(Component.text(disguise));
        }
    }
}
