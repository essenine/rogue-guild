package org.sopra.rogueguild.repository.model;
import java.util.ArrayList;
import java.util.List;
public class Player {
    private String name;
    private int gold;
    private List<Item> inventory = new ArrayList<>();
    public Player(String name, int gold) {
        this.name = name;
        this.gold = gold;
    }
    public String getName() { return name; }
    public int getGold() { return gold; }
    public void buy(Item item) { this.gold -= item.getPrice(); this.inventory.add(item); }
    
    public void removeItem(Item item) {
       
            boolean isRemoved = this.inventory.remove(item);
            
            if (isRemoved == false) {
                String itemName;
                
      
                if (item != null) {
                    itemName = item.getName();
                } else 
                {
                    itemName = "objeto desconocido";
                }
                
                System.out.println("[AVISO] El objeto '" + itemName + "' no se pudo eliminar porque no esta en el inventario.");
            }
        
    }
    
    
}
