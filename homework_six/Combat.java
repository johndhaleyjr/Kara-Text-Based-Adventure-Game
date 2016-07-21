/**
 * Class that creates the map of the dungeon.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Combat
**/
import java.util.Random;
import java.util.Scanner;

import java.util.InputMismatchException;

public class Combat{
    private static int evoCD = 0;
    static Random gen = new Random();
    private int overkill;
    private boolean isBattle;
    private boolean isWin;
    private Monster mob;
    private Player player;
    private Status playerStatus;
    private static Scanner input;
    private int playerCoords;
    private boolean iRan;
    
    public Combat(Player updatedPlayer){
        this.player = updatedPlayer;
        this.playerCoords = player.getCoords();
        this.overkill = 0;
        this.isBattle = false;
        this.playerStatus = Status.ABLE;
        findEnemy();
        this.iRan = false;
    }//End Constructor
    
    //Start getters
    
    public int getEvoCD(){
        return this.evoCD;
    }//End getEvoCD
    
    public int getOverkill(){
        return this.overkill;
    }//end getOverkill
    
    public boolean getIsBattle(){
        return this.isBattle;
    }//End getIsBattle
    
    public boolean getIsWin(){
        return this.isWin;
    }//End getIsWin
    
    public boolean getIRan(){
        return this.iRan;
    }//end getIRan
    
    public Status getStatus(){
        return this.playerStatus;
    }//End getStatus
    
    public Player updatePlayer(){
        return this.player;
    }//end updatePlayer
    
    //End Getters
    //Start Setters
    
    public void setEvoCD(int newCD){
        this.evoCD = newCD;
        player.setEvoCD(this.evoCD);
    }//End setEvoCD
    
    public void setOverkill(int newOK){
        this.overkill = newOK;
    }//End setOverkill
    
    public void setIsBattle(boolean newValue){
        this.isBattle = newValue;
    }//End setIsBattle
    
    public void setIsWin(boolean newValue){
        this.isWin = newValue;
    }//End setIsWin
    
    public void setIRan(boolean didIRun){
        this.iRan = didIRun;
    }//end setIRan
    
    public void setStatus(Status newStatus){
        playerStatus = newStatus;
    }//End setStatus
    
    //End Setters
    
    public void printStats(){
        String monsterName = mob.getName();
        int monsterHealth = mob.getHealth();
        int monsterHealthMax = mob.getHealthMax();
        int playerHealth = player.getHealth();
        int playerHealthMax = player.getHealthMax();
        int playerMana = player.getMana();
        int playerManaMax = player.getManaMax();
        if(playerStatus == Status.ABLE){
            //Print starting monster and player's stats.
            System.out.printf("You are fighting %s!\n", monsterName);
            System.out.printf("%s's Health: %d/%d \n", monsterName, monsterHealth, monsterHealthMax);
            System.out.printf("Your health: %d/%d \n", playerHealth, playerHealthMax);
            System.out.printf("Your mana: " + player.getMana() + "/" + player.getManaMax() + "\n\n");
        }
    }//End of printStatus
    
    public void checkStatus(){
        Status playerStatus = getStatus();
        if(player.getHealth() < 0){
            setStatus(Status.DEFEAT);
            checkWin();
        }
        else if(mob.getHealth() < 0){
            setStatus(Status.VICTORY);
            checkWin();
        }
        else if(playerStatus == Status.RUN){
            setIRan(true);
            System.out.println(getIRan());
            setStatus(Status.ABLE);
            checkWin();
        }
        else if(playerStatus == Status.GOBACK){
            if((mob.getHealth() > 0)&&(player.getHealth() > 0)){
                setStatus(Status.ABLE);
            }
        }
    }//End checkStatus
    
    public void checkWin(){
        if(playerStatus == Status.VICTORY){
            this.isWin = true;
        }
        else if(playerStatus == Status.DEFEAT){
            this.isWin = false;
        }
    }//End checkWin
    
    public void printMenu(){
        if ((playerStatus == Status.ABLE)||(playerStatus == Status.GOBACK)){
                System.out.println("Choices:");
                System.out.println("   1.) Attack with your " + player.getWeapon());
                System.out.println("   2.) Check Spellbook");
                System.out.println("   3.) Evocate   (Cooldown: " + getEvoCD() + ")");
                System.out.println("   4.) Run away");
                System.out.printf("What is your course of action? ");
                System.out.printf("\n\n");
            if(playerStatus == Status.GOBACK){
                playerStatus = Status.ABLE;
            }
        }
    }//End printChoice
    
