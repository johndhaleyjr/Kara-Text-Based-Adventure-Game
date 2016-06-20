/** Combat Calculator 2
* A program used to battle a boss or monster with different skills and mechanics.
* 
* 
* 
* Creator: John Haley
* Date: 14 June 2016
* Class: CombatCalculator1
*/
import java.util.Scanner;
import java.util.Random;

public class CombatCalculator2
{
    private static Scanner input = new Scanner(System.in);
    private static Random gen = new Random();
    
    //Spell Variables
    private static int evoCD = 0;
    
    //Player Variables
    private static int playerHealthMax = 250; //Max HP
    private static int playerManaMax = 300; //Max MP
    private static int playerMana = 0; //Current Mana
    private static int playerStatus = 1; //Able to attack
    private static int playerDamage = 32;
    private static int playerMaxDamage = 50;
    private static int playerMinDamage = 10;
    private static double playerHealth = 250;
    private static double weaponDamage;
    private static String playerWeapon = "Wand";
    private static String playerSpell = "Coding Error";
    
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
    private static String monsterName = "Grom Hellscream";
    
    private static int gameStatus = 1;
    //1 is Continue, 2 Win, 3 Loss, 4 Run, 5 Cast1, 6 Cast2, 7 GoBack
    
    
    public static void main(String[] args) {
        int loopStatus = 1;
        int choice = 0;
        
        while(loopStatus == 1) {
            gameStatus = changeStatus(gameStatus);
            loopStatus = changeLoop(gameStatus);
            printStats(); //Prints health and mana
            printChoice();//Prints info to choose attack.
            choice = input.nextInt();//Grabs choice from player
            userChoice(choice);//Gives player the choices and enacts them
            if(playerStatus == 7){
                continue;
            }
            monsterWillHit();//Monster attacks
            
            playerStatus = statusChecker(playerStatus);//Depending on player status, does what it needs.
            gameStatus = changeStatus(gameStatus);//Recheck to help end loop.
            loopStatus = changeLoop(gameStatus);//Recheck to help end loop.
      }
      endGame();
    }//End of Main
    
    //Changes the status of the game to WIN or LOSS.
    public static int changeStatus(int gameStatus) {
        if(gameStatus == 7){
            if((monsterHealth > 0)&&(playerHealth > 0)){
                gameStatus = 1;
            }
        }//if Status
        
        //If player or monster defeats eachother, set gameStatus Win or Loss
        if(monsterHealth <= 0){
            gameStatus = 2;//Win
        }
        else if(playerHealth <= 0){
            gameStatus = 3;//Loss
        }
        if(playerStatus == 4){
            gameStatus = 4; //Game over, run away!
        }
        return(gameStatus);
    }//End changeStatus method
    
    //Checks the status of the game to determine whether or not to keep going.
    public static int changeLoop(int gameStatus){
        int keepLoop;
        if((gameStatus == 2)||(gameStatus == 3)||(gameStatus == 4)){
            keepLoop = 2;
        }
        else{
            keepLoop = 1;
        }
        return(keepLoop);
    }
    
    //Method: Print the stats of the battle if teh status calls for it.
    public static void printStats() {
        if(gameStatus == 1){
            //Print starting monster and player's stats.
            System.out.printf("You are fighting %s!\n", monsterName);
            System.out.printf(monsterName + "'s Health: %.0f/%d \n", monsterHealth, monsterHealthMax);
            System.out.printf("Your health: %.0f/%d \n", playerHealth, playerHealthMax);
            System.out.printf("Your mana: " + playerMana + "/" + playerManaMax + "\n\n");
        }
    }//printStatus
    public static void printChoice(){
        if ((playerStatus == 1)||(playerStatus == 7)){
                System.out.println("Choices:");
                System.out.println("   1.) Attack with your " + playerWeapon);
                System.out.println("   2.) Check Spellbook");
                System.out.println("   3.) Evocate   Cooldown: " + evoCD + ")");
                System.out.println("   4.) Run away");
                System.out.printf("What is your course of action? ");
                System.out.printf("\n\n");
        }
    }//End printChoice
    
