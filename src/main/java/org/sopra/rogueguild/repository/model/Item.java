package org.sopra.rogueguild.repository.model;

public abstract class Item {
    private String name;
    private int price;
    private ItemCategory category;

    public Item(String name, int price, ItemCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public String toString() { return name + " (" + price + " oro)"; }
}
