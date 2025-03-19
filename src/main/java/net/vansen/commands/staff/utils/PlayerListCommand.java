package net.vansen.commands.staff.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class PlayerListCommand extends Command {

    public PlayerListCommand() {
        super("playerlist");

        var pageArg = ArgumentType.Integer("page").setDefaultValue(1);

        addSyntax((sender, context) -> {
            int page = context.get("page") == null ? 1 : Integer.parseInt(context.get("page"));
            int pageSize = 10;
            int start = (page - 1) * pageSize;
            int end = start + pageSize;
            int totalPages = (int) Math.ceil((double) MinecraftServer.getConnectionManager().getOnlinePlayers().size() / pageSize);

            Component header = MiniMessage.miniMessage().deserialize("<#8cb8ff>Online Players (<white>" + MinecraftServer.getConnectionManager().getOnlinePlayers().size() + "<#8cb8ff> players, <white>" + totalPages + "<#8cb8ff> pages)");
            sender.sendMessage(header);

            Component pagination = MiniMessage.miniMessage().deserialize("<#8cb8ff>Page <white>" + page + "<#8cb8ff> of <white>" + totalPages);
            sender.sendMessage(pagination);

            int count = 0;
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                if (count >= start && count < end) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<#8cb8ff>- <white><hover:show_text:'<#8cb8ff>x "
                            + player.getPosition().x()
                            + ", y " + player.getPosition().y()
                            + ", z " + player.getPosition().z())
                            + ", world "
                            + player.getInstance().getDimensionName() + ">" + player.getUsername());
                }
                count++;
            }

            if (MinecraftServer.getConnectionManager().getOnlinePlayers().size() > end) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#8cb8ff>Next page: /playerlist <white>page=" + (page + 1)));
            }
        }, pageArg);
    }
}