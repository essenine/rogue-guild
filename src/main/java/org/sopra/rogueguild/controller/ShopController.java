package org.sopra.rogueguild.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.sopra.rogueguild.repository.ShopRepository;
import org.sopra.rogueguild.repository.model.Item;
import org.sopra.rogueguild.repository.model.ItemCategory;
import org.sopra.rogueguild.repository.model.ItemGenerator;
import org.sopra.rogueguild.repository.model.Player;
import org.sopra.rogueguild.repository.model.Weapon;
import org.sopra.rogueguild.view.ViewDisplay;
import org.sopra.rogueguild.controller.dto.BuyResponse;
import org.sopra.rogueguild.event.WorldEvent;
import org.sopra.rogueguild.quest.Quest;
import org.sopra.rogueguild.repository.model.Armor;
import org.sopra.rogueguild.repository.model.Boots;
import org.sopra.rogueguild.repository.model.Helmet;
import org.sopra.rogueguild.repository.model.Incursion;

public class ShopController {
    private final Player player;
    private final ViewDisplay view;
    private final ShopRepository repository;
    private final Scanner sc;
    private int goldRewardAcummulation;
    private final List<Quest> quests;
  
    public ShopController(Player p, ViewDisplay v, ShopRepository r) {
        this.player = p;
        this.view = v;
        this.repository = r;
        this.sc = new Scanner(System.in);
        
        this.quests = new ArrayList<Quest>();

      
        List<Item> reqDanza = new ArrayList<Item>();
        reqDanza.add(new Weapon("Daga de las Sombras", 100, 15));
        reqDanza.add(new Weapon("Espada Corta", 150, 20));
        this.quests.add(new Quest("Danza de muerte", "Requiere tener en el inventario: Daga de las Sombras y Espada Corta", 150, reqDanza));

       
        List<Item> reqFenix = new ArrayList<Item>();
        reqFenix.add(new Weapon("Espada del Alba", 200, 25));
        reqFenix.add(new Helmet("Casco de Hierro", 80, 10));
        reqFenix.add(new Armor("Coraza de Acero", 300, 30));
        reqFenix.add(new Boots("Botas de Cuero", 50, 5));
        this.quests.add(new Quest("Caballero del Fénix", "Requiere tener el set completo: Espada del Alba, Casco de Hierro, Coraza de Acero y Botas de Cuero", 300, reqFenix));
    }

