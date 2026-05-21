package org.sopra.rogueguild.repository.model;

import java.util.ArrayList;
import java.util.HashMap;

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
		/*ARMOR: entre 50 y 200

BOOTS: entre 20 y 100

HELMET: entre 20 y 150

WEAPON: entre 100 y 300

POTION: entre 10 y 40
		 * 
		 * */
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
		default:
			generatedPrice=0;
		}
		return generatedPrice;
	}
	
	
	public Item generate() {
		
		int randomNumberPrefixType = ((int) (Math.random() * 5) +1)-1;
		int randomNumberSuffixType = ((int) (Math.random() *4 ) +1)-1;
		int random0to4 = ((int) (Math.random() *5 ) +1)-1;
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
		Item item = new Item(generatedName, price, category) {};
		return item;
	}
}
