/**
 * Class that creates treasures.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Room
**/
import java.util.Scanner;

public class Treasure{
    private boolean isTaken = false;
    private Player player;
    private Room map;
    
    public Treasure(Player updatedPlayer, Room updatedMap){
        this.player = updatedPlayer;
        this.map = updatedMap;
    }//end Constructor
    
    public Player updatePlayer(){
        return this.player;
    }//end updatePlayer
    
    public Room updateMap(){
        return this.map;
    }//end updateMap
    
    private void upgradePlayer(int spellNumber, int spellDamage){
        player.addSpellEffect(spellNumber, spellDamage);
    }//end upgradePlayer
    
    private void upgradePlayer(int spellDamage){
        player.addSpellEffect(spellDamage);
    }//end upgrade
    
    public void treasureFinder(){
        if(player.getCoords() == 4){
            getHandWraps();
        }
        if(player.getCoords() == 17){
            getDazzlingWand();
        }
        if(player.getCoords() == 28){
            getCorruptedWand();
        }
    }//End treasureFinder
    
    public boolean getIsTaken(){
        if(isTaken == true){
            isTaken = false;
            return true;
        }
        else{
            return false;
        }
    }
    
    //Get treasure item Hand Wraps
    public void getHandWraps(){
        System.out.println("\nBehind Attumen you find some nice handwraps. Would you like to put them on? <y>/<n>:\n");
        Scanner input = new Scanner(System.in);
        String readString = input.nextLine();
        char choiceChar = readString.charAt(0);
        System.out.println();
        if(choiceChar == 'y'){
            map.endEvent(player.getCoords(), 1);
            player.addSpellEffect(5);
            System.out.printf("You put on the wraps, and you feel your power enhance!\n\n");
            
        }
        if(choiceChar == 'n'){
            System.out.printf("You can come pick these up later.\n\n");
        }
    }//End getHandWraps
    
    //Get treasure item Dazzling Wand
    public void getDazzlingWand(){
        System.out.printf("\nDigging through the chest, you find a Dazzling Wand!\nYour years of intensive research suggest that it will do around 25 to 55 damage. Would you like to use it? <y>/<n>:\n");
        Scanner input = new Scanner(System.in);
        String readString = input.nextLine();
        char choiceChar = readString.charAt(0);
        System.out.println();
        if(choiceChar == 'y'){
            changeWeapon('d');
            map.endEvent(player.getCoords(), 1);
            System.out.printf("You take the new wand!\n\n");
        }
        if(choiceChar == 'n'){
            System.out.printf("You can come pick it up later.\n\n");
        }
    }//End getDazzlingRod
    
    //Get treasure item Corrupted Wand
    public void getCorruptedWand(){
        System.out.printf("\nBefore even reaching it, you feel its presence. But then there it is! A Corrupted Wand!\nYou don't even know how much power is possesses! Would you like to use it? <y>/<n>:\n");
        Scanner input = new Scanner(System.in);
        String readString = input.nextLine();
        char choiceChar = readString.charAt(0);
        System.out.println();
        if(choiceChar == 'y'){
            changeWeapon('c');
            map.endEvent(player.getCoords(), 1);
            System.out.printf("You take the new wand!\n\n");
        }
        if(choiceChar == 'n'){
            System.out.printf("You can come pick it up later.\n\n");
        }
    }//End getCorruptedWand
    
    private void changeWeapon(char weaponChar){
        if(weaponChar == 'd'){
            player.setDamage(25, 55);
            player.setWeapon("Dazzling Wand");
        }
        if(weaponChar == 'c'){
            player.setDamage(0, 500);
            player.setWeapon("Corrupted Wand");
        }
        
    }//End of changeWeapon
}