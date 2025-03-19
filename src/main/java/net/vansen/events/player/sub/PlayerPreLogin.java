package net.vansen.events.player.sub;

import net.minestom.server.event.player.*;
import net.minestom.server.network.player.*;

import java.util.*;

public class PlayerPreLogin {

    public static void run(AsyncPlayerPreLoginEvent event) {
        event.setGameProfile(new GameProfile(UUID.nameUUIDFromBytes(event.getGameProfile().name().getBytes()), event.getGameProfile().name()));
    }
}
