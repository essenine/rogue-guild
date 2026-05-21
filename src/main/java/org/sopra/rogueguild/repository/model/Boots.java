package org.sopra.rogueguild.repository.model;

public class Boots extends Item {
	private int quality; 
	public Boots(String name, int price, int quality) {
		 super(name, price, ItemCategory.BOOTS);
		    this.quality = quality;
	}
}
