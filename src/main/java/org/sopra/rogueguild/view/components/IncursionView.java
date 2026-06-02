package org.sopra.rogueguild.view.components;

import static org.sopra.rogueguild.view.utils.Ansi.PURP;
import static org.sopra.rogueguild.view.utils.Ansi.RED;
import static org.sopra.rogueguild.view.utils.Ansi.c;

import java.io.PrintStream;

import org.sopra.rogueguild.view.utils.Ansi;

public class IncursionView {

	private final PrintStream out;

    public IncursionView(PrintStream out) { this.out = out; }

    public void incursionPage() {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
       out.println("|| |                    INCURSIONES                  | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |  [1] Incursión de conquista                   | ||");
        out.println("|| |  [2] Incursión de saqueo                      | ||");
        out.println("|| |  [3] Incursión menor                          | ||");
        out.println("|| |" + c(Ansi.GRAY, "  [0] Salir                                    ") + "| ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }
}
