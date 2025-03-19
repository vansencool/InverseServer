package net.vansen.chat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AdChecker {

    public static boolean check(final String message) {
        return Pattern.compile("(<[^:]*>)|([\\w+]+://)|([\\w-]+\\.)*[\\w-]+:/(?:[^/]*([/?=&#.]?[\\w-]+)*/?)?", Pattern.CASE_INSENSITIVE).matcher(message).find();
    }

    public static String get(final String url) {
        try (InputStream inputStream = new URL(url).openStream();
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {

            if (scanner.hasNext()) {
                return scanner.useDelimiter("\\A").next();
            } else {
                throw new IllegalStateException("No content found in the URL: " + url);
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to read content from URL: " + url, e);
        }
    }
}
