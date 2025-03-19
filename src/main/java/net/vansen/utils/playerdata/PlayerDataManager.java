package net.vansen.utils.playerdata;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.vansen.noksdb.NoksDB;

import java.io.File;
import java.util.Map;

public class PlayerDataManager {

    private static final NoksDB db = NoksDB.builder()
            .storageFile(new File("data/player", "playerlocations.dat"))
            .autoSaveAsync(true)
            .compressionBySerializer(true)
            .build();

    public static Pos lastLocationOr(String playerName, Pos pos) {
        Map<String, Object> playerData = db.store().get(playerName);
        if (playerData == null) {
            return pos;
        }
        Object x = playerData.get("x");
        if (x == null) {
            return pos;
        }
        return new Pos(
                (double) playerData.get("x"),
                (double) playerData.get("y"),
                (double) playerData.get("z"),
                (float) playerData.get("yaw"),
                (float) playerData.get("pitch")
        );
    }

    public static boolean lastLocationExists(String playerName) {
        try {
            return db.store().get(playerName) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static void setLastLocation(String playerName, Pos pos) {
        db.rowOf(playerName).value("x", pos.x()).value("y", pos.y()).value("z", pos.z()).value("yaw", pos.yaw()).value("pitch", pos.pitch()).insert();
    }

    public static GameMode lastGamemode(String playerName) {
        Map<String, Object> playerData = db.store().get(playerName);
        if (playerData == null) {
            return GameMode.SURVIVAL;
        }
        Object gamemode = playerData.get("gamemode");
        if (gamemode == null) {
            return GameMode.SURVIVAL;
        }
        return GameMode.values()[(int) gamemode];
    }

    public static void lastGamemode(String playerName, GameMode gamemode) {
        db.rowOf(playerName).value("gamemode", gamemode.ordinal()).insert();
    }
}
