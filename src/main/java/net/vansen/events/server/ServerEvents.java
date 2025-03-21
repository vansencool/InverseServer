package net.vansen.events.server;

import net.minestom.server.event.EventNode;
import net.minestom.server.event.server.ServerListPingEvent;
import net.vansen.events.server.sub.ServerListPing;

public class ServerEvents {

    public static EventNode<?> get() {
        var eventNode = EventNode.all("server-node");

        eventNode.addListener(ServerListPingEvent.class, ServerListPing::run);
        return eventNode;
    }
}
