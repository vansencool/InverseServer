package net.vansen.events.player.sub;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.vansen.utils.playerdata.PlayerDataManager;

public class PlayerDisconnect {

    public static void run(PlayerDisconnectEvent event) {
        PlayerDataManager.setLastLocation(event.getPlayer().getUsername(), event.getPlayer().getPosition());
        PlayerDataManager.lastGamemode(event.getPlayer().getUsername(), event.getPlayer().getGameMode());
        Audiences.server().sendMessage(MiniMessage.miniMessage().deserialize("<color:#ff336d>Welcomer -> " + PlainTextComponentSerializer.plainText().serialize(event.getPlayer().getName()) + " Has left the server!</color>"));
    }
}
