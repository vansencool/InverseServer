package net.vansen.events.player.sub;

import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
import net.minestom.server.network.player.GameProfile;

import java.util.UUID;

public class PlayerPreLogin {

    public static void run(AsyncPlayerPreLoginEvent event) {
        event.setGameProfile(new GameProfile(UUID.nameUUIDFromBytes(event.getGameProfile().name().getBytes()), event.getGameProfile().name()));
    }
}
