package net.vansen.events.server.sub;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.ResponseData;

public class ServerListPing {

    public static void run(ServerListPingEvent event) {
        ResponseData response = new ResponseData();

        response.setMaxPlayer(Integer.MAX_VALUE);
        response.setVersion("1.21.2-1.21.3");
        response.setOnline(MinecraftServer.getConnectionManager().getOnlinePlayerCount());

        event.setResponseData(response);
    }
}
