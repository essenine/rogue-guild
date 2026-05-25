package org.sopra.rogueguild.repository.model;

public class Incursion {
   
    private int goldReward;
    private Item itemReward;
    private String description;
    private String shortName;

    
    public Incursion(String shortName, String description, int goldReward, Item itemReward) {
        this.shortName = shortName;
        this.description = description;
      
        this.goldReward = (int) (Math.round(goldReward / 5.0) * 5);
        this.itemReward = itemReward;
    }

    
    public Incursion(String shortName, String description, Item itemReward) {
        this.shortName = shortName;
        this.description = description;
        this.goldReward = 0; 
        this.itemReward = itemReward;
    }

    
    public Incursion(String shortName, String description, int goldReward) {
        this.shortName = shortName;
        this.description = description;
    
        this.goldReward = (int) (Math.round(goldReward / 5.0) * 5);
        this.itemReward = null; 
    }
    
    public int getGoldReward() { 
    	return goldReward; 
    	}
   
    public Item getItemReward() {
    	return itemReward; 
    	}
    
    public String getDescription() { 
    	return description; 
    	
    }
   
    public String getShortName() {
    	return shortName; 
    	}
}