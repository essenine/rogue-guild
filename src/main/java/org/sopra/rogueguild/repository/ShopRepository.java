package org.sopra.rogueguild.repository;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.sopra.rogueguild.repository.model.Armor;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Weapon;

public class ShopRepository {
    private Map<Integer, Item> stock;

    public ShopRepository() {
        stock = new LinkedHashMap<>();
        loadInitialStock();
    }

    private void loadInitialStock() {
        stock.put(1, new Weapon("Daga de las Sombras", 150, 10));
        stock.put(2, new Weapon("Espada del Renegado", 350, 15));
        stock.put(3, new Armor("Armadura del Sol Naciente", 200, 5));
    }

    public Item getItem(int id) {
        return stock.get(id);
    }

    public void removeItem(int id) { stock.remove(id); }

    public Map<Integer, Item> getAllStock() {
        return stock;
    }
}