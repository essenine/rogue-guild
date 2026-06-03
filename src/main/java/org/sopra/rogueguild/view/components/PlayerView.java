package org.sopra.rogueguild.view.components;

import java.io.PrintStream;
import java.util.List;

import static org.sopra.rogueguild.view.utils.Ansi.*;
import org.sopra.rogueguild.repository.model.Player;

public class PlayerView {
    private final PrintStream out;

    public PlayerView(PrintStream out) { this.out = out; }

    public void playerStatus(Player player) {
        out.println();
        out.println("       +---------------------------------------------------+");
        out.println("       |                 " + c(GRAY, "ESTADO COMPRADOR") + "                  |");
        out.println("       +---------------------------------------------------+");
        out.println("       | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ |");
        out.println("       | ░    NOMBRE:        " + player.getName()+"              ░ |");
        out.println("              ORO:           " + player.getGold() + " monedas");
        out.println("              INVENTARIO:    " + printInventory(player)); 
        out.println();
    }
    
    private String printInventory(Player player) {
    	int size =player.getInventory().size(); 
    	
    	String result = "";
    	if(size == 0 ) {
    		result="Vacío...\n       | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ |";
    	}else if( size == 1) {
    		result = "1 artículo -> "+ player.getInventory().getFirst().toString();
    	} else {
    		String articles = "";
    		int j=0;
    		for(int i =0; i<player.getInventory().size();i++) {
    			j=i+1;
    			articles+= "Artículo "+j+" -> "+player.getInventory().get(i).toString()+"\n     ";
    		}
    		result = size +" artículos\n     "+articles+"\n       | ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░ |";
    	}
    	
    	return result;
    }
}
