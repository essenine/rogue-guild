package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    
   
    private List<Item> equippedWeapons = new ArrayList<>(); 
    private Item equippedArmor = null;  
    private Item equippedHelmet = null; 
    private Item equippedBoots = null;  

    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;
    }

    public String getName() { return name; }
    public int getGold() { return gold; }

    public void buy(Item item) { 
        this.gold -= item.getPrice(); 
        addItem(item);
    }
    
    public void removeItem(Item item) {
        boolean isRemoved = this.inventory.remove(item);
        if (isRemoved == false) {
            String itemName = (item != null) ? item.getName() : "objeto desconocido";
            System.out.println("[AVISO] El objeto '" + itemName + "' no se pudo eliminar porque no esta en el inventario.");
        }
    }
    
    public void addGold(int amount) {
        this.gold += amount;
    }

    public List<Item> getInventory() {
        return this.inventory;
    }

    public void addItem(Item item) {
        inventory.addLast(item);
    }

   
    public List<Item> getEquippedWeapons() { return equippedWeapons; }
    public Item getEquippedArmor() { return equippedArmor; }
    public Item getEquippedHelmet() { return equippedHelmet; }
    public Item getEquippedBoots() { return equippedBoots; }

    public void setEquippedArmor(Item armor) { this.equippedArmor = armor; }
    public void setEquippedHelmet(Item helmet) { this.equippedHelmet = helmet; }
    public void setEquippedBoots(Item boots) { this.equippedBoots = boots; }
}