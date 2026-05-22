package org.sopra.rogueguild.event;

import java.util.Map;

import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Armor;
import org.sopra.rogueguild.repository.model.Boots;
import org.sopra.rogueguild.repository.model.Helmet;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.Potion;
import org.sopra.rogueguild.repository.model.Weapon;

public class WorldEvent {
	private String description; 
	private ShopRepository repository;
	

	
	public WorldEvent(ShopRepository repository) {
		this.repository = repository;
		String prefix="";
		int priceAlteration = priceAlteration();
		String suffix = suffixBasedOnPrice(priceAlteration);
		int random1to2 = ((int) (Math.random() * 2 ) +1);
		if (random1to2 == 1) {
			prefix="TODAS LA MERCANCÍAS";
			changePriceGlobally(priceAlteration);
		}
		else if(random1to2 ==2 ) {
			ItemCategory category =randomCategory();
			prefix = prefixBasedOnCategory(category);
			changePriceCategory(priceAlteration, category);
		}
		description=prefix+suffix;
		
	}
	
	private int calculateNewPriceDiscount(int basePrice,int discount ) {
		discount = Math.abs(discount);
		double transformedDiscount = discount;
		transformedDiscount = transformedDiscount/100;
		double price = basePrice -(basePrice*transformedDiscount);
		int newPrice = (int)price;
		return newPrice;
	}
	
	private int calculateNewPriceAddition(int basePrice, int addition) {
		double transformedAddition = addition;
		transformedAddition = transformedAddition/100;
		double price = basePrice+(basePrice*transformedAddition);
		int newPrice = (int)price;
		return newPrice;
	}
	
	private void changePriceGlobally(int priceAlteration) {
		boolean isADiscount = false;
		if(priceAlteration<0) {
			isADiscount=true;
		}
	     Map<Integer, Item> actualStock = repository.getAllStock();
	     for (Map.Entry<Integer, Item> entry : actualStock.entrySet()) {
			Integer key = entry.getKey();
			Item val = entry.getValue();
			int basePrice = val.getBasePrice();
			int newPrice;
			if(isADiscount) {
				newPrice= calculateNewPriceDiscount(basePrice, priceAlteration);
			}else {
				newPrice = calculateNewPriceAddition(basePrice, priceAlteration);
			}
			val.setPrice(newPrice);
			entry.setValue(val);
		}
	}
	
	private void changePriceCategory(int priceAlteration, ItemCategory category) {
		  Map<Integer, Item> actualStock = repository.getAllStock();
			boolean isADiscount = false;
			if(priceAlteration<0) {
				isADiscount=true;
			}
		  for (Map.Entry<Integer, Item> entry : actualStock.entrySet()) {
				Integer key = entry.getKey();
				Item val = entry.getValue();
				if(val.getCategory().equals(category)) {
				int basePrice = val.getBasePrice();
				int newPrice=0;
				if(isADiscount) {
					newPrice= calculateNewPriceDiscount(basePrice, priceAlteration);
				}else {
					newPrice = calculateNewPriceAddition(basePrice, priceAlteration);
				}
				val.setPrice(newPrice);
				entry.setValue(val);
				}
			}
	}
	
	
	private int priceAlteration() {
		int alteration= (((int)(Math.random() * 190) + (-90)) / 5) * 5;
		while(alteration==0) {
			alteration= (((int)(Math.random() * 190) + (-90)) / 5) * 5;
		}
		return alteration; 
	}
	
	private ItemCategory randomCategory() {
		ItemCategory category = null;
		int random1to5 = ((int) (Math.random() * 5 ) +1);
		switch (random1to5) {
		case 1: {
			category = ItemCategory.WEAPON;
		}
		case 2: {
			category = ItemCategory.ARMOR;
		}
		case 3: {
			category = ItemCategory.POTION;
		}
		case 4: {
			category = ItemCategory.HELMET;
		}
		case 5: {
			category = ItemCategory.BOOTS;
		}		
	}
		return category;
	}
	
	public String suffixBasedOnPrice(int priceAlteration) {
		String suffix="";
		if(priceAlteration>0) {
			suffix=" han aumentado su precio en un "+priceAlteration+"%";
		} else {
			suffix=" están de rebajas! Tienen un descuento del "+priceAlteration+"%";
		}
		return suffix;
		
	}
	
	public String prefixBasedOnCategory(ItemCategory category) {
		String prefix="";
		
		switch (category) {
		case WEAPON: {
			prefix="Las armas";
			 break;
		}
		case ARMOR: {
			prefix="Las armaduras";
			break;
		}
		case POTION:{
			prefix="Las pociones";
			break;
		}
		case HELMET:{
			prefix="Los cascos";
			break;
		}
		case BOOTS:{
			prefix="Las botas";
			break;
		}
		}
		return prefix;
		
	}
	
	public String getDescription() {
		return description; 
	}
	

}
