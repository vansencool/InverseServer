package net.vansen.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SwearList {
    public static ArrayList<String> list;

    static {
        list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(AdChecker.get("https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/profanity_list.txt")).split("\n")));
    }
}