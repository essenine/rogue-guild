package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sopra.rogueguild.controller.ShopController;
import org.sopra.rogueguild.repository.ShopRepository;

public class ItemGenerator {
	private HashMap<String, String[]> prefixes;
	private HashMap<String, String[]> suffixes;
	private String[] prefixesTypes;
	private String[] suffixesTypes;

	public ItemGenerator() {
		super();
		prefixes = new HashMap<String, String[]>();
		prefixes = generatePrefixes();
		suffixes = new HashMap<String, String[]>();
		suffixes = generateSufixes();
		prefixesTypes= new String[5];
		suffixesTypes = new String[4];
		
		prefixesTypes[0] = "WEAPON";
		prefixesTypes[1] = "ARMOR";
		prefixesTypes[2] = "BOOTS";
		prefixesTypes[3] = "HELMET";
		prefixesTypes[4] = "POTION";
		
		suffixesTypes[0] = "ELEMENTAL";
		suffixesTypes[1] = "MATERIAL";
		suffixesTypes[2] = "MYTHIC";
		suffixesTypes[3] = "TERRITORIAL";

	}

	private HashMap<String, String[]> generatePrefixes() {
		HashMap<String, String[]> prefixes = new HashMap<String, String[]>();
		String[] weapon = new String[5];
		weapon[0] = "Espada";
		weapon[1] = "Hacha";
		weapon[2] = "Daga";
		weapon[3] = "Lanza";
		weapon[4] = "Arco";
		String[] armor = new String[5];
		armor[0] = "Armadura";
		armor[1] = "Cota";
		armor[2] = "Peto";
		armor[3] = "Coraza";
		armor[4] = "Malla";
		String[] boots= new String[5];
		boots[0] = "Botas";
		boots[1] = "Grebas";
		boots[2] = "Sandalias";
		boots[3] = "Escarpines";
		boots[4] = "Botas altas";
		String[] helmet = new String[5];
		helmet[0] = "Yelmo";
		helmet[1] = "Casco";
		helmet[2] = "Celada";
		helmet[3] = "Capucha";
		helmet[4] = "Visera";
		String[] potion = new String[5];
		potion[0] = "Poción";
		potion[1] = "Elixir";
		potion[2] = "Brebaje";
		potion[3] = "Ungüento";
		potion[4] = "Tintura";
		prefixes.put("WEAPON", weapon);
		prefixes.put("ARMOR", armor);
		prefixes.put("BOOTS", boots);
		prefixes.put("HELMET", helmet);
		prefixes.put("POTION", potion);
		return prefixes;
	}
	
	private HashMap<String, String[]> generateSufixes(){
		HashMap<String, String[]> suffixes = new HashMap<String, String[]>();
		String[] elemental = new String[5];
		String[] material = new String[5];
		String[] mythic = new String[5];
		String[] territorial = new String[5];
		elemental[0] = "de fuego";
		elemental[1] = "de hielo";
		elemental[2] = "del rayo";
		elemental[3] = "de la tormenta";
		elemental[4] = "de la sombra";
		
		material[0] = "de hierro";
		material[1] = "de plata";
		material[2] = "de obsidiana";
		material[3] = "de acero rúnico";
		material[4] = "de bronce antiguo";
		
		mythic[0] = "del dragón";
		mythic[1] = "del fénix";
		mythic[2] = "del caos";
		mythic[3] = "del vacío";
		mythic[4] = "del alba";

		territorial[0] = "del norte";
		territorial[1] = "de las ruinas";
		territorial[2] = "del bosque maldito";
		territorial[3] = "de las profundidades";
		territorial[4] = "de la montaña";

		suffixes.put("ELEMENTAL", elemental);
		suffixes.put("MATERIAL", material);
		suffixes.put("MYTHIC", mythic);
		suffixes.put("TERRITORIAL", territorial);
		return suffixes;
	}

