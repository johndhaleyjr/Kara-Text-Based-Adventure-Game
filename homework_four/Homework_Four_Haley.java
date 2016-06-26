/**
 * Text-based adventure game exploring the depths of Karazhan.
 * As of now, there is only one floor, but I have left options available
 * for implementation.
 * 
 * 
 * TODO:
 *      BUGS:
 *          ~While implementation of adding spell damage is there,
 *              it does not actually work.
 *          ~While
 *
 * 
 * Author: John Haley
 * Last Revised: 25 June 2016
 * Assignment: Homework Two
 * Class: Homework_Four_Haley
 */
 
 //Import java utilities: Scanner, Random
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

 public class Homework_Four_Haley{
     
     //Enums
    public enum GameStatus {CONTINUE, WIN, LOSS, RUN, GOBACK}
    public enum PlayerStatus {ABLE, RUN, CASTING, ICEBLOCKED, GOBACK}
    private static GameStatus gameStatus;
    private static PlayerStatus playerStatus;

    //Initialize arrays
    private static String[] mapFloorOne = new String[38];
    private static int[][] floorOneExits = new int[38][4];
    private static boolean[][] tileEvent = new boolean[36][4];
    
    //Initialize variables
    //Player Variables
    private static String playerName = "you";
    private static int playerHealthMax = 250; //Max HP
    private static int playerManaMax = 300; //Max MP
    private static int playerMana = 300; //Current Mana
    private static int playerDamage = 32;
    private static int playerMaxDamage = 50;
    private static int playerMinDamage = 10;
    private static double playerHealth = 250;
    private static int playerExp = 0;
    private static int playerLevel = ((playerExp / 100) + 1);
    private static String playerWeapon = "Wand";
    private static String playerSpell = "Coding Error";
    private static int evoCD = 0;
    private static int playerRestZone = 0;
    private static boolean isLoss = false;
    
    //Spell Damage
    private static int[] spellEffect = {
                        55, 50, 65, 85
                        };
    //Monster Variables
    private static int monsterHealthMax = 400; //Max HP
    private static int monsterDamage = 28;
    private static int monsterMax = 40;
    private static int monsterMin = 20;
    private static int poly = 0;
    private static double monsterHealth = 400;
    private static boolean isInterrupt = false;
    private static boolean bigHit = false;
    private static boolean channelingHit = false;
    private static String monsterName = "Target Dummy";
    //Initialize Scanner
    private static Scanner input = new Scanner(System.in);
    //Initialize player movement keys. 
    private static char north = 'n';
    private static char south = 's';
    private static char east = 'e';
    private static char west = 'w';
    
    
    
    //Initialize the main method
    public static void main(String[] args){
        //Create new scanner
        Scanner input = new Scanner(System.in);
        //Initialize Variables
        int playerCoords = 0;
        
        //Welcome user
        welcomeKB();
        //Initialize startup
        startUp();
        //While Player is not leaving
        char choiceChar = 's';
            while(playerCoords != 36){
                //Level player if exp is high enough.
                playerLevel = levelUp(playerExp, playerLevel);
                
                //Print room if alive, send to restzone if dead.
                if(isLoss == false){
                    System.out.println(mapFloorOne[playerCoords]);
                }
                else if(isLoss == true){
                    playerCoords = playerRestZone;
                    System.out.println("You died, however you find yourself alive and well here again.");
                    System.out.println(mapFloorOne[playerCoords]);
                    isLoss = false;
                }
                //Ask for directions
                playerCoords = playerMove(playerCoords, choiceChar);
                
                //Check for event, and if palyer died, enact the following.
                isLoss = event(playerCoords);
            }
    } //End of main
    
    //Initialize startups
    private static void startUp(){
        mapsOne();
        exitsOne();
        tileEventFill();
    }//End startUp
    
    //Give the backstory, and offer to change directional keys.
    private static void welcomeKB(){
        System.out.printf("What is your name?: ");
        playerName = input.nextLine();
        System.out.printf("Hello, %s. You are getting this message due to some recent disturbances in \nthe tower of Kharazan. As a student and devotee of the Kirin Tor, \nwe have elected you to receive hands-on training in the tower\nwe're sure you've heard lots about.\n\nThe first thing we must ask you however, is for your choice of directioning.\nWhen moving north, south, east, and west, would you prefer using:\n1.) <n><s><e><w>; or\n2.) <w><s><d><a>?\nPlease choose a number: ", playerName);
        int choice = input.nextInt();
        System.out.printf("Your choice will be remembered.\n\n");
        if(choice == 1){
            north = 'n';
            south = 's';
            east = 'e';
            west = 'w';
        }
        if(choice == 2){
            north = 'w';
            south = 's';
            east = 'd';
            west = 'a';
        }
    }
    //Moves the player's coordinates depending on their input.
    private static int playerMove(int playerCoords, char choiceChar){
        if (playerHealth > 0){
            System.out.println();
            String readString = input.next();
            choiceChar = readString.charAt(0);
            playerCoords = playerDirection(playerCoords, choiceChar);
        }
        return playerCoords;
    }//End of playerMover
        
    //Form a method to decide whether to go north, south, east, or west.  
    private static int playerDirection(int playerCoords, char choiceChar){
        if(choiceChar == north){
            playerCoords = floorOneExits[playerCoords][0];
        }
        else if(choiceChar == south){
            playerCoords = floorOneExits[playerCoords][1];
        }
        else if(choiceChar == east){
            playerCoords = floorOneExits[playerCoords][2];
        }
        else if(choiceChar == west){
            playerCoords = floorOneExits[playerCoords][3];
        }
    
        else if(playerCoords == 36){
            System.out.println("You leave the tower of Karazhan.");
            System.exit(0);
        }
        
        //Sending the new playerCoords to check for event.
        return playerCoords;
    }//End playerMove
    
    //Player levelup
    private static int levelUp(int playerExp,int playerLevel){
        if(playerLevel < ((playerExp / 100) + 1)){
            playerLevel = ((playerExp / 100) + 1);
            playerHealthMax += 50;
            playerHealth = playerHealthMax;
            System.out.println("Congratulations on leveling up to level " + playerLevel + "! Your max health has increased by 50!");
        }
        return playerLevel;
    }
    
    //Check to see if tile is flagged for an event.
    private static boolean event(int playerCoords){
        //If tile is flagged for a battle, do battle.
        boolean isLoss = false;
        if(tileEvent[playerCoords][0] == true){
            isLoss = combat(playerCoords);
        }
        //If tile is flagged for a treasure, offer treasure.
        if(tileEvent[playerCoords][1] == true){
            treasureFinder(playerCoords);
        }
        //If tile is flagged for spell, offer spell.
        if(tileEvent[playerCoords][2] == true){
            readBook(playerCoords);
        }
        //If tile is flagged for a rest area, heal the player and set rest area.
        if(tileEvent[playerCoords][3] == true){
            restUp(playerCoords);
        }
        return isLoss;
        
    }//End of event
    
    //Implement battles.
    private static boolean combat(int playerCoords){
        //Variable initialization.
        boolean loopStatus = true;
        int choice = 0;
        int monsterCD = 0;
        evoCD = 0;
        poly = 0;
        int[] monsterStats = new int[7];
        String fighting = "You ran into ";
        System.out.printf("\n\n");
        
        //Status and stats initialization.
        GameStatus gameStatus = GameStatus.CONTINUE;
        PlayerStatus playerStatus = PlayerStatus.ABLE;
        monsterStats = findEnemy(playerCoords);
        
        //Assign stats
        int monsterID = monsterStats[0];
        monsterHealthMax = monsterStats[1];
        monsterHealth = monsterHealthMax;
        monsterDamage = monsterStats[2];
        monsterMin = monsterStats[3];
        monsterMax = monsterStats[4];
        int skillLine = monsterStats[5];
        findEnemyName(monsterID);
        monsterName = findEnemyName(monsterID);
        
        
        while(loopStatus == true) {
            gameStatus = changeStatus(gameStatus);
            //Change loop status
            loopStatus = changeLoop(gameStatus);
            
            //Print health and mana
            fighting = printStats(gameStatus, fighting);
            
            //Prints info to choose attack.
            printChoice(playerStatus);
            
            //Grab choice from player
            choice = input.nextInt();
            
            //Give player the choices and enacts them
            playerStatus = userChoice(choice, skillLine, monsterCD, playerStatus);
            
            //If player goes back, set them back to able.
            if(playerStatus == PlayerStatus.GOBACK){
                playerStatus = PlayerStatus.ABLE;
                continue;
            }
            //Check for the skills of the monster while setting monsterCD.
            monsterCD = skillLineCheck(skillLine, monsterCD, playerStatus);//Monster attacks
            
            playerStatus = statusChecker(playerStatus);//Depending on player status, does what it needs.
            gameStatus = changeStatus(gameStatus);//Recheck to help end loop.
            loopStatus = changeLoop(gameStatus);//Recheck to help end loop.
            //If evoCD is over 0, increment one down.
            if(evoCD > 0){
                evoCD--;
            }
        }//End of While
        boolean isLoss = endBattle(playerCoords);
        return isLoss;
    }//End of combat
    
    //Change game status
    public static GameStatus changeStatus(GameStatus gameStatus){
        if(gameStatus == GameStatus.GOBACK){
            if((monsterHealth > 0)&&(playerHealth > 0)){
                gameStatus = GameStatus.CONTINUE;
            }
        }
        if(monsterHealth <= 0){
            gameStatus = GameStatus.WIN;
        }
        else if(playerHealth <= 0){
            gameStatus = GameStatus.LOSS;
        }else if(playerStatus == PlayerStatus.RUN){
            gameStatus = GameStatus.RUN; //Game over, run away!
        }
        return(gameStatus);
    }//End changeStatus method
    
    //Determine whether or not to end combat loop.
     public static boolean changeLoop(GameStatus gameStatus){
        boolean keepLoop;
        if((gameStatus == GameStatus.WIN)||(gameStatus == GameStatus.LOSS)||(gameStatus == GameStatus.RUN)){
            keepLoop = false;
        }
        else{
            keepLoop = true;
        }
        return(keepLoop);
    }//End of changeLoop
    
    //Method: Print the stats of the battle if the status calls for it.
    public static String printStats(GameStatus gameStatus, String fighting){
        if(gameStatus == GameStatus.CONTINUE){
            //Print starting monster and player's stats.
            System.out.printf(fighting + "%s!\n", monsterName);
            System.out.printf(monsterName + "'s Health: %.0f/%d \n", monsterHealth, monsterHealthMax);
            System.out.printf("Your health: %.0f/%d \n", playerHealth, playerHealthMax);
            System.out.printf("Your mana: " + playerMana + "/" + playerManaMax + "\n\n");
        }
        fighting = "You are fighting ";
        return fighting;
    }//End of printStatus
    
    //Prints menu of choices if allowed to pick a choice.
    public static void printChoice(PlayerStatus playerStatus){
        if ((playerStatus == PlayerStatus.ABLE)||(playerStatus == PlayerStatus.GOBACK)){
                System.out.println("Choices:");
                System.out.println("   1.) Attack with your " + playerWeapon);
                System.out.println("   2.) Check Spellbook");
                System.out.println("   3.) Evocate   (Cooldown: " + evoCD + ")");
                System.out.println("   4.) Run away");
                System.out.printf("What is your course of action? ");
                System.out.printf("\n\n");
            if(playerStatus == PlayerStatus.GOBACK){
                playerStatus = PlayerStatus.ABLE;
            }
        }
    }//End printChoice
    
    //Implements user choice
    public static PlayerStatus userChoice(int choice, int skillLine, int monsterCD, PlayerStatus playerStatus){
        switch(choice){
            //If attack with weapon
                    case 1: 
                            if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                                playerDamage = attackDamage(playerMinDamage, playerMaxDamage);
                                System.out.printf("You attack %s with your %s for %d damage!\n\n", monsterName, playerWeapon, playerDamage);
                                monsterHealth = (monsterHealth - playerDamage);
                            }
                            if((skillLine == 0)&&(channelingHit == true)){
                                System.out.printf("You cannot attack ghosts in the ethereal!\n\n");
                            } 
                            if(playerMana <= 240){
                                playerMana += 60;
                            }
                            break;
                    
                    //Opening spellbook       
                    case 2: printSB();
                            choice = input.nextInt();
                            playerStatus = spellChoice(choice, skillLine, monsterCD, playerStatus);
                        break;
                    
                    //If using evocate, don't go over max health or mana, AND start the cooldown counter.        
                    case 3: if(evoCD == 0){
                                System.out.printf("You use evocation, restoring mana and 30 health!\n\n");
                                if(playerMana == 240){
                                    playerMana += 60;
                                }
                                
                                if(playerMana <= 180){
                                    playerMana += 120;
                                }
                                
                                if(playerHealth > 220){
                                    playerHealth = playerHealthMax;
                                }
                                if (playerHealth <= 220){
                                    playerHealth += 30;
                                }
                            }
                            
                            if (evoCD > 0){
                                System.out.printf("That ability isn't ready yet! You waste your turn.\n\n");
                            }
                            evoCD += 5; //add 5 to Evo cooldown
                            if(evoCD > 5){
                                evoCD = 5;
                            }
                            
                            break;
                    
                    //If running
                    case 4: System.out.printf("You run from battle.\n\n");
                            playerStatus = PlayerStatus.RUN;
                            break;
                            
                    default: System.out.printf("You did nothing.\n\n");
                             if(playerMana <= 280){
                                 playerMana += 60;
                             }
                             if(playerMana > 280){
                                 playerMana = playerManaMax;
                             }
                             break;
                             
            } //End of switch choice.
            return playerStatus;
    }//End of userChoice
    
    //Grants the effect of a spell choice.
    public static PlayerStatus spellChoice(int choice, int skillLine, int monsterCD, PlayerStatus playerStatus){
        System.out.println();
        
        //If the enemy skillLine is 5, and the cooldown is up, the player will not be able to cast.
        if((skillLine != 5)||((skillLine == 5)&&(monsterCD == 0))){
            if(choice == 1){
                System.out.printf("You counterspelled %s, but it drains all of your mana!\n\n", monsterName);
                isInterrupt = true;
                bigHit = false;       //Interrupt stops bigHit
                channelingHit = false;
                playerMana = 0;
            }else if(choice == 2){
                if(playerMana >= 60){
                    System.out.printf("You polymorph %s!\n\n", monsterName);
                    poly = polyRan(); //Randomizes a number between 1-3.
                    playerMana -= 60;
                    bigHit = false;
                    channelingHit = false;
                }else{
                        System.out.printf("You tried to polymorph %s, but you ran out of mana!\n\n", monsterName);
                        playerMana = 0;
                    }
            }else if(choice == 3){
                if(playerMana >= 120){
                    System.out.printf("You start charging your Frostbolt.\n\n");
                    playerStatus = PlayerStatus.CASTING;//Will not attack next round.
                    playerMana -= 120;
                }
                else{
                    System.out.printf("You tried to charge your Frostbolt, but you ran out of mana!\n\n");
                    playerMana = 0;
                }
                
            }else if(choice == 4){
                if(playerMana >= 180){
                    System.out.printf("You have entered Ice Block, and cannot be damaged.\n\n");
                    playerStatus = PlayerStatus.ICEBLOCKED;//Will not attack next round.
                    playerMana -=180;
                    bigHit = false;//Ice Block stops big hit.
                    //If enemy is not ghost, stop big attack
                    if(skillLine != 1){
                        channelingHit = false;
                    }
                }else{
                    System.out.printf("You tried to use Ice Block, but you ran out of mana!\n\n");
                    playerMana = 0;
                }
                
            }else if(choice == 5){
                if(playerMana >= 240){ 
                    if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                        System.out.printf("You instantly cast Ice Lance on %s, dealing %d damage!\n\n", monsterName, spellEffect[2]);
                        monsterHealth -= 60;
                        playerMana -= 240;
                    }
                }else{
                    System.out.printf("You tried to Ice Lance %s, but you ran out of mana!\n\n", monsterName);
                    playerMana = 0;
                }
                
            }else if(choice == 6){
                if(playerMana >= 300){
                    if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                        System.out.printf("You freeze %s, and then shatter them, dealing %d damage!\n\n", monsterName, spellEffect[3]);
                        monsterHealth -= 85;
                        playerMana -= 300;
                    }
                }else{
                    System.out.printf("You tried to freeze %s, but you ran out of mana!\n\n", monsterName);
                    playerMana = 0;
                }
                    
            }else if(choice == 7){
                playerStatus = PlayerStatus.GOBACK;
                    
            }else if((choice < 1)||(choice > 7)){ //Could make this line a do while.
                System.out.printf("That is not a valid choice!\n\n");
                playerStatus = PlayerStatus.GOBACK;
            }
            if((skillLine == 0)&&(channelingHit == true)){
                System.out.printf("You couldn't hit a target in the etheral.\n\n");
            }
        }else{
            System.out.printf("You are silenced, and thus could not attack!\n\n");
        }
        return playerStatus;
    }//end of spellChoice
    
    //Prints spellbook to player
    public static void printSB(){
        String spellZero = "Counterspell";
        String spellOne = "Polymorph";
        String spellTwo = "Frostbolt";
        String spellThree = "Ice Block";
        String spellFour = "Ice Lance";
        String spellFive = "Shatter!";
        System.out.printf("You open your spellbook and search through the spells:\n");
        System.out.printf("   1.) %s (All mana)\n", spellZero);
        System.out.printf("   2.) %s (60 mana)\n", spellOne);
        System.out.printf("   3.) %s (120 mana)\n", spellTwo);
        System.out.printf("   4.) %s (180 mana)\n", spellThree);
        System.out.printf("   5.) %s (240 mana)\n", spellFour);
        System.out.printf("   6.) %s (300 mana)\n", spellFive);
        System.out.printf("   7.) Close the spellbook.\n");
    }//End of printSB
    
    //Psuedorandoms a value between the minimum and maximum of an attack.    
    public static int attackDamage(int minAttack, int maxAttack){
        Random gen = new Random();
        int attackDif = 0;
        int attackHit = 0;
        attackDif = (maxAttack - minAttack);
        attackHit = gen.nextInt(attackDif);
        attackHit += minAttack;
        return(attackHit);
    }//end of random method
    
    //Allows polymorph to last for 1 to 3 rounds. The user isn't told how long.
    public static int polyRan(){
        Random gen = new Random();
        int polyCount = gen.nextInt(3);
        return(polyCount + 1);
    }//end of polyRan;
        
    //Controls the monster's big attack.
    public static boolean bigAttack(){
        Random gen = new Random();
        boolean isBig = false;
        int hitChance = (gen.nextInt(100) + 1);
        if(hitChance <=80){
            isBig = false;
        }
        if(hitChance > 80){
            isBig = true;
        }
        return(isBig);
    }//End bigAttack
        
    public static int skillLineCheck(int skillLine, int monsterCD, PlayerStatus playerStatus){
        //Spectral Ghost & Ghosts
        if(skillLine == 0){
            monsterCD = ghostSkills(monsterCD, playerStatus);
        }
        //Spiders
        if(skillLine == 1){
            monsterCD = spiderSkills(monsterCD, playerStatus);
        }
        //Bats
        if(skillLine == 2){
            houndSkills(playerStatus);
        }
        
        //Attumen
        if(skillLine == 100){
            attumenBossSkills(playerStatus);
        }
        //Rokad
        if(skillLine == 101){
            monsterCD = shadikithSkills(monsterCD, playerStatus);
        }
        return monsterCD;
    }//End skillLineCheck
        
    public static int ghostSkills(int monsterCD, PlayerStatus playerStatus){
        if(monsterCD > 0){
            monsterCD--;
        }
        if((poly <= 0)&&(monsterHealth > 0)&&((playerStatus == PlayerStatus.ABLE)||(playerStatus == PlayerStatus.CASTING))){
            if(channelingHit == false){
                bigHit = bigAttack();//Checking for value.
            }
            
            if((bigHit == false)){
                if(channelingHit == false){
                    if(isInterrupt == false){
                        monsterDamage = attackDamage(monsterMin, monsterMax);
                        System.out.printf("%s haunts you for %d damage!\n\n\n", monsterName, monsterDamage);
                        playerHealth -= monsterDamage;
                    }
                    if(isInterrupt == true){
                        System.out.printf("%s could not attack.\n\n\n", monsterName);
                        isInterrupt = false;
                    }
                }
                
                if(channelingHit == true){
                    monsterDamage = ((attackDamage(monsterMin, monsterMax)) / 2);
                    System.out.printf("%s haunts you for %d damage!\n\n\n", monsterName, monsterDamage);
                    playerHealth -= monsterDamage;
                }
            }
            //Big Attack Hit, has to come before the Big Attack
            if((channelingHit == true)&&(monsterCD == 0)){
                System.out.printf("%s has shifted out of the etheral plain.\n\n\n", monsterName);
                channelingHit = false;
                bigHit = false;
            }
            //Big Attack
            if(bigHit == true){
                System.out.printf("%s shifts into the ethereal plain.\n\n\n", monsterName);
                    channelingHit = true;
                    bigHit = false;
                    monsterCD = polyRan();
            }
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(monsterHealth > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            poly -= 1;
        }
        //If player is iceblocked, do not hit.
        if(playerStatus == PlayerStatus.ICEBLOCKED){
        System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", monsterName);
        }
        return monsterCD;
    }//End of ghostSkills
        
    //The next few methods enable the monsters to attack
    public static void attumenBossSkills(PlayerStatus playerStatus){
        if((poly <= 0)&&(isInterrupt == false)&&(monsterHealth > 0)&&((playerStatus == PlayerStatus.ABLE)||(playerStatus == PlayerStatus.CASTING))){
            if(channelingHit == false){
                bigHit = bigAttack();//Checking for value.
            }
            
            if((bigHit == false)&&(channelingHit == false)){
                monsterDamage = attackDamage(monsterMin, monsterMax);
                System.out.printf("%s attacks you for %d damage!\n\n\n", monsterName, monsterDamage);
                playerHealth -= monsterDamage;
            }
            //Big Attack Hit, has to come before the Big Attack
            if(channelingHit == true){
                System.out.printf("%s strikes you down with the help of his steed, dealing %d damage!\n\n\n", monsterName, 200);
                playerHealth -= 200;
                channelingHit = false;
            }
            //Big Attack
            if(bigHit == true){
                    System.out.printf("%s is getting ready for a big attack.\n\n\n", monsterName);
                    channelingHit = true;
                    bigHit = false;
            }
        }
        //If interrupted, cancel attack and big hit.
        if((isInterrupt == true)&&(monsterHealth > 0)){
            System.out.printf("%s could not attack.\n\n\n", monsterName);
            isInterrupt = false;
            bigHit = false;
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(monsterHealth > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            poly -= 1;
        }
        //If player is iceblocked, enemy will not attack.
        if(playerStatus == PlayerStatus.ICEBLOCKED){
            System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", monsterName);
        }
    }//End monsterWillAttack
    
    public static int spiderSkills(int monsterCD, PlayerStatus playerStatus){
        if(monsterCD > 0){
            monsterCD--;
        }
        if((poly <= 0)&&(monsterHealth > 0)&&((playerStatus == PlayerStatus.ABLE)||(playerStatus == PlayerStatus.CASTING))){
            if(channelingHit == false){
                bigHit = bigAttack();//Checking for value.
            }
            
            if((bigHit == false)){
                if(isInterrupt == false){
                    monsterDamage = attackDamage(monsterMin, monsterMax);
                    System.out.printf("%s bites you for %d damage!\n", monsterName, monsterDamage);
                    playerHealth -= monsterDamage;
                }
                if(isInterrupt == true){
                    System.out.printf("%s could not attack.\n", monsterName);
                    isInterrupt = false;
                }
            }
            //Big Attack Hit, has to come before the Big Attack
            if((channelingHit == true)&&(monsterCD == 0)){
                System.out.printf("The poison has been cured.\n");
                channelingHit = false;
                bigHit = false;
            }
            
            if((channelingHit == true)&&(monsterCD >= 0)){
                int poisonDamage = attackDamage(3, 12);
                System.out.printf("The poison hurts you for %d.", poisonDamage);
                playerHealth -= poisonDamage;
            }
            
            //Big Attack
            if(bigHit == true){
                System.out.printf("%s's fangs have poisoned you!\n", monsterName);
                    channelingHit = true;
                    bigHit = false;
                    monsterCD = 3;
            }
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(monsterHealth > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n", monsterName);
            poly -= 1;
        }
        //If player is iceblocked, do not hit.
        if(playerStatus == PlayerStatus.ICEBLOCKED){
        System.out.printf("%s tried to attack you, but you were protected in ice.\n", monsterName);
        }
        System.out.printf("\n\n");
        return monsterCD;
    }//End of ghostSkills
    
    public static void houndSkills(PlayerStatus playerStatus){
        if((poly <= 0)&&(isInterrupt == false)&&(monsterHealth > 0)&&((playerStatus == PlayerStatus.ABLE)||(playerStatus == PlayerStatus.CASTING))){
            bigHit = bigAttack();//Checking for value.
                monsterDamage = attackDamage(monsterMin, monsterMax);
                System.out.printf("%s scratches you for %d damage!\n\n\n", monsterName, monsterDamage);
                playerHealth -= monsterDamage;
            //Big Attack Hit, has to come before the Big Attack
            //Big Attack
            if(bigHit == true){
                    System.out.printf("%s's pups come charging in!\n", monsterName);
                    int pups = polyRan();
                    int pupDamage;
                    for(int i = 0; i < pups; i++){
                        pupDamage = attackDamage(2, 7);
                        System.out.printf("A pup bites you for %d damage!\n", pupDamage);
                        playerHealth -= pupDamage;
                    }
                    bigHit = false;
                    System.out.printf("\n\n");
            }
        }
        //If interrupted, cancel attack and big hit.
        if((isInterrupt == true)&&(monsterHealth > 0)){
            System.out.printf("%s could not attack.\n\n\n", monsterName);
            isInterrupt = false;
            bigHit = false;
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(monsterHealth > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            poly -= 1;
        }
        //If player is iceblocked, enemy will not attack.
        if(playerStatus == PlayerStatus.ICEBLOCKED){
            System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", monsterName);
        }
    }//End monsterWillAttack
    
    public static int shadikithSkills(int monsterCD, PlayerStatus playerStatus){
        if(monsterCD > 0){
            monsterCD--;
        }
        if((poly <= 0)&&(monsterHealth > 0)&&((playerStatus == PlayerStatus.ABLE)||(playerStatus == PlayerStatus.CASTING))){
            if(channelingHit == false){
                bigHit = bigAttack();//Checking for value.
            }
            
            if(channelingHit == false){
                if(isInterrupt == false){
                    monsterDamage = attackDamage(monsterMin, monsterMax);
                    System.out.printf("%s haunts you for %d damage!\n\n\n", monsterName, monsterDamage);
                    playerHealth -= monsterDamage;
                }
                if(isInterrupt == true){
                    System.out.printf("%s could not attack.\n\n\n", monsterName);
                    isInterrupt = false;
                }
            }
            //Big Attack Hit, has to come before the Big Attack
            if((channelingHit == true)&&(monsterCD == 0)){
                System.out.printf("You are no longer silenced!\n\n\n", monsterName);
                channelingHit = false;
                bigHit = false;
            }
            //Big Attack
            if(bigHit == true){
                System.out.printf("%s shrieks at you, silencing you, making you unable to cast spells!\n\n\n", monsterName);
                    channelingHit = true;
                    bigHit = false;
                    monsterCD = 5;
            }
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(monsterHealth > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            poly -= 1;
        }
        //If player is iceblocked, do not hit.
        if(playerStatus == PlayerStatus.ICEBLOCKED){
        System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", monsterName);
        }
        return monsterCD;
    }//End of ghostSkills
    
    //End of attacking methods.
    
    //Checks the status of the player to allow for spells to function.
    public static PlayerStatus statusChecker(PlayerStatus playerStatus){
        if((playerStatus == PlayerStatus.CASTING)||(playerStatus == PlayerStatus.ICEBLOCKED)){
            if(playerStatus == PlayerStatus.CASTING){
                System.out.printf("Your Frostbolt hits %s! You dealt %d damage.\n\n", monsterName, spellEffect[0]);
                monsterHealth -= 40;
                
                monsterDamage = attackDamage(monsterMin, monsterMax);
                System.out.printf("%s attacks you for %d damage!\n\n\n", monsterName, monsterDamage);
                playerHealth -= monsterDamage;
                playerStatus = PlayerStatus.ABLE;
            }
               
            if(playerStatus == PlayerStatus.ICEBLOCKED){
                System.out.printf("When you leave Ice Block, you are healed for %d!\n\n", spellEffect[1]);
                if((playerHealth <= (playerHealthMax - spellEffect[1]))&&(playerHealth > 0)){
                    playerHealth += spellEffect[1];
                } else{
                    playerHealth = playerHealthMax;
                }
                playerStatus = PlayerStatus.ABLE;
                bigHit = false;
                channelingHit = false;
            }
        }
        return playerStatus;
    }//End statusChecker
    
    //publishes end battle stats, and rids of defeated enemies.
    public static boolean endBattle(int playerCoords){
        boolean isLoss = false;
        if(monsterHealth <= 0){
            double overKill = (0 - monsterHealth);
            monsterHealth = 0;
            System.out.printf("You have defeated %s with a %.0f damage overkill!\n\n", monsterName, overKill);
            System.out.printf("You recover %d health for winning the battle!\n", 50);
            playerExp += 50;
            isLoss = false;
            tileEvent[playerCoords][0] = false;
            if(playerHealth > (playerHealthMax - 50)){
                playerHealth = playerHealthMax;
            }
            else{
                playerHealth += 50;
            }
            return isLoss;
        }if(playerHealth <= 0){
            double overKill = (0 - playerHealth);
            playerHealth = 0;
            System.out.printf("You have been defeated by %s with an overkill of %.0f damage. What a shame.\n\n", monsterName, overKill);
            playerCoords = playerRestZone;
            isLoss = true;
            defeated(playerCoords, isLoss);
            playerHealth = playerHealthMax;
            return isLoss;
        }
        return isLoss;
    }//end endBattle
    
    public static void defeated(int playerCoords, boolean isLoss){
        tileEvent[playerCoords][0] = isLoss;
        return;
    }//End defeated
    
    //End of combat methods
    
    //Implements treasure.
    public static void treasureFinder(int playerCoords){
        if(playerCoords == 4){
            getHandWraps();
        }
        if(playerCoords == 17){
            getDazzlingWand();
        }
        if(playerCoords == 28){
            getCorruptedWand();
        }
    }//End treasureFinder
    
    //Get treasure item Hand Wraps
    public static void getHandWraps(){
        System.out.println("\nBehind Attumen you find some nice handwraps. Would you like to put them on? <y>/<n>:\n");
        Scanner input = new Scanner(System.in);
        String readString = input.nextLine();
        char choiceChar = readString.charAt(0);
        System.out.println();
        if(choiceChar == 'y'){
            increasespellEffect();
            tileEvent[4][1] = false;
            System.out.printf("You put on the wraps, and you feel your power enhance!\n\n");
        }
        if(choiceChar == 'n'){
            System.out.printf("You can come pick these up later.\n\n");
        }
    }//End getHandWraps
    
    //Get treasure item Dazzling Wand
    public static void getDazzlingWand(){
        System.out.printf("\nDigging through the chest, you find a Dazzling Wand!\nYour years of intensive research suggest that it will do around 25 to 55 damage. Would you like to use it? <y>/<n>:\n");
        Scanner input = new Scanner(System.in);
        String readString = input.nextLine();
        char choiceChar = readString.charAt(0);
        System.out.println();
        if(choiceChar == 'y'){
            changeWeapon('d');
            tileEvent[17][1] = false;
            System.out.printf("You take the new wand!\n\n");
        }
        if(choiceChar == 'n'){
            System.out.printf("You can come pick it up later.\n\n");
        }
    }//End getDazzlingRod
    
    //Get treasure item Corrupted Wand
    public static void getCorruptedWand(){
        System.out.printf("\nBefore even reaching it, you feel its presence. But then there it is! A Corrupted Wand!\n You don't even know how much power is possesses! Would you like to use it? <y>/<n>:\n");
        Scanner input = new Scanner(System.in);
        String readString = input.nextLine();
        char choiceChar = readString.charAt(0);
        System.out.println();
        if(choiceChar == 'y'){
            changeWeapon('c');
            tileEvent[28][1] = false;
            System.out.printf("You take the new wand!\n\n");
        }
        if(choiceChar == 'n'){
            System.out.printf("You can come pick it up later.\n\n");
        }
    }//End getCorruptedWand
    
    //Increase spellEffect by 5.
    public static void increasespellEffect(){
        spellEffect[0] = (spellEffect[0] + 5);
        spellEffect[1] = (spellEffect[1] + 5);
        spellEffect[2] = (spellEffect[2] + 5);
        return;
    }//End of increaseSpellEffect
    
    //Put on corresponding weapon.
    public static void changeWeapon(char weaponChar){
        if(weaponChar == 'd'){
            playerMaxDamage = 55;
            playerMinDamage = 25;
            playerWeapon = "Dazzling Wand";
        }
        if(weaponChar == 'c'){
            playerMaxDamage = 500;
            playerMinDamage = 0;
            playerWeapon = "Corrupted Wand";
        }
        
    }//End of changeWeapon
    
    //End of treasure methods
    
    //Allows player to choose to upgrade a spell
    public static void readBook(int playerCoords){
        if(playerCoords == 9){
            System.out.println("You have found a very promising book.");
            System.out.println("While there's not much information inside you already do not know, there is enough to upgrade one of your spells.");
            System.out.println("Would you want to?:");
            System.out.printf("   1.) Upgrade Frostbolt to do 30 extra damage.\n   2.)Upgrade Ice Block to heal for 25 extra.\n   3.) Upgrade Ice Lance to do 20 extra damage.\n   4.) Upgrade Shatter! to do 15 extra damage.\n   5.) Maybe later.\nEnter a number: ");
            int userChoice = input.nextInt();
            System.out.println();
            if ((userChoice == 1)||(userChoice == 2)||(userChoice == 3)||(userChoice == 4)){
                tileEvent[9][2] = false;
            }
        }
    }//End of readBook
    
    //Actually upgrades the spell
    public static void upgradeSpell(int userChoice){
        switch(userChoice){
            case 1:
                spellEffect[0] += 30;
                System.out.println("You feel more in tune with your spells. Frostbolt's damage was increased.");
                break;
                
            case 2:
                spellEffect[1] += 50;
                System.out.println("You feel more in tune with your spells. Ice Block's healing was increased.");
                break;
                
            case 3:
                spellEffect[2] += 20;
                System.out.println("You feel more in tune with your spells. Ice Lance's damage was increased.");
                break;
                
            case 4:
                spellEffect[3] += 15;
                System.out.println("You feel more in tune with your spells. Shatter's damage was increased.");
                break;
            default:
                System.out.println("You can come back another time.");
                break;
        }
    }//End upgradeSpell
    
    //End of upgrading spellbook methods.
    
    //Adds RestZones
    public static void restUp(int playerCoords){
        System.out.println("You feel oddly refreshed.");
        playerHealth = playerHealthMax;
        System.out.println("Do you want to set this as your rest zone? <y>/<n>");
        String readString = input.nextLine();
        char userChar = readString.charAt(0);
        if(userChar == 'y'){
            playerRestZone = playerCoords;
        }else{
            System.out.println("You can come back later.");
        }
    }//End restUp
    
    //Create method to assign array strings
    private static void mapsOne(){
        mapFloorOne[0] = String.format("You enter Karazhan, able to smell the fel corruption in the air. Will you walk north into the hallway, or exit to the south? <%c><%c>", north, south);
        mapFloorOne[1] = String.format("You stand in the opening hallway. You see what looks like an indoor stable to the west. \nThere's a dark dungeon to the east, and a staircase to the north. You can exit to the south. <%c><%c><%c><%c>", north, south, east, west);
        mapFloorOne[2] = String.format("In the stables, you see ghosts  that are tending to undead horses. You can head westward deeper into the stables. \nThere also seems to be a hallway heading northward, and the eastern hallway leading back to the entrance. <%c><%c><%c>", north, east, west);
        mapFloorOne[3] = String.format("The blood of Attumen's undead horse smells terrible. \nYou can head either west, further into the stables, south to a staircase, or head east, back to the trisection. <%c><%c><%c>", south, east, west);
        mapFloorOne[4] = String.format("You reach the end of the stables, but nothing's really here. \nReady to head back eastward? <%c>", east);
        mapFloorOne[5] = String.format("You climb the stairs, but the end of the stairway seems to be gated. Head back down? <%c>", north);
        mapFloorOne[6] = String.format("You enter the hallway. Northward, you see a small room covered with bookshelves. The stables are to the south. <%c><%c>", north, south);
        mapFloorOne[7] = String.format("");
        mapFloorOne[8] = String.format("There are bookcases all around. You can visit the bookcases to the north, to the east, or head down the hallway to the stables. <%c><%c><%c>", north, south, east);
        mapFloorOne[9] = String.format("You find a book of good use to you. You can search the other bookcases to the east, or head southwards back to the stables. <%c><%c>", south, east);
        mapFloorOne[10] = String.format("You find more books that are in an unfamiliar language. You can find more books to the west, east, and south. <%c><%c><%c>", south, east, west);
        mapFloorOne[11] = String.format("You see no books of interest here. You can find more books to the west and to the south. <%c><%c>", south, west);
        mapFloorOne[12] = String.format("You see no books of interest here. You can find more books to the west and to the north. <%c><%c>", north, west);
        mapFloorOne[13] = String.format("You find books on fundamental spells you have already learned from the Kirin Tor. \nYou can find more books to the north and to the east, or head to the stables to the west. <%c><%c><%c>", north, east, west);
        mapFloorOne[14] = String.format("You climb the stairs, but the room is gated off. You notice spirits dancing inside, however. Head back down? <%c>", south);
        mapFloorOne[15] = String.format("You head into the dark dungeon. It's dark and hard to see. Spiderwebs fill the ceilings, walls, and floors. \nYou make out an opening to the north and east. You can head west to the main hallway. <%c><%c><%c>", north, east, west);
        mapFloorOne[16] = String.format("The floor is wet and moldy. You can go southward, or head back towards the main hallway to the west. <%c><%c>", south, west);
        mapFloorOne[17] = String.format("Other than the wand, there's nothing of value here. Head north? <%c>", north);
        mapFloorOne[18] = String.format("You find yourself at another hallway. Head east, deeper into the dungeon, or south, heading back to the main hall? <%c><%c>", south, east);
        mapFloorOne[19] = String.format("");
        mapFloorOne[20] = String.format("You're halfway down the hall, but hear something hissing. There are two rooms to the north and south. You can also keep going east or west. <%c><%c><%c><%c>", north, south, east, west);
        mapFloorOne[21] = String.format("In the room, you find nothing but spiderwebs and eggs on empty bookcases. You'd be better off not touching the eggs. Head out? <%c>", south);
        mapFloorOne[22] = String.format("The room's a bit bigger than you thought. Head further down south, or head back north? <%c><%c>", north, south);
        mapFloorOne[23] = String.format("It's pitch black, but you've also reached a dead end. Head back north? <%c>", north);
        mapFloorOne[24] = String.format("");
        mapFloorOne[25] = String.format("You reach the end of the dungeon's hallway. Northward, there seems to be a completely dark hallway. \nThere are two rooms to the south and east. Westward lies back to the main hallway. <%c><%c><%c><%c>", north, south, east, west);
        mapFloorOne[26] = String.format("Besides the blood and odor of the bats, nothing seems to be here. Head out? <%c>", north);
        mapFloorOne[27] = String.format("This is where you defeated Rokad the Ravager. Head east, or westward back to the main hallway? <%c><%c>", east, west);
        mapFloorOne[28] = String.format("You see a bed to the east. Do you want to head to it, or head back east heading towards the main hallway? <%c><%c>", east, west);
        mapFloorOne[29] = String.format("You find a bed. It's in amazingly good condition, considering of the palce that it's in. You rest up. Want to head out? <%c>", west);
        mapFloorOne[30] = String.format("You can see nothing at all. Head north or south? <%c><%c>", north, south);
        mapFloorOne[31] = String.format("You hear faint murmurs, but cannot see anything. Head north or south? <%c><%c>", north, south);
        mapFloorOne[32] = String.format("You take another step, but you can't see anything. Head north or south? <%c><%c>", north, south);
        mapFloorOne[33] = String.format("You take another step, but you can't see anything. Head north or south? <%c><%c>", north, south);
        mapFloorOne[34] = String.format("You reach a wall, but the hallway continues. Head east or south? <%c><%c>", south, east);
        mapFloorOne[35] = String.format("You find a staircase and slowly walk up. It seems like the top is gated off though. Head back west? <%c>", west);
         
    } //End of mapFloorOneMethod
    
    //Creates the movement changes when using player commands
    private static void exitsOne(){
        
        floorOneExits[0] = fillValues(1, 36, 0, 0);
                
        floorOneExits[1] = fillValues(14, 0, 15, 2);
                
        floorOneExits[2] = fillValues(6, 2, 1, 3);
                
        floorOneExits[3] = fillValues(3, 5, 2, 4);
                
        floorOneExits[4] = fillValues(4, 4, 3, 4);
                
        floorOneExits[5] = fillValues(3, 5, 5, 5);
                
        floorOneExits[6] = fillValues(8, 2, 6, 6);
                
        floorOneExits[7] = fillValues(8, 6, 7, 7);
                
        floorOneExits[8] = fillValues(9, 6, 13, 8);
                
        floorOneExits[9] = fillValues(9, 8, 9, 10);
                
        floorOneExits[10] = fillValues(10, 13, 11, 9);
                
        floorOneExits[11] = fillValues(11, 12, 11, 10);
                
        floorOneExits[12] = fillValues(11, 12, 12, 13);
                
        floorOneExits[13] = fillValues(10, 13, 12, 8);
                
        floorOneExits[14] = fillValues(14, 1, 14, 14);
                
        floorOneExits[15] = fillValues(18,15, 16, 1);
                
        floorOneExits[16] = fillValues(16, 17, 16, 15);
                
        floorOneExits[17] = fillValues(16, 17, 17, 17);
                
        floorOneExits[18] = fillValues(18, 15, 20, 18);
                
        floorOneExits[19] = fillValues(19, 19, 20, 18);
                
        floorOneExits[20] = fillValues(21, 22, 25, 18);
                
        floorOneExits[21] = fillValues(21, 20, 21, 21);
                
        floorOneExits[22] = fillValues(20, 23, 22, 22);
                
        floorOneExits[23] = fillValues(22, 23, 23, 23);
                
        floorOneExits[24] = fillValues(24, 24, 25, 20);
                
        floorOneExits[25] = fillValues(30, 26, 27, 20);
                
        floorOneExits[26] = fillValues(25, 26, 26, 26);
                
        floorOneExits[27] = fillValues(27, 27, 28, 25);
                
        floorOneExits[28] = fillValues(28, 28, 29, 27);
                
        floorOneExits[29] = fillValues(29, 29, 29, 27);
                
        floorOneExits[30] = fillValues(31, 25, 30, 30);
                
        floorOneExits[31] = fillValues(32, 30, 31, 31);
                
        floorOneExits[32] = fillValues(33, 31, 32, 32);
                
        floorOneExits[33] = fillValues(34, 32, 33, 33);
                
        floorOneExits[34] = fillValues(34, 33, 34, 34);
                
        floorOneExits[35] = fillValues(35, 35, 35, 34);
    }//End of mapTest
    
    //Fills the tiles that flag for an event.
    private static void tileEventFill(){
        
        //Set coordinates for [x][1] Monsters, [x][2] Treasures, [x][3] Spells, & [x][4] Rest Areas.
        //[x][0] Monsters
        tileEvent[2][0] = true;
        tileEvent[3][0] = true;
        tileEvent[8][0] = true;
        tileEvent[10][0] = true;
        tileEvent[16][0] = true;
        tileEvent[21][0] = true;
        tileEvent[22][0] = true;
        tileEvent[26][0] = true;
        tileEvent[27][0] = true;
        tileEvent[30][0] = true;
        tileEvent[34][0] = true;
        
        //[x][1] Treasures
        tileEvent[4][1] = true;
        tileEvent[17][1] = true;
        tileEvent[23][1] = true;
        tileEvent[28][1] = true;
        
        //[x][2] MagicBook
        tileEvent[9][2] = true;
        
        //[x][3]RestAreas
        tileEvent[29][3] = true;
        
        
    } //End of tileEventFill
    
    //Finds the enemy that the player is fighting.
    private static int[] findEnemy(int playerCoords){
        int skillLine = 0;
        int monsterID = 1;
        if(playerCoords == 2){
            monsterID = 0;
            monsterHealthMax = 200;
            monsterDamage = 20;
            monsterMin = 10;
            monsterMax = 30;
            skillLine = 0;
        }
        if(playerCoords == 3){
            monsterID = 1;
            monsterHealthMax = 400;
            monsterDamage = 30;
            monsterMin = 20;
            monsterMax = 40;
            skillLine = 100;
        }
        if((playerCoords == 8)||(playerCoords == 10)){
            monsterID = 2;
            monsterHealthMax = 250;
            monsterDamage = 25;
            monsterMin = 15;
            monsterMax = 30;
            skillLine = 0;
        }
        if((playerCoords == 16)||(playerCoords == 21)||(playerCoords == 22)){
            monsterID = 3;
            monsterHealthMax = 250;
            monsterDamage = 25;
            monsterMin = 15;
            monsterMax = 32;
            skillLine = 1;
        }
        if((playerCoords == 26)||(playerCoords == 30)){
            monsterID = 4;
            monsterHealthMax = 300;
            monsterDamage = 30;
            monsterMin = 20;
            monsterMax = 35;
            skillLine = 2;
        }
        if(playerCoords == 27){
            monsterID = 5;
            monsterHealthMax = 450;
            monsterDamage = 35;
            monsterMin = 30;
            monsterMax = 50;
            skillLine = 101;
        }
        if(playerCoords == 34){
            monsterID = 6;
            monsterHealthMax = 1500;
            monsterDamage = 50;
            monsterMin = 25;
            monsterMax = 75;
            skillLine = 1;
        }
        //Put the stats into an array to send to combat
        int[] monsterStats = {
                monsterID, monsterHealthMax, monsterDamage,
                monsterMin, monsterMax, skillLine, playerCoords
            };
        
        return monsterStats;
        
    }//End findEnemy
    
    //I am seeing the use of databases here....... Naming monsters.
    public static String findEnemyName(int monsterID){
        if(monsterID == 0){
            monsterName = "Spectral Ghost";
        }
        if(monsterID == 1){
            monsterName = "Attumen the Huntsman";
        }
        if(monsterID == 2){
            monsterName = "Ghost";
        }
        if(monsterID == 3){
            monsterName = "Spider";
        }
        if(monsterID == 4){
            monsterName = "Hound";
        }
        if(monsterID == 5){
            monsterName = "Shadikith the Glider";
        }
        if(monsterID == 6){
            monsterName = "Servant Specter";
        }
        return monsterName;
    }//End findEnemyName
    
    //Creates an array to place values into a spot on the 2D array.
    private static int[] fillValues(int a, int b, int c, int d){
        int[] valueFiller = new int[4];
        valueFiller[0] = a;
        valueFiller[1] = b;
        valueFiller[2] = c;
        valueFiller[3] = d;
        return valueFiller;
    }
    
    //Print map. 
    private static void printMap(boolean[][] mapPrint){
        for(int i = 0; i < 36; i++){
            System.out.printf(tileEvent[i][0] + "\n     " + tileEvent[i][1] + "\n     " + tileEvent[i][2] + "\n     " + tileEvent[i][3] + "\n");
        }
    } //End of printMap
}//End of class