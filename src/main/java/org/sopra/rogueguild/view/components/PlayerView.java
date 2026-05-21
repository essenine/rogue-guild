package org.sopra.rogueguild.view.components;

import java.io.PrintStream;

import static org.sopra.rogueguild.view.utils.Ansi.*;
import org.sopra.rogueguild.repository.model.Player;

public class PlayerView {
    private final PrintStream out;

    public PlayerView(PrintStream out) { this.out = out; }

    public void playerStatus(Player player) {
        out.println();
        out.println("    +---------------------------------------------------+");
        out.println("    |                 " + c(GRAY, "ESTADO COMPRADOR") + "                  |");
        out.println("    +--+------------------------------------------------+");
        out.println("       | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        out.println("       | ░    NOMBRE:        " + player.getName());
        out.println("       | ░    ORO:           " + player.getGold() + " monedas");
        out.println("       | ░    INVENTARIO:    Vacío"); // TODO: Implement
        out.println();
    }
}