	private int generatePrice(String prefixType) {
		int generatedPrice = 0;

		switch (prefixType) {
		case "ARMOR": {
		generatedPrice = (((int)(Math.random() * 200) + 50) / 5) * 5;
		break;
		}
		case "BOOTS": {
			generatedPrice = (((int)(Math.random() * 81) + 20) / 5) * 5;
		break;
			}
		case "HELMET": {
			generatedPrice = (((int)(Math.random() * 131) + 20) / 5) * 5;	
		break;
			}
		case "WEAPON": {
			generatedPrice = (((int)(Math.random() * 201) + 100) / 5) * 5;
		break;
			}
		case "POTION": {
			generatedPrice = (((int)(Math.random() * 31) + 10) / 5) * 5;
		break;
			}
		case "OTHERS":{
			generatedPrice = (((int)(Math.random() * 51) + 250) / 5) * 5;
		}
		default:
			generatedPrice=0;
		}
		return generatedPrice;
	}
	
	private boolean itemExists(ShopRepository repository, String name) {
		boolean itemExists= false;
		Map<Integer, Item> actualStock = repository.getAllStock();
		itemExists =actualStock.containsValue(actualStock);
		Set<Entry<Integer, Item>> set = actualStock.entrySet();
		for (Entry<Integer, Item> item : set) {
			if (item.getValue().getName().equals(name)) {
				itemExists=true;
			}
		}
		return itemExists;
	}
	
	private Others generateOthersItem(ShopRepository repository) {
		Others others = null;
		int randomPrefix = ((int) (Math.random() *5 ) +1)-1;
		int randomSuffix = ((int) (Math.random() *5 ) +1)-1;
		String[] prefixes = new String[5];
		prefixes[0] = "Cuadro";
		prefixes[1] = "Colonia";
		prefixes[2] = "Pluma";
		prefixes[3] = "Botella";
		prefixes[4] = "Reloj";
		String[] suffixes = new String[5];
		suffixes[0] = "de Alaska";
		suffixes[1] = "del futuro ";
		suffixes[2] = "del Antiguo Egipto";
		suffixes[3] = "de flores silvestres";
		suffixes[4] = "que contiene secretos";
		
		String name = prefixes[randomPrefix]+suffixes[randomSuffix];
		if(!itemExists(repository, name)) {
		int price = generatePrice("OTHERS");
		others = new Others(name, price);
		}else {
			others = generateOthersItem(repository);
		}
		return others;
		
	}

	public Item generate(ShopRepository repository) {
		Item item = null; 
		int randomNumberPrefixType = ((int) (Math.random() * 5) +1)-1;
		int randomNumberSuffixType = ((int) (Math.random() *4 ) +1)-1;
		int random0to4 = ((int) (Math.random() *5 ) +1)-1;
		int fivePercentProbability = (int)(Math.random() * 100) + 1;
		
		if (fivePercentProbability <= 5) {
		 item = generateOthersItem(repository);	
		
		}
		else {
		String prefixType = prefixesTypes[randomNumberPrefixType];
		String suffixType = suffixesTypes[randomNumberSuffixType];
		
		String[] arrayGeneratedPrefix = prefixes.get(prefixType); 
		String generatedPrefix = arrayGeneratedPrefix[random0to4];
		
		random0to4 = ((int) (Math.random() *5 ) +1)-1;
		String[] arrayGeneratedSuffix = suffixes.get(suffixType);
		String generatedSuffix = arrayGeneratedSuffix[random0to4];
		
		String generatedName = generatedPrefix+" "+generatedSuffix;
		int price = generatePrice(prefixType);
		ItemCategory category = ItemCategory.valueOf(prefixType);
		int extraAttributeRandom = ((int) (Math.random() * 100 ) +1);
		
		if(!itemExists(repository, generatedName)) {
		switch (category) {
		case WEAPON: {
			 item = new Weapon(generatedName, price, extraAttributeRandom);
			 break;
		}
		case ARMOR: {
			item = new Armor(generatedName, price, extraAttributeRandom);
			break;
		}
		case POTION:{
			item = new Potion(generatedName, price, extraAttributeRandom);
			break;
		}
		case HELMET:{
			item = new Helmet(generatedName, price, extraAttributeRandom);
			break;
		}
		case BOOTS:{
			item = new Boots(generatedName, price, extraAttributeRandom);
			break;
		}
		default:
			System.out.println("Item couldn't be generated.");
			break;
		}
		} 
		else {
			item = generate(repository);
		}
		}
		return item;
	}
	
}

