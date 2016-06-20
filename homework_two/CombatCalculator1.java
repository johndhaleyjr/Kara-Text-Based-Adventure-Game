/** Combat Calculator 1
* A program used to battle a boss or monster with different skills and mechanics.
* 
* I promise to remake this project once we learn more about methods to make it
* look better.
* 
* 
* Creator: John Haley
* Date: 7 June 2016
* Class: CombatCalculator1
*/
import java.util.Scanner;
import java.util.Random;

public class CombatCalculator1
{
    
    public static void main(String[] args){
        //Initialize player and monster variables.
        Scanner input = new Scanner(System.in);
        
        //Spell Variables
        int evoCD = 0;
        String spellZero = "Counterspell";
        String spellOne = "Polymorph";
        String spellTwo = "Frostbolt";
        String spellThree = "Ice Block";
        String spellFour = "Ice Lance";
        String spellFive = "Shatter!";
        String evoMain = "   3.) Evocate to regain health and mana. (";
        
        //Player Variables
        int playerHealthMax = 250; //Max HP
        int playerManaMax = 300; //Max MP
        int playerMana = 0; //Current Mana
        int playerStatus = 1; //Able to attack
        int playerDamage = 32;
        int playerMaxDamage = 50;
        int playerMinDamage = 10;
        double playerHealth = 250;
        double weaponDamage;
        String playerWeapon = "Wand";
        String playerSpell = "Coding Error";

        //Monster Variables
        int monsterHealthMax = 400; //Max HP
        int monsterDamage = 28;
        int monsterMax = 40;
        int monsterMin = 20;
        int poly = 0;
        double monsterHealth = 400;
        boolean isInterrupt = false;
        boolean bigHit = false;
        boolean channelingHit = false;
        String monsterName = "Grom Hellscream";
        
        while((monsterHealth > 0)&&(playerHealth > 0)&&(playerStatus != 4)){
            //Fixing playerStatus bug with infinite loop.
            if(playerStatus == 5){
                playerStatus = 1;
            }
            
            //Print starting monster and player's stats.
            System.out.printf("You are fighting %s!\n", monsterName);
            System.out.printf(monsterName + "'s Health: %.0f/%d \n", monsterHealth, monsterHealthMax);
            System.out.printf("Your health: %.0f/%d \n", playerHealth, playerHealthMax);
            System.out.printf("Your mana: " + playerMana + "/" + playerManaMax + "\n\n");
            
            //Print choices
            System.out.println("Choices:");
            if (playerStatus == 1){
                System.out.println("   1.) Attack with your " + playerWeapon);
                System.out.println("   2.) Check Spellbook");
                System.out.println(evoMain + "Cooldown: " + evoCD + ")");
                System.out.println("   4.) Run away");
                
                //Get player input
                System.out.printf("What is your course of action? ");
                int playerCommand = input.nextInt();
                System.out.printf("\n\n");
                
                //Calculate and print result
                switch(playerCommand){
                    
                    //If attack with weapon
                    case 1: playerDamage = attackDamage(playerMinDamage, playerMaxDamage);
                            System.out.printf("You attack %s with your %s for %d damage!\n\n", monsterName, playerWeapon, playerDamage);
                            monsterHealth = (monsterHealth - playerDamage);
                            if(playerMana <= 240){
                                playerMana += 60;
                            }
                            break;
                    
                    //Opening spellbook       
                    case 2: System.out.printf("You open your spellbook and search through the spells:\n");
                            System.out.printf("   1.) %s (0 mana)\n", spellZero);
                            System.out.printf("   2.) %s (60 mana)\n", spellOne);
                            System.out.printf("   3.) %s (120 mana)\n", spellTwo);
                            System.out.printf("   4.) %s (180 mana) (Healing Glyphed)\n", spellThree);
                            System.out.printf("   5.) %s (240 mana)\n", spellFour);
                            System.out.printf("   6.) %s (300 mana)\n", spellFive);
                            System.out.printf("   7.) Close the spellbook.\n");
                            
                            //I'm sure that I can use a method to organize this, but I don't know how yet.
                            playerCommand = input.nextInt();
                            System.out.println("");
                            if(playerCommand == 1){
                                System.out.printf("You counterspelled %s, but it drains all of your mana!\n\n", monsterName);
                                isInterrupt = true;
                                bigHit = false;       //Interrupt stops bigHit
                                channelingHit = false;
                                playerMana = 0;
                            } else if(playerCommand == 2){
                                if(playerMana >= 60){
                                    System.out.printf("You polymorph %s!\n\n", monsterName);
                                    poly = polyRan(); //Randomizes a number between 1-3.
                                    playerMana -= 60;
                               } else{
                                    System.out.printf("You tried to polymorph %s, but you ran out of mana!\n\n", monsterName);
                                    playerMana = 0;
                                }
                            } else if(playerCommand == 3){
                                if(playerMana >= 120){
                                    System.out.printf("You start charging your Frostbolt.\n\n");
                                    playerStatus = 2;//Will not attack next round.
                                    playerMana -= 120;
                                }
                                else{
                                    System.out.printf("You tried to charge your Frostbolt, but you ran out of mana!\n\n");
                                    playerMana = 0;
                                }
                            } else if(playerCommand == 4){
                                if(playerMana >= 180){
                                    System.out.printf("You have entered Ice Block, and cannot be damaged.\n\n");
                                    playerStatus = 3;//Will not attack next round.
                                    playerMana -=180;
                                    bigHit = false;//Ice Block stops big hit.
                                    channelingHit = false;
                                } else{
                                    System.out.printf("You tried to use Ice Block, but you ran out of mana!\n\n");
                                    playerMana = 0;
                                }
                            } else if(playerCommand == 5){
                                if(playerMana >= 240){ 
                                    System.out.printf("You instantly cast Ice Lance on %s, dealing %d damage!\n\n", monsterName, 65);
                                    monsterHealth -= 60;
                                    playerMana -= 240;
                                } else{
                                    System.out.printf("You tried to Ice Lance %s, but you ran out of mana!\n\n", monsterName);
                                    playerMana = 0;
                                }
                            } else if(playerCommand == 6){
                                if(playerMana >= 300){
                                    System.out.printf("You freeze %s, and then shatter them, dealing %d damage!\n\n", monsterName, 85);
                                    monsterHealth -= 85;
                                    playerMana -= 300;
                                } else{
                                    System.out.printf("You tried to freeze %s, but you ran out of mana!\n\n", monsterName);
                                    playerMana = 0;
                                }
                                
                            }else if(playerCommand == 7){
                                playerStatus = 5;
                            }else if((playerCommand < 1)||(playerCommand > 7)){ //Could make this line a do while.
                                System.out.printf("That is not a valid choice!\n\n");
                                playerStatus = 5;
                            }
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
                
                //If not polymorphed nor interrupted
                if((poly <= 0)&&(isInterrupt == false)&&(monsterHealth > 0)&&(playerStatus < 3)){
                    
                    bigHit = bigAttack();//Checking for value.
                    if((bigHit == false)||(channelingHit == false)){
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
                
            } //End ifAlive
            
            //Player Statuses
            if((playerStatus == 2)||(playerStatus == 3)){
                if(playerStatus == 2){
                    System.out.printf("Your Frostbolt hits %s! You dealt %d damage.\n\n", monsterName, 55);
                    monsterHealth -= 40;
                    playerStatus = 1;
                }
                
                if(playerStatus == 3){
                    System.out.printf("When you leave Ice Block, you are healed for %d!\n\n", 100);
                    if((playerHealth <= 150)&&(playerHealth > 0)){
                        playerHealth += 100;
                        bigHit = false;
                        channelingHit = false; //Stops any big hit on you.
                    } else{
                        playerHealth = playerHealthMax;
                    }
                    playerStatus = 1;
                }
                if((playerStatus >= 3)&&(playerStatus <= 5)){
                    monsterDamage = attackDamage(monsterMin, monsterMax);
                    System.out.printf("%s attacks you for %d damage!\n\n\n", monsterName, monsterDamage);
                    playerHealth -= monsterDamage;
                }
            }
            if((evoCD > 0)&&(playerStatus != 5)){
                evoCD--;
            }
        }//End of while
        
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
    } //End of main method.
    
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
    }
    
} // End of class.
