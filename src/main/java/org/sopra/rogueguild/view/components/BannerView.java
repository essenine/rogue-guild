package org.sopra.rogueguild.view.components;
import static org.sopra.rogueguild.view.utils.Ansi.*;

import java.io.PrintStream;

import org.sopra.rogueguild.view.utils.Ansi;

public class BannerView {
    private final PrintStream out;

    public BannerView(PrintStream out) { this.out = out; }

    public void landingPage() {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |  " + c(RED, " ___                          ") + c(PURP, " _        _ ") + "   | ||");
        out.println("|| |  " + c(RED, "| _ \\___  __ _ _  _ ___  ") + c(PURP, " __ _(_)_ _ __| |") + "   | ||");
        out.println("|| |  " + c(RED, "|   / _ \\/ _` | || / -_) ") + c(PURP, "/ _` | | | / _` |") + "   | ||");
        out.println("|| |  " + c(RED, "|_|_\\___/\\__, |\\_,_\\___| ") + c(PURP, "\\__, |_|_|_\\__,_|") + "   | ||");
        out.println("|| |  " + c(RED, "         |___/           ") + c(PURP, "|___/            ") + "   | ||");
        out.println("|| |                                               | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |  [1] Ver productos de la tienda               | ||");
        out.println("|| |  [2] Comprar un producto                      | ||");
        out.println("|| |" + c(Ansi.GRAY, "  [0] Salir                                    ") + "| ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }
}
