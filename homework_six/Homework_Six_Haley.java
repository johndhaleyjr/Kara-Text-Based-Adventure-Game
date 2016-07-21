/**
 * Main for Homework Six that creates a text-based adventure game.
 * Explore the fel-corrupted tower of Karazhan, battling monsters
 * and pwoering up your spells with new books, weapons, and gear.
 * 
 * TODO:
 *      Add implementation of attack.
 *      Add new classes for every monster.
 * 
 * Author: John Haley
 * Last Revised: 20 July 2016
 * Assignment: Homework Six
 * Class: Homework_Six_Haley
**/
import java.util.Random;
import java.util.Scanner;

import java.util.InputMismatchException;
 
public class Homework_Six_Haley{
    private static char north = 'n';
    private static char south = 's';
    private static char east = 'e';
    private static char west = 'w';
    private static int oldCoords;
    private static boolean isLoss = false;
    private static Player player;
    private static Room map;
    private static Combat combat;
    private static Treasure treasure;
    private static EventType eventType;
    private static Scanner input;
     
    public static void main(String[] args){
        //Create new objects
        input = new Scanner(System.in);
        eventType = EventType.NONE;
        
        //Welcome user
        String playerName = welcomeKB();
        
        //Create objects
        player = new Player(playerName);
        map = new Room(north, south, east, west);
        
        //While Player is not leaving
        char choiceChar = 's';
            while(player.getCoords() != 36){
                oldCoords = player.getCoords();
                
                //Print room
                System.out.println(map.getRoom(player.getCoords()));
                
                //Ask for directions
                playerMove();
                eventType = map.getEvent(player.getCoords());
                goToEvent(eventType);
            }
    }//end main
    
    private static EventType goToEvent(EventType onEvent){
        if(onEvent == EventType.COMBAT){
            combatEvent();
        }
        else if(onEvent == EventType.TREASURE){
            treasureEvent();
        }
        else if(onEvent == EventType.UPGRADE){
            readBook();
        }
        else if(onEvent == EventType.RESTAREA){
            restUp();
        }
        onEvent = EventType.NONE;
        return onEvent;
    }
    
    private static void combatEvent(){
        //Variable initialization.
        combat = new Combat(player);
        boolean loopStatus = true;
        int choice = 0;
        System.out.printf("\n\n");
        
        while((loopStatus == true)||(player.getHealth() > 0)) {
            try{
                combat.checkStatus();
                //Change loop status
                loopStatus = combat.getIsWin();
                
                //Print health and mana
                combat.printStats();
                
                //Prints info to choose attack.
                combat.printMenu();
                
                //Grab choice from player
                choice = input.nextInt();
                //Give player the choices and enacts them
                combat.menuExecute(choice);
                //Allows monster to hit.
                combat.monsterHit();
                
                combat.spellChecker();//Depending on player status, does what it needs.
                combat.checkStatus();//Recheck to help end loop.
                loopStatus = combat.getIsWin();//Recheck to help end loop.
                player = combat.updatePlayer();
                //If evoCD is over 0, increment one down.
                if(combat.getEvoCD() > 0){
                    combat.setEvoCD(combat.getEvoCD() - 1);
                }
                if(combat.getIRan() == true){
                    combat.setIRan(false);
                    player.setCoords(oldCoords);
                    break;
                }
                if(combat.getStatus() == Status.VICTORY){
                    break;
                }
                if(combat.getStatus() == Status.DEFEAT){
                    break;
                }
            }catch(java.util.InputMismatchException e){
                System.out.println("That was not a command!");
                input.nextLine();
                System.out.println();
            }
        }//End of While
        combat.endBattle();
        if(combat.getIsWin() == true){
            map.endEvent(player.getCoords(), 0);
        }
        player = combat.updatePlayer();
        return;
    }//End of combatEvent
    
    private static void treasureEvent(){
        treasure = new Treasure(player, map);
        treasure.treasureFinder();
        player = treasure.updatePlayer();
        map = treasure.updateMap();
    }
    
