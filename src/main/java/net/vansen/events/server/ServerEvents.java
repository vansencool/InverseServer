package net.vansen.events.server;

import net.minestom.server.event.*;
import net.minestom.server.event.server.*;
import net.vansen.events.server.sub.*;

public class ServerEvents {

    public static EventNode<?> get() {
        var eventNode = EventNode.all("server-node");

        eventNode.addListener(ServerListPingEvent.class, ServerListPing::run);
        return eventNode;
    }
}
