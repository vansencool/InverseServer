package net.vansen.events.player.sub;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.vansen.chat.AdChecker;
import net.vansen.chat.ChatEntry;
import net.vansen.chat.SwearChecker;
import net.vansen.permissions.Permissions;
import net.vansen.util.Variables;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerChat {

    public static final ConcurrentHashMap<UUID, CompletableFuture<User>> userCache = new ConcurrentHashMap<>();
    private static final int SPAM_MESSAGE_LIMIT = 5;
    private static final long SPAM_TIME_LIMIT_MS = 4000;
    private static final double SIMILARITY_THRESHOLD = 0.75;
    private static final int REPEATED_CHAR_LIMIT = 10;
    private static final long WARNING_COOLDOWN_MS = 3600000;
    private static final ConcurrentHashMap<String, Player> playerCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, LinkedList<ChatEntry>> chatHistory = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<UUID, Long> lastWarningTime = new ConcurrentHashMap<>();

    public static @NotNull String translateAll(@NotNull String input) {
        input = input.replaceAll("&#([a-fA-F0-9]{6})", "<color:#$1>"); // Hex color codes

        // Legacy color codes
        input = input.replaceAll("&0", "<color:#000000>"); // Black
        input = input.replaceAll("&1", "<color:#0000AA>"); // Dark Blue
        input = input.replaceAll("&2", "<color:#00AA00>"); // Dark Green
        input = input.replaceAll("&3", "<color:#00AAAA>"); // Dark Aqua
        input = input.replaceAll("&4", "<color:#AA0000>"); // Dark Red
        input = input.replaceAll("&5", "<color:#AA00AA>"); // Dark Purple
        input = input.replaceAll("&6", "<color:#FFAA00>"); // Gold
        input = input.replaceAll("&7", "<color:#AAAAAA>"); // Gray
        input = input.replaceAll("&8", "<color:#555555>"); // Dark Gray
        input = input.replaceAll("&9", "<color:#5555FF>"); // Blue
        input = input.replaceAll("&a", "<color:#55FF55>"); // Green
        input = input.replaceAll("&b", "<color:#55FFFF>"); // Aqua
        input = input.replaceAll("&c", "<color:#FF5555>"); // Red
        input = input.replaceAll("&d", "<color:#FF55FF>"); // Light Purple
        input = input.replaceAll("&e", "<color:#FFFF55>"); // Yellow
        input = input.replaceAll("&f", "<color:#FFFFFF>"); // White
        input = input.replaceAll("&l", "<bold>");
        input = input.replaceAll("&o", "<italic>");
        input = input.replaceAll("&n", "<underlined>");
        input = input.replaceAll("&m", "<strikethrough>");
        input = input.replaceAll("&k", "<obfuscated>");
        input = input.replaceAll("&r", "<reset>");

        return input;
    }

    private static String isSpam(@NotNull Player sender, @NotNull String message) {
        UUID playerUUID = sender.getUuid();
        long currentTime = System.currentTimeMillis();
        LinkedList<ChatEntry> history = chatHistory.computeIfAbsent(playerUUID, k -> new LinkedList<>());

        if (repeat(message)) {
            return "Many Characters -> ";
        }

        history.add(new ChatEntry(message, currentTime));
        while (!history.isEmpty() && currentTime - history.getFirst().timestamp() > SPAM_TIME_LIMIT_MS) {
            history.removeFirst();
        }

        if (history.size() >= SPAM_MESSAGE_LIMIT) {
            double similaritySum = 0;
            int comparisons = 0;

            for (int i = 0; i < history.size() - 1; i++) {
                for (int j = i + 1; j < history.size(); j++) {
                    similaritySum += calculate(history.get(i).message(), history.get(j).message());
                    comparisons++;
                }
            }

            double averageSimilarity = similaritySum / comparisons;

            if (averageSimilarity >= SIMILARITY_THRESHOLD) {
                return "Similar Messages";
            }

            if (currentTime - history.getFirst().timestamp() <= SPAM_TIME_LIMIT_MS) {
                return "Anti Spam";
            }
        }

        return null;
    }

    private static boolean repeat(@NotNull String message) {
        int count = 1;
        char lastChar = message.charAt(0);

        for (int i = 1; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            if (currentChar == lastChar) {
                count++;
                if (count >= REPEATED_CHAR_LIMIT) {
                    return true;
                }
            } else {
                count = 1;
                lastChar = currentChar;
            }
        }

        return false;
    }

    private static double calculate(@NotNull String message1, @NotNull String message2) {
        int maxLength = Math.max(message1.length(), message2.length());
        if (maxLength == 0) return 1.0;
        int distance = distance(message1, message2);
        return 1.0 - ((double) distance / maxLength);
    }

    private static int distance(@NotNull String s1, @NotNull String s2) {
        int[] prev = new int[s2.length() + 1];
        int[] curr = new int[s2.length() + 1];

        for (int i = 0; i <= s2.length(); i++) prev[i] = i;

        for (int i = 1; i <= s1.length(); i++) {
            curr[0] = i;
            for (int j = 1; j <= s2.length(); j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                curr[j] = Math.min(Math.min(prev[j] + 1, curr[j - 1] + 1), prev[j - 1] + cost);
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[s2.length()];
    }

    private static CompletableFuture<String> formatMessage(@NotNull Player sender, @NotNull String message) {
        CompletableFuture<User> userFuture = userCache.computeIfAbsent(sender.getUuid(), uuid -> Variables.luckperms.getUserManager().loadUser(uuid));

        return userFuture.thenApply(user -> {
            @NotNull String prefix = Optional.ofNullable(user.getCachedData().getMetaData(QueryOptions.defaultContextualOptions()).getPrefix()).orElse("");
            @NotNull String coloredPrefix = translateAll(prefix);
            @NotNull String formattedMessage = String.format("%s%s <color:#ceccff>-> %s", coloredPrefix, PlainTextComponentSerializer.plainText().serialize(sender.getName()), message);
            return format(formattedMessage, sender);
        });
    }

    private static String format(@NotNull String message, @NotNull Player sender) {
        @NotNull Matcher matcher = Pattern.compile("\\b([a-zA-Z0-9_]{3,16})\\b").matcher(message);
        @NotNull StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            @NotNull String mention = matcher.group();
            Player mentionedPlayer = playerCache.computeIfAbsent(mention, (s) -> MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(s));

            if (mentionedPlayer != null && mentionedPlayer.isOnline() && !PlainTextComponentSerializer.plainText().serialize(sender.getName()).equals(PlainTextComponentSerializer.plainText().serialize(mentionedPlayer.getName()))) {
                @NotNull String replacement = String.format(
                        "<hover:show_text:'<color:#00FF00>Click here to message %s</color>'>" +
                                "<click:suggest_command:'/msg %s '>" +
                                "<color:#ceccff>%s</color>" +
                                "</click></hover>",
                        mention, mention, mention
                );
                matcher.appendReplacement(buffer, replacement);
            } else matcher.appendReplacement(buffer, mention);
        }

        matcher.appendTail(buffer);

        return buffer.toString();
    }

    public static void run(@NotNull PlayerChatEvent event) {
        event.setCancelled(true);
        if (!Permissions.CHAT_BYPASS.has(event.getPlayer())) {
            String r = isSpam(event.getPlayer(), event.getRawMessage());
            if (r != null) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastWarningTime.getOrDefault(event.getPlayer().getUuid(), 0L) >= WARNING_COOLDOWN_MS) {
                    event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<color:#ff2e35>" + r + " -> Spamming is not allowed!"));
                    lastWarningTime.put(event.getPlayer().getUuid(), currentTime);
                    return;
                } else {
                    event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<color:#ff2e35>" + r + " -> Spamming is not allowed!"));
                    return;
                }
            }

            if (AdChecker.check(event.getRawMessage())) {
                event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<color:#ff2e35>Anti Advertise -> Advertising is not allowed!"));
                return;
            }

            String check = SwearChecker.check(event.getRawMessage());
            if (check != null) {
                event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<color:#ff2e35>Anti Swear -> That language is not allowed! Detected word(s): " + check));
                return;
            }
        }
        formatMessage(event.getPlayer(), event.getRawMessage()).thenAccept(formattedMessage -> Audiences.server().sendMessage(MiniMessage.miniMessage().deserialize(formattedMessage)));
    }
}