    public void menuExecute(int playerChoice){
        int skillLine = mob.getSkillLine();
        boolean channelingHit = mob.getChannelingSpecial();
        
        switch(playerChoice){
            case 1: 
                if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                    int playerDamage = player.getDamage();
                    System.out.printf("You attack %s with your %s for %d damage!\n\n", mob.getName(), player.getWeapon(), playerDamage);
                    setOverkill(mob.takeHealth(playerDamage));
                }
                if((skillLine == 0)&&(channelingHit == true)){
                    System.out.printf("You cannot attack ghosts in the ethereal!\n\n");
                } 
                player.giveMana(60);
                break;
            
            
            //Opening spellbook
            case 2: 
                int choice;
                do{
                    player.printSB();
                    try{
                        input = new Scanner(System.in);
                        choice = input.nextInt();
                        spellChoice(choice);
                    }catch(InputMismatchException e){
                        System.out.println();
                        System.out.println("That was not a command!");
                        System.out.println();
                        input.nextLine();
                        choice = -55;
                    }
                }while(choice == -55);
                
                break;
            
            
            //If using evocate, don't go over max health or mana, AND start the cooldown counter.        
            case 3: 
                evoCD = getEvoCD();
                if(evoCD == 0){
                    System.out.printf("You use evocation, restoring mana and %d health!\n\n", player.getEvoHeal());
                    player.giveMana(120);
                    player.giveHealth(player.getEvoHeal());
                }
                
                if(evoCD > 0){
                    System.out.printf("That ability isn't ready yet! You waste your turn.\n\n");
                }
                evoCD += 5;
                if(evoCD > 5){
                    evoCD = 5;
                }
                setEvoCD(evoCD);
                break;
            
            
            //If running
            case 4:
                System.out.printf("You run from battle.\n\n");
                setStatus(Status.RUN);
                break;
            
                    
            default:
                System.out.printf("You did nothing.\n\n");
                player.giveMana(60);
                break;
            
        }
    }//End menuExecute
        
    public void spellChoice(int choice){
        System.out.println();
        int skillLine = mob.getSkillLine();
        int monsterCD = mob.getMonsterCD();
        int spellDamage;
        
        boolean specialHit = mob.getSpecialAttack();
        boolean channelingHit = mob.getChannelingSpecial();
        
        int playerMana = player.getMana();
        String monsterName = mob.getName();
        
        //If the enemy skillLine is 5, and the cooldown is up, the player will not be able to cast.
        if((skillLine != 101)||((skillLine == 101)&&(monsterCD == 0))){
            if(choice == 1){
                System.out.printf("You counterspelled %s, but it drains all of your mana!\n\n", monsterName);
                mob.setIsInterrupt(true);
                mob.setSpecialAttack(false);       //Interrupt stops specialHit
                mob.setChannelingSpecial(false);
                player.setMana(0);
                
            }else if(choice == 2){
                if(playerMana >= 60){
                    System.out.printf("You polymorph %s!\n\n", monsterName);
                    mob.setPoly(); //Randomizes a number between 1-3.
                    mob.setSpecialAttack(false);
                    mob.setChannelingSpecial(false);
                }else{
                    System.out.printf("You tried to polymorph %s, but you ran out of mana!\n\n", monsterName);
                }
                player.takeMana(60);
                
            }else if(choice == 3){
                if(playerMana >= 120){
                    System.out.printf("You start charging your Frostbolt.\n\n");
                    setStatus(Status.CASTING);//Will not attack next round.
                }
                else{
                    System.out.printf("You tried to charge your Frostbolt, but you ran out of mana!\n\n");
                    player.setMana(0);
                }
                
            }else if(choice == 4){
                if(playerMana >= 180){
                    System.out.printf("You have entered Ice Block, and cannot be damaged.\n\n");
                    setStatus(Status.ICEBLOCK);//Will not attack next round.
                    mob.setSpecialAttack(false);//Ice Block stops big hit.
                    //If enemy is not ghost, stop big attack
                    if(skillLine != 1){
                        mob.setChannelingSpecial(false);
                    }
                }else{
                    System.out.printf("You tried to use Ice Block, but you ran out of mana!\n\n");
                }
                player.takeMana(180);
                
            }else if(choice == 5){
                if(playerMana >= 240){
                    spellDamage = player.getSpellEffect(5);
                    if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                        System.out.printf("You instantly cast Ice Lance on %s, dealing %d damage!\n\n", monsterName, spellDamage);
                        setOverkill(mob.takeHealth(spellDamage));
                    }
                }else{
                    System.out.printf("You tried to Ice Lance %s, but you ran out of mana!\n\n", monsterName);
                }
                player.takeMana(240);
            }else if(choice == 6){
                if(playerMana >= 300){
                    spellDamage = player.getSpellEffect(6);
                    if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                        System.out.printf("You freeze %s, and then shatter them, dealing %d damage!\n\n", monsterName, spellDamage);
                        setOverkill(mob.takeHealth(spellDamage));
                    }
                }else{
                    System.out.printf("You tried to freeze %s, but you ran out of mana!\n\n", monsterName);
                }
                player.takeMana(300);
                    
            }else if(choice == 7){
                setStatus(Status.GOBACK);
                    
            }else if((choice < 1)||(choice > 7)){ //Could make this line a do while.
                System.out.printf("That is not a valid choice!\n\n");
                setStatus(Status.GOBACK);
            }
            if((skillLine == 0)&&(channelingHit == true)){
                System.out.printf("You couldn't hit a target in the etheral.\n\n");
                mob.setIsInterrupt(false);
            }
        }else{
            System.out.printf("You are silenced, and thus could not attack!\n\n");
            mob.setIsInterrupt(false);
        }
    }//end of spellChoice
    
    public void monsterHit(){
        mob.attack(getStatus());
        setOverkill(player.takeHealth(mob.getSkillMonsterDamage()));
    }
    
    public void spellChecker(){
        int statMonsterDamage;
        int spellDamage;
        String statMonsterName = mob.getName();
        
        Status playerStatus = getStatus();
        if((playerStatus == Status.CASTING)||(playerStatus == Status.ICEBLOCK)){
            if(playerStatus == Status.CASTING){
                spellDamage = player.getSpellEffect(3);
                System.out.printf("Your Frostbolt hits %s! You dealt %d damage.\n\n", statMonsterName, spellDamage);
                mob.takeHealth(spellDamage);
                setStatus(Status.ABLE);
            }
               
            if(playerStatus == Status.ICEBLOCK){
                spellDamage = player.getSpellEffect(4);
                System.out.printf("When you leave Ice Block, you are healed for %d!\n\n", spellDamage);
                player.giveHealth(spellDamage);
                setStatus(Status.ABLE);
                mob.setSpecialAttack(false);
                if(mob.getSkillLine() != 0){
                    mob.setChannelingSpecial(false);
                }
            }
        }
    }//End spellChecker
    
    public void endBattle(){
        String endMonsterName = mob.getName();
        if(getIsWin() == true){
            System.out.printf("You have defeated %s with a %d damage overkill!\n\n", endMonsterName, getOverkill());
            System.out.printf("You recover %d health for winning the battle!\n", 50);
            player.giveExp(50);
            player.giveHealth(50);
        }if(getIsWin() == false){
            if(playerStatus == Status.DEFEAT){
                System.out.printf("You have been defeated by %s with an overkill of %d damage. What a shame.\n\n", endMonsterName, getOverkill());
                player.setCoords(player.getRestArea());
                player.setHealth(player.getHealthMax());
                System.out.println("You died, however you find yourself alive and well here again.");
            }
            else{
                
            }
        }
    }//end endBattle
    
    private void findEnemy(){
        if(this.playerCoords == 2){
            this.mob = new Ghost();
        }
        else if(this.playerCoords == 3){
            this.mob = new Attumen();
        }
        else if((this.playerCoords == 8)||(this.playerCoords == 10)){
            this.mob = new SpectralGhost();
        }
        else if((this.playerCoords == 16)||(this.playerCoords == 21)||(this.playerCoords == 22)){
            this.mob = new Spider();
        }
        else if((this.playerCoords == 26)||(this.playerCoords == 30)){
            this.mob = new Hound();
        }
        else if(this.playerCoords == 27){
            this.mob = new Shadikith();
        }
        else if(this.playerCoords == 34){
            this.mob = new ServantSpecter();
        }
        else{
            this.mob = new Ghost();
        }
        
    }//end findEnemy
}