    private static void readBook(){
        if(player.getCoords() == 9){
            System.out.println("You have found a very promising book.");
            System.out.println("While there's not much information inside you already don't know, there is enough to upgrade one of your spells.");
            System.out.println("Would you want to?:");
            System.out.printf("   1.) Upgrade Frostbolt to do 30 extra damage.\n   2.) Upgrade Ice Block to heal for 25 extra.\n   3.) Upgrade Ice Lance to do 20 extra damage.\n   4.) Upgrade Shatter! to do 15 extra damage.\n   5.) Maybe later.\nEnter a number: ");
            int userChoice = input.nextInt();
            System.out.println();
            if ((userChoice == 1)||(userChoice == 2)||(userChoice == 3)||(userChoice == 4)){
                upgradeSpell(userChoice);
                map.endEvent(player.getCoords(), 2);
            }
        }
    }//end readBook
    
    public static void restUp(){
        char userChar;
        input = new Scanner(System.in);
        System.out.println("You feel oddly refreshed.");
        player.giveHealth(player.getHealthMax());
        do{ 
            try{
                System.out.println("Do you want to set this as your rest zone? <y>/<n>");
                System.out.println();
                String readString = input.nextLine();
                userChar = readString.charAt(0);
                if(userChar == 'y'){
                    player.setRestArea(player.getCoords());
                }else{
                    System.out.println("You can come back later.");
                }
            }catch(InputMismatchException e){
                System.out.println();
                System.out.println("That was not a command!");
                System.out.println();
                input.nextLine();
                userChar = 'e';
            }
        }while((userChar != 'y')||(userChar != 'n'));
    }//End restUp
    
    public static void upgradeSpell(int userChoice){
        do{
            try{
                switch(userChoice){
                    case 1:
                        player.addSpellEffect(3, 30);
                        System.out.println("You feel more in tune with your spells. Frostbolt's damage was increased.");
                        break;
                        
                    case 2:
                        player.addSpellEffect(4, 25);
                        System.out.println("You feel more in tune with your spells. Ice Block's healing was increased.");
                        break;
                        
                    case 3:
                        player.addSpellEffect(5, 20);
                        System.out.println("You feel more in tune with your spells. Ice Lance's damage was increased.");
                        break;
                        
                    case 4:
                        player.addSpellEffect(6, 15);
                        System.out.println("You feel more in tune with your spells. Shatter's damage was increased.");
                        break;
                    default:
                        System.out.println("You can come back another time.");
                        userChoice = 1;
                        break;
                }
            }catch(InputMismatchException e){
                        System.out.println();
                        System.out.println("That was not a command!");
                        System.out.println();
                        input.nextLine();
                        userChoice = -55;
                    }
        }while((userChoice > 4)&&(userChoice < 0));
    }//End upgradeSpell
     
    private static void playerMove(){
        if (player.getHealth() > 0){
            Scanner input = new Scanner(System.in);
            System.out.println();
            String readString = input.next();
            char choiceChar = readString.charAt(0);
            player.setCoords(map.direction(player.getCoords(), choiceChar));
        }
    }//End of playerMover
    
     
      //Give the backstory, and offer to change directional keys.
    private static String welcomeKB(){
        boolean kbPicked = false;
        Scanner input = new Scanner(System.in);
        System.out.printf("What is your name?: ");
        String playerName = input.nextLine();
        System.out.printf("Hello, %s. You are getting this message due to some recent disturbances in \nthe tower of Kharazan. As a student and devotee of the Kirin Tor, \nwe have elected you to receive hands-on training in the tower we're\nsure you've heard lots about.\n", playerName);
        while(kbPicked == false){
            try{
                System.out.printf("The first thing we must ask you\nhowever, is for your choice of directioning.\nWhen moving north, south, east, and west, would you prefer using:\n1.) <n><s><e><w>; or\n2.) <w><s><d><a>?\nPlease choose a number: ");
                int choice = input.nextInt();
                System.out.printf("Your choice will be remembered.\n\n");
                if(choice == 1){
                    north = 'n';
                    south = 's';
                    east = 'e';
                    west = 'w';
                    kbPicked = true;
                }
                if(choice == 2){
                    north = 'w';
                    south = 's';
                    east = 'd';
                    west = 'a';
                    kbPicked = true;
                }
            }catch(InputMismatchException e){
                System.out.println("That was not a command!");
                System.out.println();
                input.nextLine();
            }
        }
        return playerName;
    }//End welcomeKB
     
}//end class
 