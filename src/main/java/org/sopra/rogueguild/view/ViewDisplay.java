package org.sopra.rogueguild.view;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.event.WorldEvent;
import org.sopra.rogueguild.view.components.BannerView;
import org.sopra.rogueguild.view.components.BuyResultView;
import org.sopra.rogueguild.view.components.EventView;
import org.sopra.rogueguild.view.components.IncursionView;
import org.sopra.rogueguild.view.components.MessageView;
import org.sopra.rogueguild.view.components.PlayerView;
import org.sopra.rogueguild.view.components.StockView;

public class ViewDisplay {
    private final BannerView banner;
    private final MessageView messages;
    private final PlayerView playerView;
    private final StockView stockView;
    private final BuyResultView buyResultView;
    private final EventView eventView;
    private final IncursionView incursionView;

    public ViewDisplay() {
        this(System.out, 59);
    }

    public ViewDisplay(PrintStream out, int width) {
        this.banner = new BannerView(out);
        this.messages = new MessageView(out, width);
        this.playerView = new PlayerView(out);
        this.stockView = new StockView(out);
        this.buyResultView = new BuyResultView(messages);
        this.eventView = new EventView(out);
        this.incursionView = new IncursionView(out);
    }

    public void landingPage() { banner.landingPage(); }
    public void showMessage(String msg) { messages.showMessage(msg); }
    public void pressKeyMessage() { messages.pressKeyMessage(); }
    public void quitMessage() { messages.quitMessage(); }
    public void showPrompt(String prompt) { messages.showPrompt(prompt); }

    public void playerStatus(Player player) { playerView.playerStatus(player); }
    public void eventStatus(WorldEvent event) {
    	eventView.eventStatus(event);
    }
    public void displayStock(Map<Integer, Item> itemMap, boolean isInPurchaseProcess) {
        stockView.displayStock(itemMap, isInPurchaseProcess);
    }

    public void buyResult(BuyResponse r) {
        buyResultView.show(r);
    }
    
    public void showIncursion() {
    	incursionView.incursionPage();
    }
    
    public void displayPlayerInventory(List<Item> inventory) {
        System.out.println("--- TU INVENTARIO DE OBJETOS ---");
        
       
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            
            int id = i + 1;
            
            
            System.out.println("[" + id + "] " + item.getName() + " - " + item.getPrice() + " oro");
        }
        System.out.println("--------------------------------");
    }
}
