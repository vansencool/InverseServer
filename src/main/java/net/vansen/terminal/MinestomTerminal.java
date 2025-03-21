package net.vansen.terminal;


import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.suggestion.Suggestion;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.listener.TabCompleteListener;
import org.jetbrains.annotations.ApiStatus;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MinestomTerminal {
    private static final String PROMPT = "> ";
    static volatile LineReader reader;
    private static volatile Terminal terminal;
    private static volatile boolean running = false;

    @ApiStatus.Internal
    public static void start() {
        final Thread thread = new Thread(null, () -> {
            try {
                terminal = TerminalBuilder.terminal();
            } catch (IOException e) {
                LoggerFactory.getLogger("Terminal").error("Failed to create terminal!!", e);
                return;
            }
            reader = LineReaderBuilder.builder()
                    .completer(new MinestomCompleter())
                    .terminal(terminal)
                    .appName("Protected by OmniGuard (Very Fancy-Sounding Security Team)")
                    .build();
            running = true;
            while (running) {
                String command;
                try {
                    command = reader.readLine(PROMPT);
                    var commandManager = MinecraftServer.getCommandManager();
                    commandManager.execute(commandManager.getConsoleSender(), command);
                } catch (UserInterruptException e) {
                    System.exit(0);
                    return;
                } catch (EndOfFileException e) {
                    return;
                }
            }
        }, "Terminal");
        thread.setDaemon(true);
        thread.start();
    }

    @ApiStatus.Internal
    public static void stop() {
        running = false;
        if (terminal != null) {
            try {
                terminal.close();
            } catch (IOException e) {
                LoggerFactory.getLogger("Terminal").error("Failed to close terminal!!", e);
            }
            reader = null;
        }
    }

    private static final class MinestomCompleter implements Completer {
        @Override
        public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
            final var commandManager = MinecraftServer.getCommandManager();
            final var consoleSender = commandManager.getConsoleSender();
            if (line.wordIndex() == 0) {
                final String commandString = line.word().toLowerCase();
                candidates.addAll(
                        commandManager.getDispatcher().getCommands().stream()
                                .map(Command::getName)
                                .filter(name -> commandString.isBlank() || name.toLowerCase().startsWith(commandString))
                                .map(Candidate::new)
                                .toList()
                );
            } else {
                final String text = line.line();
                final Suggestion suggestion = TabCompleteListener.getSuggestion(consoleSender, text);
                if (suggestion != null) {
                    suggestion.getEntries().stream()
                            .map(SuggestionEntry::getEntry)
                            .map(Candidate::new)
                            .forEach(candidates::add);
                }
            }
        }
    }
}