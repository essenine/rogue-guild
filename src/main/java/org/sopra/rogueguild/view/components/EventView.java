package org.sopra.rogueguild.view.components;

import static org.sopra.rogueguild.view.utils.Ansi.GRAY;
import static org.sopra.rogueguild.view.utils.Ansi.c;

import java.io.PrintStream;

import org.sopra.rogueguild.event.WorldEvent;
import org.sopra.rogueguild.repository.model.Player;

public class EventView {
	 private final PrintStream out;

	    public EventView(PrintStream out) { this.out = out; }

	    public void eventStatus(WorldEvent worldEvent) {
	        out.println();
	        out.println("       +---------------------------------------------------------------+");
	        out.println(" 	|    " + c(GRAY, "                   ---WORLD EVENT ----") + "                    |");
	        out.println("       +---------------------------------------------------------------+");
	        out.println("   | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
	        out.println("   | ░             	Hay un nuevo evento!!!!!                       ░");
	        out.println("   |  "+worldEvent.getDescription()); 
	        out.println("   | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
	        out.println();
	    }
}
