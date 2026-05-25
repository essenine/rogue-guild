package org.sopra.rogueguild.controller;

import java.util.Scanner;

import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.ItemGenerator;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.event.WorldEvent;
import org.sopra.rogueguild.repository.model.Incursion;

public class ShopController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final Scanner sc;
    private int goldRewardAcummulation;
  
    public ShopController(Player p, ViewDisplay v, ShopRepository r) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.sc = new Scanner(System.in);
    }

    public void start() {
        int opt;
        WorldEvent worldEvent = new WorldEvent(repository);
    	view.eventStatus(worldEvent);
    	goldRewardAcummulation=0;
        do {
            view.landingPage();
            view.playerStatus(player);
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1:
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    view.displayStock(repository.getAllStock(), true);
                    int itemId = Integer.parseInt(sc.nextLine());
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                    break;
                case 3:
                	int actualGoldReward = 0;
                	Incursion incursion = selectIncursion();
                	if(incursion != null) {
                		if(incursion.getGoldReward() ==0) {
                    		System.out.println("No has obtenido oro. Tu item de recompensa es "+incursion.getItemReward().getName());
                    		player.addItem(incursion.getItemReward());
                    	}
                    	else if( incursion.getItemReward().equals(null)) {
                    		System.out.println("No tienes item. Tu recompensa de oro es de "+incursion.getGoldReward()+ " oro.");
                    		actualGoldReward = incursion.getGoldReward();
                    		goldRewardAcummulation += incursion.getGoldReward();
                    	}
                    	else {
                    	System.out.println("Has obtenido "+incursion.getGoldReward()+" de oro y el objeto "+ incursion.getItemReward().getName());
                    	player.addItem(incursion.getItemReward());
                    	actualGoldReward = incursion.getGoldReward();
                    	goldRewardAcummulation += incursion.getGoldReward();
                    	}
                		validateGoldReward(actualGoldReward);
                	}
                	
                	
                	break;
                case 4:
                	System.out.println("");
                    System.out.println("[!] Regresas de la incursion con exito.");

                   
                    repository.refreshStock();
                    System.out.println("[INFO] El mercader ha renovado su stock con nuevos generos.");
                    break;
                case 0:
                    view.quitMessage();
                    break;
                }
                view.pressKeyMessage();
                sc.nextLine();
        } while (opt != 0);
    }

    
    
    
    private void validateGoldReward( int actualGoldReward) {
		if( goldRewardAcummulation<=500) {	
			player.addGold(actualGoldReward);
			System.out.println("Se ha añadido "+ actualGoldReward+" oro a tu inventario");
		} else {
		System.out.println("No se puede sobrepasar de 500 monedas de oro. ");
		int goldDifference = 500- (goldRewardAcummulation -actualGoldReward);
		System.out.println("Tu recompensa en oro pasa de "+actualGoldReward+" a"+ goldDifference+" oro.");
		player.addGold(goldDifference);
		System.out.println("Se ha añadido "+ goldDifference+" oro a tu inventario");
		goldRewardAcummulation=501;
		}
		
	}

	private  Incursion selectIncursion() {
    	view.showIncursion();
    	int opt = Integer.parseInt(sc.nextLine());
    	Incursion incursion= null;
    	switch(opt) {
    	case 1:
    		 incursion = generateCoquerIncursion();
    		break;
    	case 2:
    		 incursion = generateLootIncursion();
    		break;
    	case 3:
    		incursion= generateMinorIncursion();
    		break;
    		
    	case 4:
    		System.out.println("Saliendo... incursión cancelada");
    		break;
    	default :
    		System.out.println("Valor no aceptado, incursion cancelada");
    	
    		break;
    	}
    	return incursion;
   
    	
    }
    
    
    private Incursion generateMinorIncursion() {
    	int goldReward= (((int)(Math.random() * 51)) / 5) * 5;
    	Item item = generateMinorItem();
    	Incursion minorIncursion = new Incursion("minor", "Incursión mixta", goldReward, item);
    	return minorIncursion;
    }
    
    
    
    private Item generateMinorItem() {
    	Item item = null;
    	boolean isLowValue = false;
    	while (!isLowValue) {
    		 item = generateHighValueItem();
    		 if(item.getBasePrice() <= 50) {
    			 isLowValue=true;
    		 }
    	}
 
    	return item;
    }
    
    private Incursion generateLootIncursion() {
    	int goldReward= (((int)(Math.random() * 401)+50) / 5) * 5;
		ItemGenerator generator  = new ItemGenerator();
		int random0to1 = (int)(Math.random() * 2);
		Item itemReward =null;
		if(random0to1 == 1) {
			itemReward = generateLowValueItem();
		}
		Incursion conquer = new Incursion("loot", "Saqueando...", goldReward, itemReward);
    	return conquer;
    }
    
    
    
    //es de bajo valor por su tipo o por el dinero??
    private Item generateLowValueItem() {
    	Item item = null;
    	boolean isLowValue = false;
    	ItemCategory potion = ItemCategory.POTION;
    	ItemCategory boots = ItemCategory.BOOTS;
    	while (!isLowValue) {
    		 item = generateHighValueItem();
    		 if(item.getCategory().equals(potion)) {
    			 isLowValue=true;
    		 }
    		 else if(item.getCategory().equals(boots)) {
    			 isLowValue=true;
    		 }
    	}
 
    	return item;
	}

	private Incursion generateCoquerIncursion() {
		int random0to1 = (int)(Math.random() * 2);
		int goldReward;
		if (random0to1 == 0) {
			goldReward =0;
		}else {
		 goldReward= (((int)(Math.random() * 51)) / 5) * 5;
		}
		ItemGenerator generator  = new ItemGenerator();
		Item itemReward = generateHighValueItem();
		Incursion conquer = new Incursion("conquer", "Conquistando... ", goldReward, itemReward);
    	return conquer;
    }
    
    
    private Item generateHighValueItem() {
    	Item item = null;
    	boolean isHighValue = false;
    	ItemCategory armor = ItemCategory.ARMOR;
    	ItemCategory helmet = ItemCategory.HELMET;
    	ItemCategory weapon = ItemCategory.WEAPON;
    	while (!isHighValue) {
    		 item = generateHighValueItem();
    		 if(item.getCategory().equals(armor)) {
    			 isHighValue=true;
    		 }
    		 else if(item.getCategory().equals(helmet)) {
    			 isHighValue=true;
    		 }
    		 else if(item.getCategory().equals(weapon)) {
    			 isHighValue=true;
    		 }
    	}
    	
    	
    	return item;
    }
    
    
    
    
    
    private BuyResponse buyProcess(int id) {
        Item item = repository.getItem(id);
        if (item == null) {
            return BuyResponse.notFound(id);
        }
        if (player.getGold() < item.getBasePrice()) {
            return BuyResponse.notEnoughGold(item, player.getGold());
        }
        player.buy(item);
        repository.removeItem(id);
        return BuyResponse.success(item);
    }

    private void sellProcess(Item item) {
        //TODO Sell process
    }
    
   
}
