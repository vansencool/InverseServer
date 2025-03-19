package net.vansen.events.player.sub;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerCommandEvent;

public class PlayerCommand {

    public static void run(PlayerCommandEvent event) {
        MinecraftServer.getCommandManager().getConsoleSender().sendMessage(
                MiniMessage.miniMessage().deserialize(
                        "<#8cb8ff>Console Log: <white><hover:show_text:'<#8cb8ff>Player UUID: " + event.getPlayer().getUuid() + "'>" +
                                event.getPlayer().getUsername() +
                                " <#8cb8ff>executed command: " + event.getCommand()
                )
        );
    }
}
