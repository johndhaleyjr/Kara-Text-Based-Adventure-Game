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

public class Combat{
    private static int evoCD = 0;
    static Random gen = new Random();
    private int monsterCD;
    private int poly;
    private int overkill;
    private boolean isInterrupt;
    private boolean bigHit;
    private boolean channelingHit;
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
        this.monsterCD = 0;
        this.poly = 0;
        this.overkill = 0;
        this.isInterrupt = false;
        this.bigHit = false;
        this.channelingHit = false;
        this.isBattle = false;
        this.playerStatus = Status.ABLE;
        this.mob = new Monster(playerCoords);
        this.iRan = false;
    }//End Constructor
    
    //Start getters
    
    public int getEvoCD(){
        return this.evoCD;
    }//End getEvoCD
    
    public int getMonsterCD(){
        return this.monsterCD;
    }//End getMonsterCD
    
    public int getPoly(){
        return this.poly;
    }//End getPoly
    
    public int getOverkill(){
        return this.overkill;
    }//end getOverkill
    
    public boolean getIsInterrupt(){
        return this.isInterrupt;
    }//End getIsInterrupt
    
    public boolean getBigHit(){
        return this.bigHit;
    }//End getBigHit
    
    public boolean getChannelingHit(){
        return this.channelingHit;
    }//End getChannelingHit
    
    public boolean getIsBattle(){
        return this.isBattle;
    }//End getIsBattle
    
    public boolean getIsWin(){
        return this.isWin;
    }//End getIsWin
    
    public boolean getIRan(){
        return this.iRan;
    }//end getIRan
    
    public Status getPlayerStatus(){
        return this.playerStatus;
    }//End getPlayerStatus
    
    public Player updatePlayer(){
        return this.player;
    }//end updatePlayer
    
    //End Getters
    //Start Setters
    
    public void setEvoCD(int newCD){
        this.evoCD = newCD;
        player.setEvoCD(this.evoCD);
    }//End setEvoCD
    
    public void setMonsterCD(int newCD){
        this.monsterCD = newCD;
    }//End setMonsterCD
    
    public void setPoly(int newPoly){
        this.poly = newPoly;
    }//End setPoly
    
    public void setPoly(){
        this.poly = gen.nextInt(3) + 1;
    }//End setPoly2
    
    public void setOverkill(int newOK){
        this.overkill = newOK;
    }//End setOverkill
    
    public void setIsInterrupt(boolean newValue){
        this.isInterrupt = newValue;
    }//End interrupt
    
    public void setBigHit(boolean newValue){
        this.bigHit = newValue;
    }//End setBigHit
    
    public void setChannelingHit(boolean newValue){
        this.channelingHit = newValue;
    }//End setChannelingHit
    
    public void setIsBattle(boolean newValue){
        this.isBattle = newValue;
    }//End setIsBattle
    
    public void setIsWin(boolean newValue){
        this.isWin = newValue;
    }//End setIsWin
    
    public void setIRan(boolean didIRun){
        this.iRan = didIRun;
    }//end setIRan
    
    public void setPlayerStatus(Status newStatus){
        playerStatus = newStatus;
    }//End setPlayerStatus
    
    //End Setters
    
    public void printStats(){
        String monsterName = mob.getMonsterName();
        int monsterHealth = mob.getMonsterHealth();
        int monsterHealthMax = mob.getMonsterHealthMax();
        int playerHealth = player.getPlayerHealth();
        int playerHealthMax = player.getPlayerHealthMax();
        int playerMana = player.getPlayerMana();
        int playerManaMax = player.getPlayerManaMax();
        if(playerStatus == Status.ABLE){
            //Print starting monster and player's stats.
            System.out.printf("You are fighting %s!\n", monsterName);
            System.out.printf("%s's Health: %d/%d \n", monsterName, monsterHealth, monsterHealthMax);
            System.out.printf("Your health: %d/%d \n", playerHealth, playerHealthMax);
            System.out.printf("Your mana: " + player.getPlayerMana() + "/" + player.getPlayerManaMax() + "\n\n");
        }
    }//End of printStatus
    
    public void checkStatus(){
        Status playerStatus = getPlayerStatus();
        if(player.getPlayerHealth() < 0){
            setPlayerStatus(Status.DEFEAT);
            checkWin();
        }
        else if(mob.getMonsterHealth() < 0){
            setPlayerStatus(Status.VICTORY);
            checkWin();
        }
        else if(playerStatus == Status.RUN){
            setIRan(true);
            System.out.println(getIRan());
            setPlayerStatus(Status.ABLE);
            checkWin();
        }
        else if(playerStatus == Status.GOBACK){
            if((mob.getMonsterHealth() > 0)&&(player.getPlayerHealth() > 0)){
                setPlayerStatus(Status.ABLE);
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
                System.out.println("   1.) Attack with your " + player.getPlayerWeapon());
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
        boolean channelingHit = getChannelingHit();
        
        switch(playerChoice){
            case 1: 
                if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                    int playerDamage = player.getPlayerDamage();
                    System.out.printf("You attack %s with your %s for %d damage!\n\n", mob.getMonsterName(), player.getPlayerWeapon(), playerDamage);
                    setOverkill(mob.takeHealth(playerDamage));
                }
                if((skillLine == 0)&&(channelingHit == true)){
                    System.out.printf("You cannot attack ghosts in the ethereal!\n\n");
                } 
                player.giveMana(60);
                break;
            
            
            //Opening spellbook * TODO *
            case 2: 
                player.printSB();
                input = new Scanner(System.in);
                int choice = input.nextInt();
                spellChoice(choice);
                
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
                setPlayerStatus(Status.RUN);
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
        int monsterCD = getMonsterCD();
        int spellDamage;
        
        boolean bigHit = getBigHit();
        boolean channelingHit = getChannelingHit();
        
        int playerMana = player.getPlayerMana();
        String monsterName = mob.getMonsterName();
        
        //If the enemy skillLine is 5, and the cooldown is up, the player will not be able to cast.
        if((skillLine != 101)||((skillLine == 101)&&(monsterCD == 0))){
            if(choice == 1){
                System.out.printf("You counterspelled %s, but it drains all of your mana!\n\n", monsterName);
                setIsInterrupt(true);
                setBigHit(false);       //Interrupt stops bigHit
                setChannelingHit(false);
                player.setPlayerMana(0);
                
            }else if(choice == 2){
                if(playerMana >= 60){
                    System.out.printf("You polymorph %s!\n\n", monsterName);
                    setPoly(polyRan()); //Randomizes a number between 1-3.
                    setBigHit(false);
                    setChannelingHit(false);
                }else{
                    System.out.printf("You tried to polymorph %s, but you ran out of mana!\n\n", monsterName);
                }
                player.takeMana(60);
                
            }else if(choice == 3){
                if(playerMana >= 120){
                    System.out.printf("You start charging your Frostbolt.\n\n");
                    setPlayerStatus(Status.CASTING);//Will not attack next round.
                }
                else{
                    System.out.printf("You tried to charge your Frostbolt, but you ran out of mana!\n\n");
                    player.setPlayerMana(0);
                }
                
            }else if(choice == 4){
                if(playerMana >= 180){
                    System.out.printf("You have entered Ice Block, and cannot be damaged.\n\n");
                    setPlayerStatus(Status.ICEBLOCK);//Will not attack next round.
                    setBigHit(false);//Ice Block stops big hit.
                    //If enemy is not ghost, stop big attack
                    if(skillLine != 1){
                        setChannelingHit(false);
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
                setPlayerStatus(Status.GOBACK);
                    
            }else if((choice < 1)||(choice > 7)){ //Could make this line a do while.
                System.out.printf("That is not a valid choice!\n\n");
                setPlayerStatus(Status.GOBACK);
            }
            if((skillLine == 0)&&(channelingHit == true)){
                System.out.printf("You couldn't hit a target in the etheral.\n\n");
            }
        }else{
            System.out.printf("You are silenced, and thus could not attack!\n\n");
        }
    }//end of spellChoice
    
    public int polyRan(){
        Random gen = new Random();
        int polyCount = gen.nextInt(3);
        return(polyCount + 1);
    }//end of polyRan;
    
    public void spellChecker(){
        int statMonsterDamage;
        int spellDamage;
        String statMonsterName = mob.getMonsterName();
        
        Status playerStatus = getPlayerStatus();
        if((playerStatus == Status.CASTING)||(playerStatus == Status.ICEBLOCK)){
            if(playerStatus == Status.CASTING){
                spellDamage = player.getSpellEffect(3);
                System.out.printf("Your Frostbolt hits %s! You dealt %d damage.\n\n", statMonsterName, spellDamage);
                mob.takeHealth(spellDamage);
                setPlayerStatus(Status.ABLE);
            }
               
            if(playerStatus == Status.ICEBLOCK){
                spellDamage = player.getSpellEffect(4);
                System.out.printf("When you leave Ice Block, you are healed for %d!\n\n", spellDamage);
                player.giveHealth(spellDamage);
                setPlayerStatus(Status.ABLE);
                setBigHit(false);
                if(mob.getSkillLine() != 0){
                    setChannelingHit(false);
                }
            }
        }
    }//End spellChecker
    
    //Checks to see what skillLine to give the monster.
    public void skillLineCheck(){
        //Spectral Ghost & Ghosts
        int checkSkillLine = mob.getSkillLine();
        if(checkSkillLine == 0){
            ghostSkills();
        }
        //Spiders
        if(checkSkillLine == 1){
            spiderSkills();
        }
        //Bats
        if(checkSkillLine == 2){
            houndSkills();
        }
        
        //Attumen
        if(checkSkillLine == 100){
            attumenBossSkills();
        }
        //Rokad
        if(checkSkillLine == 101){
            shadikithSkills();
        }
    }//End skillLineCheck
    
    //The next few methods enable the monsters to attack 
    private void ghostSkills(){
        int skillMonsterDamage;
        int monsterCD = getMonsterCD();
        int poly = getPoly();
        boolean bigHit = getBigHit();
        boolean channelingHit = getChannelingHit();
        boolean isInterrupt = getIsInterrupt();
        String monsterName = mob.getMonsterName();
        
        Status playerStatus = getPlayerStatus();
        
        if(monsterCD > 0){
            setMonsterCD(monsterCD - 1);
        }
        if((poly <= 0)&&(mob.getMonsterHealth() > 0)&&((playerStatus == Status.ABLE)||(playerStatus == Status.CASTING))){
            if(channelingHit == false){
                bigHit = bigAttack();//Checking for value.
            }
            
            if((bigHit == false)){
                if(channelingHit == false){
                    if(isInterrupt == false){
                        skillMonsterDamage = mob.getMonsterDamage();
                        System.out.printf("%s haunts you for %d damage!\n\n\n", monsterName, skillMonsterDamage);
                        player.takeHealth(skillMonsterDamage);
                    }
                    if(isInterrupt == true){
                        System.out.printf("%s could not attack.\n\n\n", monsterName);
                        setIsInterrupt(false);
                    }
                }
                
                if(channelingHit == true){
                    skillMonsterDamage = mob.getMonsterDamage() / 2;
                    System.out.printf("%s haunts you for %d damage!\n\n\n", monsterName, skillMonsterDamage);
                    player.takeHealth(skillMonsterDamage);
                }
            }
            //Big Attack Hit, has to come before the Big Attack
            if((channelingHit == true)&&(monsterCD == 0)){
                System.out.printf("%s has shifted out of the etheral plain.\n\n\n", monsterName);
                setChannelingHit(false);
                setBigHit(false);
            }
            //Big Attack
            if(bigHit == true){
                System.out.printf("%s shifts into the ethereal plain.\n\n\n", monsterName);
                    setChannelingHit(true);
                    setBigHit(false);
                    setMonsterCD(polyRan());
            }
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(mob.getMonsterHealth() > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            setPoly(poly - 1);
        }
        //If player is iceblocked, do not hit.
        if(playerStatus == Status.ICEBLOCK){
        System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", monsterName);
        }
    }//End of ghostSkills
        
    private void attumenBossSkills(){
        int skillMonsterDamage;
        int monsterCD = getMonsterCD();
        int poly = getPoly();
        boolean bigHit = getBigHit();
        boolean channelingHit = getChannelingHit();
        boolean isInterrupt = getIsInterrupt();
        String monsterName = mob.getMonsterName();
        
        if((poly <= 0)&&(isInterrupt == false)&&(mob.getMonsterHealth() > 0)&&((playerStatus == Status.ABLE)||(playerStatus == Status.CASTING))){
            if(channelingHit == false){
                setBigHit(bigAttack());//Checking for value.
            }
            
            if((bigHit == false)&&(channelingHit == false)){
                skillMonsterDamage = mob.getMonsterDamage();
                System.out.printf("%s attacks you for %d damage!\n\n\n", monsterName, skillMonsterDamage);
                setOverkill(player.takeHealth(skillMonsterDamage));
            }
            //Big Attack Hit, has to come before the Big Attack
            if(channelingHit == true){
                System.out.printf("%s strikes you down with the help of his steed, dealing %d damage!\n\n\n", monsterName, 200);
                setOverkill(player.takeHealth(200));
                setChannelingHit(false);
            }
            //Big Attack
            if(bigHit == true){
                    System.out.printf("%s is getting ready for a big attack.\n\n\n", monsterName);
                    setChannelingHit(true);
                    setBigHit(false);
            }
        }
        //If interrupted, cancel attack and big hit.
        if((isInterrupt == true)&&(mob.getMonsterHealth() > 0)){
            System.out.printf("%s could not attack.\n\n\n", monsterName);
            setIsInterrupt(false);
            setBigHit(false);
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(mob.getMonsterHealth() > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            setPoly(poly - 1);
        }
        //If player is iceblocked, enemy will not attack.
        if(playerStatus == Status.ICEBLOCK){
            System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", monsterName);
        }
    }//End monsterWillAttack
    
    private void spiderSkills(){
        int skillMonsterDamage;
        int monsterCD = getMonsterCD();
        int poly = getPoly();
        boolean bigHit = getBigHit();
        boolean channelingHit = getChannelingHit();
        boolean isInterrupt = getIsInterrupt();
        String monsterName = mob.getMonsterName();
        
        if(monsterCD > 0){
            setMonsterCD(monsterCD - 1);
        }
        if((poly <= 0)&&(mob.getMonsterHealth() > 0)&&((playerStatus == Status.ABLE)||(playerStatus == Status.CASTING))){
            if(channelingHit == false){
                setBigHit(bigAttack());//Checking for value.
                bigHit = getBigHit();
            }
            
            if((bigHit == false)){
                if(isInterrupt == false){
                    skillMonsterDamage = mob.getMonsterDamage();
                    System.out.printf("%s bites you for %d damage!\n", monsterName, skillMonsterDamage);
                    setOverkill(player.takeHealth(skillMonsterDamage));
                }
                if(isInterrupt == true){
                    System.out.printf("%s could not attack.\n", monsterName);
                    setIsInterrupt(false);
                }
            }
            //Big Attack Hit, has to come before the Big Attack
            if((channelingHit == true)&&(monsterCD == 0)){
                System.out.printf("The poison has been cured.\n");
                setChannelingHit(false);
                setBigHit(false);
            }
            
            if((channelingHit == true)&&(monsterCD >= 0)){
                int poisonDamage = mob.attackDamage(3, 12);
                System.out.printf("The poison hurts you for %d.", poisonDamage);
                setOverkill(player.takeHealth(poisonDamage));
            }
            
            //Big Attack
            if(bigHit == true){
                System.out.printf("%s's fangs have poisoned you!\n", monsterName);
                    setChannelingHit(true);
                    setBigHit(false);
                    setMonsterCD(3);
            }
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(mob.getMonsterHealth() > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n", monsterName);
            setPoly(poly - 1);
        }
        //If player is iceblocked, do not hit.
        if(playerStatus == Status.ICEBLOCK){
        System.out.printf("%s tried to attack you, but you were protected in ice.\n", monsterName);
        }
        System.out.printf("\n\n");
    }//End of ghostSkills
    
    private void houndSkills(){
        int skillMonsterDamage;
        int monsterCD = getMonsterCD();
        int poly = getPoly();
        boolean bigHit = getBigHit();
        boolean channelingHit = getChannelingHit();
        boolean isInterrupt = getIsInterrupt();
        String monsterName = mob.getMonsterName();
        
        if((poly <= 0)&&(isInterrupt == false)&&(mob.getMonsterHealth() > 0)&&((playerStatus == Status.ABLE)||(playerStatus == Status.CASTING))){
            setBigHit(bigAttack());//Checking for value.
            bigHit = getBigHit();
            
            skillMonsterDamage = mob.getMonsterDamage();
            System.out.printf("%s scratches you for %d damage!\n\n\n", monsterName, skillMonsterDamage);
            setOverkill(player.takeHealth(skillMonsterDamage));
            //Big Attack Hit, has to come before the Big Attack
            //Big Attack
            if(bigHit == true){
                    System.out.printf("%s's pups come charging in!\n", monsterName);
                    int pups = polyRan();
                    int pupDamage;
                    for(int i = 0; i < pups; i++){
                        pupDamage = mob.attackDamage(2, 7);
                        System.out.printf("A pup bites you for %d damage!\n", pupDamage);
                        setOverkill(player.takeHealth(pupDamage));
                    }
                    setBigHit(false);
                    System.out.printf("\n\n");
            }
        }
        //If interrupted, cancel attack and big hit.
        if((isInterrupt == true)&&(mob.getMonsterHealth() > 0)){
            System.out.printf("%s could not attack.\n\n\n", monsterName);
            setIsInterrupt(false);
            setBigHit(false);
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(mob.getMonsterHealth() > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            setPoly(poly - 1);
        }
        //If player is iceblocked, enemy will not attack.
        if(playerStatus == Status.ICEBLOCK){
            System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", monsterName);
        }
    }//End monsterWillAttack
    
    private void shadikithSkills(){
        int skillMonsterDamage;
        int monsterCD = getMonsterCD();
        int poly = getPoly();
        boolean bigHit = getBigHit();
        boolean channelingHit = getChannelingHit();
        boolean isInterrupt = getIsInterrupt();
        String monsterName = mob.getMonsterName();
        
        if(monsterCD > 0){
            setMonsterCD(monsterCD - 1);
        }
        if((poly <= 0)&&(mob.getMonsterHealth() > 0)&&((playerStatus == Status.ABLE)||(playerStatus == Status.CASTING))){
            if(channelingHit == false){
                setBigHit(bigAttack());//Checking for value.
                bigHit = getBigHit();
            }
            
            if(isInterrupt == false){
                skillMonsterDamage = mob.getMonsterDamage();
                System.out.printf("%s bites you for %d damage!\n\n\n", monsterName, skillMonsterDamage);
                setOverkill(player.takeHealth(skillMonsterDamage));
            }
            if(isInterrupt == true){
                System.out.printf("%s could not attack.\n\n\n", monsterName);
                setIsInterrupt(false);
            }
            if((channelingHit == true)&&(monsterCD == 0)){
                System.out.printf("You are no longer silenced!\n\n\n");
                setChannelingHit(false);
                setBigHit(false);
            }
            //Big Attack
            if(bigHit == true){
                System.out.printf("%s shrieks at you, silencing you, making you unable to cast spells!\n\n\n", monsterName);
                    setChannelingHit(true);
                    setBigHit(false);
                    setMonsterCD(5);
            }
        }
        //If polymorphed, do not allow to hit.
        if((poly > 0)&&(mob.getMonsterHealth() > 0)){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", monsterName);
            setPoly(poly - 1);
        }
        //If player is iceblocked, do not hit.
        if(playerStatus == Status.ICEBLOCK){
        System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", monsterName);
        }
    }//End of ghostSkills
    
    private boolean bigAttack(){
        Random gen = new Random();
        boolean isBig = false;
        int hitChance = (gen.nextInt(100) + 1);
        if(hitChance <=80){
            isBig = false;
        }
        if(hitChance > 80){
            isBig = true;
        }
        return isBig;
    }//End bigAttack
    
    public void endBattle(){
        String endMonsterName = mob.getMonsterName();
        if(getIsWin() == true){
            System.out.printf("You have defeated %s with a %d damage overkill!\n\n", endMonsterName, getOverkill());
            System.out.printf("You recover %d health for winning the battle!\n", 50);
            player.giveExp(50);
            player.giveHealth(50);
        }if(getIsWin() == false){
            if(playerStatus == Status.DEFEAT){
                System.out.printf("You have been defeated by %s with an overkill of %d damage. What a shame.\n\n", endMonsterName, getOverkill());
                player.setCoords(player.getPlayerRestArea());
                player.setPlayerHealth(player.getPlayerHealthMax());
                System.out.println("You died, however you find yourself alive and well here again.");
            }
            else{
                
            }
        }
    }//end endBattle
}