package org.sopra.rogueguild.repository.model;

public abstract class Item {
    private String name;
    private int price;
    private ItemCategory category;
    private final int BASE_PRICE; 

    public Item(String name, int price, ItemCategory category) {
        this.name = name;
        this.price = price;
        BASE_PRICE = price;
        this.category = category;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public void setPrice(int price) {
    	this.price = price;
    }
    public int getBasePrice() {
    	return BASE_PRICE;
    }
    public String toString() { return name + " (" + BASE_PRICE + " oro)"; }
}
