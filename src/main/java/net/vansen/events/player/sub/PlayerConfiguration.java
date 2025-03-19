package net.vansen.events.player.sub;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.vansen.util.Variables;
import net.vansen.utils.playerdata.PlayerDataManager;

public class PlayerConfiguration {

    public static void run(AsyncPlayerConfigurationEvent event) {
        final Player player = event.getPlayer();
        event.setSpawningInstance(Variables.instanceContainer);
        player.setRespawnPoint(PlayerDataManager.lastLocationOr(player.getUsername(), new Pos(0, 66, 0)));
    }
}
