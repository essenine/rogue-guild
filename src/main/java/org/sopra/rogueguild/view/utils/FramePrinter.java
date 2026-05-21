package org.sopra.rogueguild.view.utils;

import java.io.PrintStream;

public class FramePrinter {
    private final PrintStream out;
    private final int width;

    public FramePrinter(PrintStream out, int width) {
        this.out = out;
        this.width = width;
    }

    public void box(String message) {
        String top    = "  " + "_".repeat(width - 2);
        String topIn  = " /  " + "_".repeat(width - 6) + "  \\";
        String bottom = " \\" + "_".repeat(width - 2) + "/";

        out.println(top);
        out.println(topIn);
        out.println("|| ");
        out.println("||  " + message);
        out.println("|| ");
        out.println(bottom);
    }

    public void line(String s) {
        out.println(s);
    }
}
