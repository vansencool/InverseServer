package net.vansen.events.player;

import net.minestom.server.event.EventNode;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.player.*;
import net.vansen.events.player.sub.*;

public class PlayerEvents {

    public static EventNode<?> get() {
        var eventNode = EventNode.all("players-node");

        eventNode.addListener(AsyncPlayerConfigurationEvent.class, PlayerConfiguration::run);

        eventNode.addListener(PlayerBlockBreakEvent.class, PlayerBlockBreak::run);

        eventNode.addListener(ItemDropEvent.class, ItemDrop::run);

        eventNode.addListener(PlayerSkinInitEvent.class, PlayerSkinInit::run);

        eventNode.addListener(AsyncPlayerPreLoginEvent.class, PlayerPreLogin::run);

        eventNode.addListener(PlayerSpawnEvent.class, PlayerSpawn::run);

        eventNode.addListener(PlayerDisconnectEvent.class, PlayerDisconnect::run);

        eventNode.addListener(PlayerCommandEvent.class, PlayerCommand::run);

        eventNode.addListener(PlayerChatEvent.class, PlayerChat::run);
        
        return eventNode;
    }
}