    public void start() {
        int opt=-1;
        WorldEvent worldEvent = new WorldEvent(repository);
    	view.eventStatus(worldEvent);
    	goldRewardAcummulation=0;
    	
        do {
            view.landingPage();
            view.playerStatus(player);
           
            try {
            opt = Integer.parseInt(sc.nextLine());
            }catch(IllegalArgumentException e) {
            	boolean isACorrectNumber = false;
            	
            	while(!isACorrectNumber) {
            		try {
            		System.out.println("Por favor, inserta un caracter permitido: un número del 0 al 7");
            		opt = Integer.parseInt(sc.nextLine());
            		if(opt>=0 && opt<=7) {
            			isACorrectNumber=true;
            		}}catch(IllegalArgumentException e1) {
            			System.out.println("Cáracter no permitido.");
            		}
            		
            	}
            }
            switch (opt) {
                case 1:
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    view.displayStock(repository.getAllStock(), true);
                    int itemId= -1;
                    try {
                    	  itemId = Integer.parseInt(sc.nextLine());
                        }catch(IllegalArgumentException e) {
                        	boolean isACorrectNumber = false;
                        	
                        	while(!isACorrectNumber) {
                        		try {
                        		System.out.println("Por favor, inserta un caracter permitido");
                        		itemId = Integer.parseInt(sc.nextLine());
                        			isACorrectNumber=true;
                        	}catch(IllegalArgumentException e1) {
                        			System.out.println("Cáracter no permitido.");
                        		}
                        	}
                        }
                    
                   
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                    break;
                    //SELL PROCESS, ENSEÑAR BIEN INVENTARIO 
                case 3:
                	//case 7 ESTE TIENE QUE SER EL CASE 7, CAMBIAR ??
                	//pQUEDA PENDEINTE CAMBIAR VISTA at. Sara
                	
                	//prueba 2
                	//pruebafdfds
                	
                	break;
                case 4:
                	System.out.println("");
                    System.out.println("[!] Regresas de la incursion con exito");

                   
                    repository.refreshStock();
                    System.out.println("[INFO] El mercader ha renovado su stock con nuevos generos");
                    break;
                case 5:
                    System.out.println("");
                    System.out.println("--- TABLON DE MISIONES ---");
                    for (int i = 0; i < quests.size(); i++) {
                        Quest q = quests.get(i);
                        String status = q.isCompleted() ? "[COMPLETADA]" : "[DISPONIBLE]";
                        System.out.println("[" + (i + 1) + "] " + q.getTitle() + " " + status + " - Premio: " + q.getGoldReward() + " oro");
                        System.out.println("    Descripcion: " + q.getDescription());
                    }
                    System.out.println("--------------------------");
                    System.out.print("Selecciona una mision para intentar: ");
                    try {
                    	
                        int choice = Integer.parseInt(sc.nextLine()) - 1;
                        if (choice >= 0 && choice < quests.size()) {
                            Quest selectedQuest = quests.get(choice);
                            
                            if (selectedQuest.isCompleted()) {
                                System.out.println("[!] Esta mision ya la has completado y no puedes repetirla");
                            } else
                            {
                                
                                if (selectedQuest.checkRequirement(player)) {
                                    System.out.println("");
                                    System.out.println("[!] ¡Exito! Has completado la mision: " + selectedQuest.getTitle());
                                    System.out.println("[!] Recompensa obtenida: " + selectedQuest.getGoldReward() + " monedas de oro");
                                    
                                    player.addGold(selectedQuest.getGoldReward());
                                    selectedQuest.setCompleted(true);
                                    repository.refreshStock(); 
                                } else {
                                    System.out.println("");
                                    System.out.println("[!] No tienes los objetos exactos requeridos en tu inventario");
                                }
                            }
                        } else {
                            System.out.println("[!] ID de mision invalido");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Error: Introduce un numero valido");
                    }
                    break;
                case 7:
                	int actualGoldReward = 0;
                	Incursion incursion = selectIncursion();
                	if(incursion != null) {
                		if(incursion.getGoldReward() ==0) {
                    		System.out.println("No has obtenido oro. Tu item de recompensa es "+incursion.getItemReward().getName());
                    		player.addItem(incursion.getItemReward());
                    	}
                    	else if( incursion.getItemReward() ==null) {
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
                    
                case 0:
                    view.quitMessage();
                    break;
                default:
                	System.out.println("Opción no reconocida, elige otra opción.");
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
		if( goldDifference >0 ) {
		System.out.println("Tu recompensa en oro pasa de "+actualGoldReward+" a"+ goldDifference+" oro.");
		player.addGold(goldDifference);
		System.out.println("Se ha añadido "+ goldDifference+" oro a tu inventario");
		} else {
			System.out.println("No se añade ningún oro a tu inventario");
		}
		goldRewardAcummulation=501;
		}
		
	}

	private  Incursion selectIncursion() {
    	view.showIncursion();
    	Incursion incursion= null;
    	try {
    	int opt = Integer.parseInt(sc.nextLine());
    	
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
    	}
    	catch(Exception e) {
    		System.out.println("Solo valores del 1-4. Saliendo de incursiones...");
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
    	ItemGenerator generator = new ItemGenerator();
    	
    	boolean isLowValue = false;
    	while (!isLowValue) {
    		 item = generator.generate(repository);
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
    
    
    
  
    private Item generateLowValueItem() {
    	Item item = null;
    	ItemGenerator generator = new ItemGenerator();
    	boolean isLowValue = false;
    	ItemCategory armor = ItemCategory.ARMOR;
    	ItemCategory helmet = ItemCategory.HELMET;
    	ItemCategory weapon = ItemCategory.WEAPON;
    	ItemCategory boots = ItemCategory.BOOTS;
    	ItemCategory potion = ItemCategory.POTION;
    	while (!isLowValue) {
    		 item = generator.generate(repository);
    		 if(item.getCategory().equals(armor) && item.getBasePrice() <=100) {
    		isLowValue=true;
    	} else if(item.getCategory().equals(boots) && item.getBasePrice() <=50) {
    		isLowValue=true; 
    	} else if (item.getCategory().equals(helmet) && item.getBasePrice() <= 70 ) {
    		isLowValue=true;  
    	} else if (item.getCategory().equals(weapon) && item.getBasePrice() <= 150 ) {
    		isLowValue=true;  
    	}else if (item.getCategory().equals(potion) && item.getBasePrice() <= 25 ) {
    		isLowValue=true;  }
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
    	ItemGenerator generator = new ItemGenerator();
    	boolean isHighValue = false;
    	ItemCategory armor = ItemCategory.ARMOR;
    	ItemCategory helmet = ItemCategory.HELMET;
    	ItemCategory weapon = ItemCategory.WEAPON;
    	ItemCategory boots = ItemCategory.BOOTS;
    	ItemCategory potion = ItemCategory.POTION;
    	while (!isHighValue) {
    		 item = generator.generate(repository);
    		 if(item.getCategory().equals(armor) && item.getBasePrice() >100) {
    		isHighValue=true;
    	} else if(item.getCategory().equals(boots) && item.getBasePrice() >50) {
    		isHighValue=true; 
    	} else if (item.getCategory().equals(helmet) && item.getBasePrice() > 70 ) {
    		isHighValue=true;  
    	} else if (item.getCategory().equals(weapon) && item.getBasePrice() > 150 ) {
    		isHighValue=true;  
    	}else if (item.getCategory().equals(potion) && item.getBasePrice() > 25 ) {
    		isHighValue=true;  }
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
