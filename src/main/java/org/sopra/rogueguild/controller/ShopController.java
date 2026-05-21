package org.sopra.rogueguild.controller;

import java.util.Scanner;

import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.controller.dto.BuyResponse;

public class ShopController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final Scanner sc;

    public ShopController(Player p, ViewDisplay v, ShopRepository r) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        int opt;
        do {
            view.landingPage();
            view.playerStatus(player);
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    view.displayStock(repository.getAllStock(), true);
                    int itemId = Integer.parseInt(sc.nextLine());
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                    break;
                case 3:
                    // TODO Logic to sell and add products to stock
                    break;
                case 4:
                    // TODO Logic to ...
                    break;
                case 0:
                    view.quitMessage();
                    break;
                }
                view.pressKeyMessage();
                sc.nextLine();
        } while (opt != 0);
    }

    private BuyResponse buyProcess(int id) {
        Item item = repository.getItem(id);
        if (item == null) {
            return BuyResponse.notFound(id);
        }
        if (player.getGold() < item.getBasePrice()) {
            return BuyResponse.notEnoughGold(item, player.getGold());
        }
        player.buy(item);
        repository.removeItem(id);
        return BuyResponse.success(item);
    }

    private void sellProcess(Item item) {
        //TODO Sell process
    }
}
