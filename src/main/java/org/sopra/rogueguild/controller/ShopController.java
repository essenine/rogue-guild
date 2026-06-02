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
        this.quests.add(new Quest("Bastion Inquebrantable", "Requiere tener equipado un minimo de 40 de armadura total", 400, 0, 40));
    }

    public void start() {
        int opt = -1;
        String stringOpt = "";
        WorldEvent worldEvent = new WorldEvent(repository);
        view.eventStatus(worldEvent);
        goldRewardAcummulation = 0;
        
        do {
            view.landingPage();
            view.playerStatus(player);
           
            stringOpt = sc.nextLine();
            stringOpt = stringOpt.trim();
            
            String acceptableOptions = "01234567";
            if (!acceptableOptions.contains(stringOpt) || stringOpt.isEmpty()) {
                boolean isACorrectNumber = false;
                while (!isACorrectNumber) {
                    System.out.println("Por favor, inserta un caracter permitido: un número del 0 al 7");
                    stringOpt = sc.nextLine();
                    stringOpt = stringOpt.trim();
                    if (!stringOpt.isEmpty() && acceptableOptions.contains(stringOpt)) {
                        isACorrectNumber = true;
                    } else {
                        System.out.println("CÁRACTER NO PERMITIDO. VUELVE A INTENTARLO");
                    }
                }
            }
            opt = Integer.parseInt(stringOpt);
           
            switch (opt) {
                case 1:
                    view.displayStock(repository.getAllStock(), false);
                    break;
                case 2:
                    view.displayStock(repository.getAllStock(), true);
                    String stringItem = sc.nextLine();
                    boolean isANumber = false;

                    String numbers = "0123456789";
                    if (!numbers.contains(stringItem) || stringItem.isEmpty()) {
                        while (!isANumber) {
                            System.out.println("INSERTA UN NÚMERO");
                            stringItem = sc.nextLine();
                            if (!stringItem.isEmpty() && numbers.contains(stringItem)) {
                                isANumber = true;
                            }
                        }
                    }
                    int itemId = Integer.parseInt(stringItem);
                   
                    BuyResponse buyResponse = buyProcess(itemId);
                    view.buyResult(buyResponse);
                    break;

                case 3:
                	 List<Item> inventarioVenta = player.getInventory();

                     if (inventarioVenta.isEmpty()) {
                         System.out.println("");
                         System.out.println("[INFO] Tu inventario esta vacio. No tienes objetos para vender.");
                     } else {
                         System.out.println("");
                         System.out.println("--- TU INVENTARIO (VENTA) ---");
                     
                         for (int i = 0; i < inventarioVenta.size(); i++) {
                             Item item = inventarioVenta.get(i);
                             System.out.println("[" + (i + 1) + "] " + item.getName() + " - Cat: " + item.getCategory() + " - Valor Base: " + item.getBasePrice() + " oro");
                         }
                         System.out.println("-----------------------------");
                         System.out.print("Selecciona el ID del item que quieres vender: ");

                         try {
                             int choiceVenta = Integer.parseInt(sc.nextLine()) - 1;

                             if (choiceVenta >= 0 && choiceVenta < inventarioVenta.size()) {
                                 Item itemAVender = inventarioVenta.get(choiceVenta);
                                 sellProcess(itemAVender);
                             } else {
                                 System.out.println("[!] ID de objeto invalido. Operacion cancelada.");
                             }
                         } catch (NumberFormatException e) {
                             System.out.println("[!] Error: Introduce un numero valido.");
                         }
                     }
                	
                	///
                  
                    break;

                case 4:
                    System.out.println("");
                    System.out.println("[!] Regresas de la incursion con exito");
                    repository.refreshStock();
                    System.out.println("[INFO] El mercader ha renovado su stock con nuevos generos");
                    break;

                case 5:
                    System.out.println("");
                    System.out.println("--- TABLON DE MISIONES DISPONIBLES ---");
                    
                    int disponibles = 0;
                    for (int i = 0; i < quests.size(); i++) {
                        if (!quests.get(i).isCompleted()) {
                            disponibles++;
                        }
                    }

                    if (disponibles == 0) {
                        System.out.println("[INFO] No hay misiones disponibles. ¡Las has completado todas!");
                    } else {
                        for (int i = 0; i < quests.size(); i++) {
                            Quest q = quests.get(i);
                            if (!q.isCompleted()) {
                                System.out.println("[" + (i + 1) + "] " + q.getTitle() + " - Premio: " + q.getGoldReward() + " oro");
                                System.out.println("    Descripcion: " + q.getDescription());
                            }
                        }
                        System.out.println("--------------------------------------");
                        System.out.print("Selecciona el ID de la mision para reclamar: ");
                        
                        try {
                            int choice = Integer.parseInt(sc.nextLine()) - 1;
                            
                            if (choice >= 0 && choice < quests.size()) {
                                Quest selectedQuest = quests.get(choice);
                                
                                if (selectedQuest.isCompleted()) {
                                    System.out.println("[!] Esa mision ya no esta disponible.");
                                } else {
                                    if (selectedQuest.checkRequirement(player)) {
                                        selectedQuest.setCompleted(true);
                                        player.addGold(selectedQuest.getGoldReward());
                                        
                                        System.out.println("");
                                        System.out.println("[!] ¡MISION COMPLETADA CON EXITO!");
                                        System.out.println("[!] Has reclamado: " + selectedQuest.getTitle());
                                        System.out.println("[!] Oro obtenido: " + selectedQuest.getGoldReward() + " monedas de oro.");
                                        
                                        repository.refreshStock();
                                    } else {
                                        System.out.println("");
                                        System.out.println("[X] No cumples los requisitos para reclamar esta mision.");
                                        
                                        List<Item> requeridos = selectedQuest.getRequiredItems();
                                        
                                        if (requeridos != null && !requeridos.isEmpty()) {
                                            System.out.println("Te falta tener en el inventario:");
                                            List<Item> inventario = player.getInventory();
                                            for (int i = 0; i < requeridos.size(); i++) {
                                                Item req = requeridos.get(i);
                                                if (!inventario.contains(req)) {
                                                    System.out.println("    - " + req.getName());
                                                }
                                            }
                                        } else {
                                            System.out.println("Asegurate de tener suficiente Ataque o Armadura activa en tu menu de equipamiento.");
                                        }
                                    }
                                }
                                
                            } else {
                                System.out.println("[!] ID de mision invalido.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Error: Introduce un numero valido.");
                        }
                    }
                    break;
                    
                case 6:
                	  int actualGoldReward = 0;
                      Incursion incursion = selectIncursion();
                      if (incursion != null) {
                          if (incursion.getGoldReward() == 0) {
                              System.out.println("No has obtenido oro. Tu item de recompensa es " + incursion.getItemReward().toString());
                              player.addItem(incursion.getItemReward());
                          } else if (incursion.getItemReward() == null) {
                              System.out.println("No tienes item. Tu recompensa de oro es de " + incursion.getGoldReward() + " oro.");
                              actualGoldReward = incursion.getGoldReward();
                              goldRewardAcummulation += incursion.getGoldReward();
                          } else {
                              System.out.println("Has obtenido " + incursion.getGoldReward() + " de oro y el objeto " + incursion.getItemReward().toString());
                              player.addItem(incursion.getItemReward());
                              actualGoldReward = incursion.getGoldReward();
                              goldRewardAcummulation += incursion.getGoldReward();
                          }
                          validateGoldReward(actualGoldReward);
                      }
                    break;
                    
                case 7:
                    List<Item> mochilaEquipar = player.getInventory();
                    
                    if (mochilaEquipar.isEmpty()) {
                        System.out.println("");
                        System.out.println("[INFO] Tu inventario está vacío. No tienes objetos para equipar.");
                    } else {
                        System.out.println("");
                        System.out.println("--- MENU DE EQUIPAMIENTO ---");
                        for (int i = 0; i < mochilaEquipar.size(); i++) {
                            Item item = mochilaEquipar.get(i);
                            System.out.println("[" + (i + 1) + "] " + item.getName() + " (" + item.getCategory() + ")");
                        }
                        System.out.println("----------------------------");
                        System.out.print("Selecciona el ID del objeto que quieres equipar: ");
                        
                        try {
                            int choiceEquip = Integer.parseInt(sc.nextLine()) - 1;
                            if (choiceEquip >= 0 && choiceEquip < mochilaEquipar.size()) {
                                Item itemAEquipar = mochilaEquipar.get(choiceEquip);
                                equipProcess(itemAEquipar);
                            } else {
                                System.out.println("[!] ID de objeto invalido.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("[!] Error: Introduce un numero valido.");
                        }
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

    private void validateGoldReward(int actualGoldReward) {
        if (goldRewardAcummulation <= 500) {    
            player.addGold(actualGoldReward);
            System.out.println("Se ha añadido " + actualGoldReward + " oro a tu inventario");
        } else {
            System.out.println("No se puede sobrepasar de 500 monedas de oro ");
            int goldDifference = 500 - (goldRewardAcummulation - actualGoldReward);
            if (goldDifference > 0) {
                System.out.println("Tu recompensa en oro pasa de " + actualGoldReward + " a " + goldDifference + " oro");
                player.addGold(goldDifference);
                System.out.println("Se ha añadido " + goldDifference + " oro a tu inventario");
            } else {
                System.out.println("No se añade ningún oro a tu inventario");
            }
            goldRewardAcummulation = 501;
        }
    }

    private Incursion selectIncursion() {
        view.showIncursion();
        Incursion incursion = null;
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
                    incursion = generateMinorIncursion();
                    break;
                case 4:
                    System.out.println("Saliendo... incursión cancelada");
                    break;
                default :
                    System.out.println("Valor no aceptado, incursion cancelada");
                    break;
            }
        } catch(Exception e) {
            System.out.println("Solo valores del 1-4. Saliendo de incursiones...");
        }
        return incursion;
    }
    
    private Incursion generateMinorIncursion() {
        int goldReward = (((int)(Math.random() * 51)) / 5) * 5;
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
            if (item.getBasePrice() <= 50) {
                isLowValue = true;
            }
        }
        return item;
    }
    
    private Incursion generateLootIncursion() {
        int goldReward = (((int)(Math.random() * 401) + 50) / 5) * 5;
        int random0to1 = (int)(Math.random() * 2);
        Item itemReward = null;
        if (random0to1 == 1) {
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
            if (item.getCategory().equals(armor) && item.getBasePrice() <= 100) {
                isLowValue = true;
            } else if (item.getCategory().equals(boots) && item.getBasePrice() <= 50) {
                isLowValue = true; 
            } else if (item.getCategory().equals(helmet) && item.getBasePrice() <= 70) {
                isLowValue = true;  
            } else if (item.getCategory().equals(weapon) && item.getBasePrice() <= 150) {
                isLowValue = true;  
            } else if (item.getCategory().equals(potion) && item.getBasePrice() <= 25) {
                isLowValue = true;  
            }
        }
        return item;
    }

    private Incursion generateCoquerIncursion() {
        int random0to1 = (int)(Math.random() * 2);
        int goldReward;
        if (random0to1 == 0) {
            goldReward = 0;
        } else {
            goldReward = (((int)(Math.random() * 51)) / 5) * 5;
        }
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
            if (item.getCategory().equals(armor) && item.getBasePrice() > 100) {
                isHighValue = true;
            } else if (item.getCategory().equals(boots) && item.getBasePrice() > 50) {
                isHighValue = true; 
            } else if (item.getCategory().equals(helmet) && item.getBasePrice() > 70) {
                isHighValue = true;  
            } else if (item.getCategory().equals(weapon) && item.getBasePrice() > 150) {
                isHighValue = true;  
            } else if (item.getCategory().equals(potion) && item.getBasePrice() > 25) {
                isHighValue = true;  
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
        double precioCalculado = item.getBasePrice() * 0.8;
        int precioVentaFinal = (int) (Math.round(precioCalculado / 5.0) * 5);

        player.removeItem(item);
        player.addGold(precioVentaFinal);

        int nuevoIdStock = 1;
        while (repository.getAllStock().containsKey(nuevoIdStock)) {
            nuevoIdStock++;
        }
        repository.getAllStock().put(nuevoIdStock, item);
      
        System.out.println("");
        System.out.println("Has vendido " + item.getName() + " por " + precioVentaFinal + " monedas");
    }
    
    private void equipProcess(Item item) {
        ItemCategory cat = item.getCategory();

        if (cat == ItemCategory.WEAPON) {
            List<Item> armasEquipadas = player.getEquippedWeapons();

            if (armasEquipadas.size() < 2) {
                player.removeItem(item);
                armasEquipadas.add(item); 
                System.out.println("[!] Has equipado el arma: " + item.getName());
            } else {
                Item arma1 = armasEquipadas.get(0);
                Item arma2 = armasEquipadas.get(1);

                int dmg1 = ((Weapon) arma1).getDamage();
                int dmg2 = ((Weapon) arma2).getDamage();

                Item armaReemplazo;
                int indiceReemplazo;

                if (dmg1 < dmg2) {
                    armaReemplazo = arma1;
                    indiceReemplazo = 0;
                } else if (dmg2 < dmg1) {
                    armaReemplazo = arma2;
                    indiceReemplazo = 1;
                } else {
                    armaReemplazo = arma1;
                    indiceReemplazo = 0;
                }

                armasEquipadas.remove(indiceReemplazo);
                player.addItem(armaReemplazo);

                player.removeItem(item);
                armasEquipadas.add(item);

                System.out.println("[!] Ranuras llenas. Desequipado '" + armaReemplazo.getName() + "' (Daño: " + ((Weapon) armaReemplazo).getDamage() + ") para equipar '" + item.getName() + "' (Daño: " + ((Weapon) item).getDamage() + ").");
            }
        } else {
            Item equipadoActual = null;

            if (cat == ItemCategory.ARMOR) {
                equipadoActual = player.getEquippedArmor();
                if (equipadoActual != null) player.addItem(equipadoActual); 
                player.removeItem(item); 
                player.setEquippedArmor(item);
            } else if (cat == ItemCategory.HELMET) {
                equipadoActual = player.getEquippedHelmet();
                if (equipadoActual != null) player.addItem(equipadoActual);
                player.removeItem(item);
                player.setEquippedHelmet(item);
            } else if (cat == ItemCategory.BOOTS) {
                equipadoActual = player.getEquippedBoots();
                if (equipadoActual != null) player.addItem(equipadoActual);
                player.removeItem(item);
                player.setEquippedBoots(item);
            }

            if (equipadoActual != null) {
                System.out.println("[INFO] Se ha reemplazado '" + equipadoActual.getName() + "' y ha vuelto a tu inventario");
            }
            System.out.println("[!] Has equipado con exito: " + item.getName());
        }
    }
}