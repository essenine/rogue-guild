package org.sopra.rogueguild.view.utils;

public final class Ansi {
    public static boolean enabled = true;

    public static final String R     = "\u001B[0m";
    public static final String GRAY  = "\u001B[90m";
    public static final String RED   = "\u001B[31m";
    public static final String PURP  = "\u001B[35m";

    public static String c(String color, String text) {
        if (!enabled) return text;
        return color + text + R;
    }

    private Ansi() {}
}

