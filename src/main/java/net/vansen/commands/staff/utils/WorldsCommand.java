package net.vansen.commands.staff.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.vansen.permissions.Permissions;

public class WorldsCommand extends Command {

    public WorldsCommand() {
        super("worlds", "worlds");

        setCondition(((sender, commandString) -> Permissions.WORLDS.has(sender)));

        setDefaultExecutor((sender, context) -> {
            StringBuilder instanceList = new StringBuilder();
            instanceList.append("<#87cdff>===== List of worlds =====<newline>");
            instanceList.append("<#87cdff>-------------------------------<newline>");
            MinecraftServer.getInstanceManager().getInstances().forEach(instance -> instanceList.append("   <#87cdff>").append(instance.getDimensionName()).append("<newline>"));
            instanceList.append("<#87cdff>-------------------------------<newline>");
            sender.sendMessage(MiniMessage.miniMessage().deserialize(instanceList.toString()));
        });
    }
}
