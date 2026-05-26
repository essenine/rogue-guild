package org.sopra.rogueguild.quest;

import java.util.List;

import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.Player;

public class Quest {
	private String description;
	private int goldReward;
	private List<Item> requiredItems;
	private boolean isCompleted = false;
	
	
	
	
	public Quest(String description, int goldReward, List<Item> requiredItems) {
		super();
		this.description = description;
		if((this.goldReward/5) == 0 ) {
			this.goldReward = goldReward;
		} else {
			goldReward = (this.goldReward / 5)*5;
		}
		this.requiredItems = requiredItems;
	}


	public boolean checkRequirement(Player p) {
		boolean checksRequirement = false;
		List<Item> inventory = p.getInventory();
		if(inventory.containsAll(requiredItems)) {
			checksRequirement = true;
		}
		return checksRequirement;
		
	}
	
	
	//completed pasa a true cuando el player completa la mision y no se puede volver
	//a jugar la mision.
	
	
	
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
	
	
	
}
