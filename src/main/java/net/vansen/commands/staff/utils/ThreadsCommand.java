package net.vansen.commands.staff.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.command.builder.Command;
import net.vansen.permissions.Permissions;

public class ThreadsCommand extends Command {

    public ThreadsCommand() {
        super("threads");

        setCondition(((sender, commandString) -> Permissions.THREADS.has(sender)));

        setDefaultExecutor((sender, context) -> {
            Component header = MiniMessage.miniMessage().deserialize("<#8cb8ff>Active Threads (<white>" + Thread.getAllStackTraces().keySet().stream().filter(Thread::isAlive).count() + "<#8cb8ff> threads)");
            sender.sendMessage(header);

            int running = 0;
            int sleeping = 0;
            int waiting = 0;
            for (Thread thread : Thread.getAllStackTraces().keySet().stream().filter(Thread::isAlive).toArray(Thread[]::new)) {
                String state = thread.getState().toString();
                String task = "";
                switch (state) {
                    case "RUNNABLE" -> {
                        task = " (currently running)";
                        running++;
                    }
                    case "TIMED_WAITING" -> {
                        task = " (sleeping)";
                        sleeping++;
                    }
                    case "WAITING" -> {
                        task = " (waiting)";
                        waiting++;
                    }
                }
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#8cb8ff>- <hover:show_text:'<#8cb8ff>State: " + state + ", ID: " + thread.threadId() + "'> " + thread.getName() + task));
            }

            sender.sendMessage(MiniMessage.miniMessage().deserialize("<#8cb8ff>Running threads: <white>" + running));
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<#8cb8ff>Waiting threads: <white>" + waiting));
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<#8cb8ff>Sleeping threads: <white>" + sleeping));
        });
    }
}