    public static void userChoice(int choice){
        switch(choice){
            //If attack with weapon
                    case 1: playerDamage = attackDamage(playerMinDamage, playerMaxDamage);
                            System.out.printf("You attack %s with your %s for %d damage!\n\n", monsterName, playerWeapon, playerDamage);
                            monsterHealth = (monsterHealth - playerDamage);
                            if(playerMana <= 240){
                                playerMana += 60;
                            }
                            break;
                    
                    //Opening spellbook       
                    case 2: printSB();
                            choice = input.nextInt();
                            spellChoice(choice);
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
                            
                            
                            break;
                    
                    //If running
                    case 4: System.out.printf("You run from battle.\n\n");
                            playerStatus = 4;
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
        }//End of userChoice
        
        //Choices of spells being put into effect.
        public static void spellChoice(int choice){
            System.out.println("");
            if(choice == 1){
                System.out.printf("You counterspelled %s, but it drains all of your mana!\n\n", monsterName);
                isInterrupt = true;
                bigHit = false;       //Interrupt stops bigHit
                channelingHit = false;
                playerMana = 0;
            } else if(choice == 2){
                if(playerMana >= 60){
                    System.out.printf("You polymorph %s!\n\n", monsterName);
                    poly = polyRan(); //Randomizes a number between 1-3.
                    playerMana -= 60;
                    bigHit = false;
                    channelingHit = false;
                } else{
                        System.out.printf("You tried to polymorph %s, but you ran out of mana!\n\n", monsterName);
                        playerMana = 0;
                    }
            } else if(choice == 3){
                if(playerMana >= 120){
                    System.out.printf("You start charging your Frostbolt.\n\n");
                    playerStatus = 5;//Will not attack next round.
                    playerMana -= 120;
                }
                else{
                    System.out.printf("You tried to charge your Frostbolt, but you ran out of mana!\n\n");
                    playerMana = 0;
                }
                
            } else if(choice == 4){
                if(playerMana >= 180){
                    System.out.printf("You have entered Ice Block, and cannot be damaged.\n\n");
                    playerStatus = 6;//Will not attack next round.
                    playerMana -=180;
                    bigHit = false;//Ice Block stops big hit.
                    channelingHit = false;
                } else{
                    System.out.printf("You tried to use Ice Block, but you ran out of mana!\n\n");
                    playerMana = 0;
                }
                
            } else if(choice == 5){
                if(playerMana >= 240){ 
                    System.out.printf("You instantly cast Ice Lance on %s, dealing %d damage!\n\n", monsterName, 65);
                    monsterHealth -= 60;
                    playerMana -= 240;
                } else{
                    System.out.printf("You tried to Ice Lance %s, but you ran out of mana!\n\n", monsterName);
                    playerMana = 0;
                }
                
            } else if(choice == 6){
                if(playerMana >= 300){
                    System.out.printf("You freeze %s, and then shatter them, dealing %d damage!\n\n", monsterName, 85);
                    monsterHealth -= 85;
                    playerMana -= 300;
                } else{
                    System.out.printf("You tried to freeze %s, but you ran out of mana!\n\n", monsterName);
                    playerMana = 0;
                }
                    
                }else if(choice == 7){
                    playerStatus = 7;
                    
                }else if((choice < 1)||(choice > 7)){ //Could make this line a do while.
                    System.out.printf("That is not a valid choice!\n\n");
                    playerStatus = 7;
                }
                            
        }//end of spellChoice
        
        //prints spellbook
        private static void printSB(){
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
            System.out.printf("   4.) %s (180 mana) (Healing Glyphed)\n", spellThree);
            System.out.printf("   5.) %s (240 mana)\n", spellFour);
            System.out.printf("   6.) %s (300 mana)\n", spellFive);
            System.out.printf("   7.) Close the spellbook.\n");
        }//End of printSB
        
        //Allows more random attacks with min, max values.
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
    
    //Enables the monster to attack
    public static void monsterWillHit(){
        if((poly <= 0)&&(isInterrupt == false)&&(monsterHealth > 0)&&((playerStatus == 1)||(playerStatus == 3))){
            bigHit = bigAttack();//Checking for value.
            if((bigHit == false)&&(channelingHit == false)){
                monsterDamage = attackDamage(monsterMin, monsterMax);
                System.out.printf("%s attacks you for %d damage!\n\n\n", monsterName, monsterDamage);
                playerHealth -= monsterDamage;
            }
            //Big Attack Hit, has to come before the Big Attack
            if(channelingHit == true){
                System.out.printf("%s strikes you down with his weapon Gorehowl, dealing %d damage!\n\n\n", monsterName, 200);
                playerHealth -= 200;
                channelingHit = false;
                bigHit = false;
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
        //If polymorphed, allow big hit to hit once out.
        if((poly > 0)&&(monsterHealth > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            poly -= 1;
        }
    }//End monsterWillAttack
    
    //Checks the status of the player to allow for spells to function.
    public static int statusChecker(int playerStatus){
        if((playerStatus == 5)||(playerStatus == 6)){
            if(playerStatus == 5){
                System.out.printf("Your Frostbolt hits %s! You dealt %d damage.\n\n", monsterName, 55);
                monsterHealth -= 40;
                
                monsterDamage = attackDamage(monsterMin, monsterMax);
                System.out.printf("%s attacks you for %d damage!\n\n\n", monsterName, monsterDamage);
                playerHealth -= monsterDamage;
                playerStatus = 1;
            }
               
            if(playerStatus == 6){
                System.out.printf("When you leave Ice Block, you are healed for %d!\n\n", 100);
                if((playerHealth <= 150)&&(playerHealth > 0)){
                    playerHealth += 100;
                } else{
                    playerHealth = playerHealthMax;
                }
                playerStatus = 1;
                bigHit = false;
                channelingHit = false;
            }
        }
        return(playerStatus);
    }//End statusChecker
    
    //Ends game if game is over
    public static void endGame(){
        if(monsterHealth < 0){
            double overKill = (0 - monsterHealth);
            monsterHealth = 0;
            System.out.printf("You have defeated %s with a %.0f damage overkill!\n\n", monsterName, overKill);
        } else if(playerHealth < 0){
            double overKill = (0 - playerHealth);
            playerHealth = 0;
            System.out.printf("You have been defeated by %s with an overkill of %.0f damage. What a shame.\n\n", monsterName, overKill);
        }
        System.out.printf("End Game Stats:\n");    
        System.out.printf(monsterName + "'s Health: %.0f/%d \n", monsterHealth, monsterHealthMax);
        System.out.printf("Your health: %.0f/%d \n", playerHealth, playerHealthMax);
        System.out.printf("Your mana: " + playerMana + "/" + playerManaMax + "\n\n");
    }//end endGame
    
}//End of Class