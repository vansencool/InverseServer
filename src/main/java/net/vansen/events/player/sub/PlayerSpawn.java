package net.vansen.events.player.sub;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.vansen.utils.disguise.DisguiseManager;
import net.vansen.utils.playerdata.PlayerDataManager;

public class PlayerSpawn {

    public static void run(PlayerSpawnEvent event) {
        DisguiseManager.checkForUpdate(event.getPlayer());
        if (!PlayerDataManager.lastLocationExists(event.getPlayer().getUsername())) {
            Audiences.server().sendMessage(MiniMessage.miniMessage().deserialize("<color:#70ffb5>Welcomer -> Welcome, " + PlainTextComponentSerializer.plainText().serialize(event.getPlayer().getName()) + "! First time here? Check out our rules and enjoy your stay!</color>"));
        } else {
            Audiences.server().sendMessage(MiniMessage.miniMessage().deserialize("<color:#70ffb5>Welcomer -> Welcome back, " + PlainTextComponentSerializer.plainText().serialize(event.getPlayer().getName()) + "!</color>"));
        }
        event.getPlayer().setGameMode(PlayerDataManager.lastGamemode(event.getPlayer().getUsername()));
    }
}
