package org.sopra.rogueguild.repository.model;

public class Helmet extends Item{
	private int protection; 
	public Helmet(String name, int price, int protection) {
		 super(name, price, ItemCategory.HELMET);
		    this.protection = protection;
	}

	public int getProtection() {
        return this.protection;
    }
	
	@Override
	public String toString() {
		return super.toString()+" [ protección : "+protection+ " ]";
	}
}
