package net.vansen.commands.staff.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.command.builder.Command;
import net.vansen.permissions.Permissions;

public class ThreadsCommand extends Command {

    public ThreadsCommand() {
        super("threads");

        setCondition(((sender, commandString) -> Permissions.MONITOR.has(sender)));

        setDefaultExecutor((sender, context) -> {
            Component header = MiniMessage.miniMessage().deserialize("<#8cb8ff>Active Threads (<white>" + Thread.getAllStackTraces().keySet().stream().filter(thread -> thread.isAlive()).count() + "<#8cb8ff> threads)");
            sender.sendMessage(header);

            for (Thread thread : Thread.getAllStackTraces().keySet().stream().filter(Thread::isAlive).toArray(Thread[]::new)) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#8cb8ff>- <hover:show_text:'<#8cb8ff>State: " + thread.getState() + ", ID: " + thread.getId() + "'> " + thread.getName()));
            }
        });
    }
}
