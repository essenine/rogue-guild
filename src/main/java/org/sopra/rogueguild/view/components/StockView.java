package org.sopra.rogueguild.view.components;

import java.io.PrintStream;
import java.util.Map;

import org.sopra.rogueguild.repository.model.Item;

public class StockView {
    private final PrintStream out;

    public StockView(PrintStream out) { this.out = out; }

    public void displayStock(Map<Integer, Item> itemMap, boolean inPurchase) {
        out.println("  ___________________________________________________");
        out.println(" /  _______________________________________________  \\");
        out.println("|| /                                               \\ ||");
        out.println("|| |           INVENTARIO DE LA TIENDA             | ||");
        out.println("|| | ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ | ||");
        out.println("|| |                                               | ||");

        itemMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> {
                    int id = e.getKey();
                    Item item = e.getValue();
                    if (inPurchase) {
                        out.printf("|| |  [%d] %-28s %4d oro    | ||%n",
                                id, item.getName(), item.getPrice());
                    } else {
                        out.printf("|| |  [%s] %-28s %4d oro    | ||%n",
                                "-", item.getName(), item.getPrice());
                    }
                });

        out.println("|| |                                               | ||");
        out.println("|| \\_______________________________________________/ ||");
        out.println(" \\___________________________________________________/");
    }
}
