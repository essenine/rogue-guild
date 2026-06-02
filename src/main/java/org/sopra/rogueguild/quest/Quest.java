package org.sopra.rogueguild.quest;

import java.util.ArrayList;
import java.util.List;

import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;

public class Quest {
	private String description;
	private int goldReward;
	private List<Item> requiredItems;
	private boolean isCompleted = false;
	private String title;
	
	// nuevos atributos para la validacion de estadisticas 
	private int requiredDamage = 0;
	private int requiredDefense = 0;
	private boolean isEquipmentQuest = false; // diferencia entre un tipo de mision u otro, si es true son el tipo de mision que requiere un daño o una defensa (hu 15). si es false, es una mision simple
	

	public Quest(String title, String description, int goldReward, List<Item> requiredItems) {
		super();
		this.title = title;
		this.description = description;
		if((this.goldReward/5) == 0 ) {
			this.goldReward = goldReward;
		} else {
			this.goldReward = (int) (Math.round(goldReward / 5.0) * 5);
		}
		this.requiredItems = requiredItems;
	}

	// nuevo constructor para hu 15
	public Quest(String title, String description, int goldReward, int requiredDamage, int requiredDefense) {
	    super();
	    this.title = title;
	    this.description = description;
	    this.goldReward = (int) (Math.round(goldReward / 5.0) * 5);
	    this.requiredDamage = requiredDamage;
	    this.requiredDefense = requiredDefense;
	    this.requiredItems = new ArrayList<>(); 
	    this.isEquipmentQuest = true; 
	}

	
	public boolean checkRequirement(Player p) {
		// si el interruptor es true, valida lo que el jugador lleva equipado
		if (this.isEquipmentQuest) {
			return p.getTotalEquippedDamage()>=this.requiredDamage &&  p.getTotalEquippedDefense()> this.requiredDefense;
		}
		
		// si no se ejecuta el codigo de una mision normal que requiere algun objeto de inventario
		boolean checksRequirement = false;
		List<Item> inventory = p.getInventory();
		if(inventory.containsAll(requiredItems)) {
			checksRequirement = true;
		}
		return checksRequirement;
	}
	
	// completed pasa a true cuando el player completa la mision y no se puede volver
	// a jugar la mision.
	
	public String getDescription() {
		return description;
	}
	public int getGoldReward() {
		return goldReward;
	}
	public List<Item> getRequiredItems() {
		return requiredItems;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
}