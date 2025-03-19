package net.vansen.events.player.sub;

import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.vansen.utils.skin.SkinManager;

public class PlayerSkinInit {

    public static void run(PlayerSkinInitEvent event) {
        if (!SkinManager.checkForSkinUpdate(event)) {
            event.setSkin(PlayerSkin.fromUsername(event.getPlayer().getUsername()));
        }
    }
}
