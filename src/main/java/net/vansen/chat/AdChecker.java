package net.vansen.chat;

import java.util.regex.Pattern;

public class AdChecker {

    public static boolean check(final String message) {
        return Pattern.compile("(<[^:]*>)|([\\w+]+://)|([\\w-]+\\.)*[\\w-]+:/(?:[^/]*([/?=&#.]?[\\w-]+)*/?)?|(?:\\s*[^ ]+)?\\.\\s*[^ ]+", Pattern.CASE_INSENSITIVE).matcher(message).find();
    }
}
