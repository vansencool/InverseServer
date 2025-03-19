package net.vansen.terminal.format;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinecraftColorFormatter extends ClassicConverter {

    private final Pattern hexPattern = Pattern.compile("§#([0-9a-f]{6})");

    @Override
    public String convert(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        message = message
                .replaceAll("§0", "\u001B[30m") // Black
                .replaceAll("§1", "\u001B[34m") // Dark Blue
                .replaceAll("§2", "\u001B[32m") // Dark Green
                .replaceAll("§3", "\u001B[36m") // Dark Aqua
                .replaceAll("§4", "\u001B[31m") // Dark Red
                .replaceAll("§5", "\u001B[35m") // Dark Purple
                .replaceAll("§6", "\u001B[33m") // Gold
                .replaceAll("§7", "\u001B[37m") // Gray
                .replaceAll("§8", "\u001B[38;5;8m") // Dark Gray
                .replaceAll("§9", "\u001B[38;5;12m") // Blue
                .replaceAll("§a", "\u001B[92m") // Green
                .replaceAll("§b", "\u001B[96m") // Aqua
                .replaceAll("§c", "\u001B[91m") // Red
                .replaceAll("§d", "\u001B[38;5;13m") // Light Purple
                .replaceAll("§e", "\u001B[93m") // Yellow
                .replaceAll("§f", "\u001B[97m") // White
                .replaceAll("§l", "\u001B[1m") // Bold
                .replaceAll("§m", "\u001B[9m") // Strikethrough
                .replaceAll("§n", "\u001B[4m") // Underline
                .replaceAll("§o", "\u001B[3m") // Italic
                .replaceAll("§r", "\u001B[0m"); // Reset
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            String hex = matcher.group(1);
            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);
            String replacement = "\u001B[38;2;" + r + ";" + g + ";" + b + "m";
            message = message.replace("§#" + hex, replacement);
        }
        message += "\u001B[0m";
        return message;
    }
}