package org.sopra.rogueguild;

import org.sopra.rogueguild.controller.ShopController;
import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;

public class App {
    public static void main(String[] args) {
        ShopRepository repository = new ShopRepository();
        ViewDisplay view = new ViewDisplay();

        Player player = new Player( "Iñigo Montolla", 500);

        ShopController controller = new ShopController(player, view, repository);
        controller.start();
    }
}