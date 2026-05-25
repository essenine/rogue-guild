package org.sopra.rogueguild.repository.model;

public class Potion extends Item{
	  private int damage;

	  public Potion(String name, int price, int amage) {
	    super(name, price, ItemCategory.ARMOR);
	    this.damage = damage;
	  }
}
