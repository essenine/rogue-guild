package org.sopra.rogueguild.repository.model;

public class Helmet extends Item{
	private int protection; 
	public Helmet(String name, int price, int protection) {
		 super(name, price, ItemCategory.HELMET);
		    this.protection = protection;
	}

}
