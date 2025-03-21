package net.vansen.utils.skin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.vansen.noksdb.NoksDB;

import java.io.File;
import java.util.Map;

public class SkinManager {

    private static final NoksDB db = NoksDB.builder()
            .storageFile(new File("data/player", "skins.dat"))
            .autoSaveAsync(true)
            .compressionBySerializer(true)
            .build();

    public static void copySkin(Player player, String target) {
        db.update("skins").value(player.getUuid().toString(), target).applyAsync();
        player.setSkin(PlayerSkin.fromUsername(target));
    }

    public static void reset(Player player) {
        Map<String, Object> skins = db.store().get("skins");
        if (skins != null) {
            skins.remove(player.getUuid().toString());
            player.setSkin(PlayerSkin.fromUuid(player.getUuid().toString()));
        }
    }

    public static boolean checkForSkinUpdate(PlayerSkinInitEvent event) {
        Map<String, Object> skins = db.store().get("skins");
        if (skins != null) {
            if (skins.containsKey(event.getPlayer().getUuid().toString())) {
                event.setSkin(PlayerSkin.fromUsername(skins.get(event.getPlayer().getUuid().toString()).toString()));
                event.getPlayer().sendMessage(Component.text("Using skin of: " + skins.get(event.getPlayer().getUuid().toString()).toString()).color(TextColor.fromHexString("#c39cff")));
                return true;
            }
        }
        return false;
    }
}