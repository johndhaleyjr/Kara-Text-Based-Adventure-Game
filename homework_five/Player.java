/**
 * Class that creates a player.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Player
**/
import java.util.Random;

public class Player{
    //import java.util.Random; before the class
    static Random gen = new Random();
    private Spellbook mySpells;
    
    private String playerName = "you";
    private int playerHealthMax; //Max HP
    private int playerManaMax; //Max MP
    private int playerMana; //Current Mana
    private int playerDamage;
    private int playerMaxDamage;
    private int playerMinDamage;
    private int evoHeal;
    private int playerHealth;
    private int playerExp;
    private int playerLevel;
    private int playerRestArea;
    private int playerCoords;
    private int evoCD;
    
    private String weaponName;
    private int[] playerStats = new int[4];
    
    public Player(String name){
        this.playerName = name;
        this.playerCoords = 0;
        this.playerHealthMax = 250;
        this.playerManaMax = 300;
        this. playerMana = 300;
        this.playerMaxDamage = 50;
        this.playerMinDamage = 10;
        this.playerHealth = 250;
        this.playerExp = 0;
        this.playerLevel = 1;
        this.playerRestArea = 0;
        this.evoHeal = 30;
        this.evoCD = 0;
        this.mySpells = new Spellbook();
        this.weaponName = "Wand";
        
    }//end Constructor
    
    //Start of gets:
    
    public String getName(){
        return this.playerName;
    }//End getName
    
    public int getCoords(){
        return this.playerCoords;   
    }
    
    public int getPlayerHealthMax(){
        return this.playerHealthMax;
    }//End getPlayerHealthMax
    
    public int getPlayerManaMax(){
        return this.playerManaMax;
    }//End getPlayerManaMax
    
    public int getPlayerMana(){
        return this.playerMana;
    }//End getPlayerMana
    
    public int getPlayerDamage(){
        return attackDamage(this.playerMinDamage, this.playerMaxDamage);
    }//End getPlayerDamage
    
    public int getPlayerHealth(){
        return this.playerHealth;
    }//End getPlayerHealth
    
    public int getPlayerExp(){
        return this.playerExp;
    }//End getPlayerExp
    
    public int getPlayerLevel(){
        return this.playerLevel;
    }//End getPlayerLevel
    
    public String getPlayerWeapon(){
        return this.weaponName;
    }//End getPlayerWeapon
    
    public int getPlayerRestArea(){
        return this.playerRestArea;
    }//End getPlayerRestArea
    
    public int getEvoHeal(){
        return this.evoHeal;
    }//End getEvoHeal
    
    public int getEvoCD(){
        return this.evoCD;
    }//End getEvoCD
    
    public int[] getPlayerStats(){
        playerStats[0] = getPlayerHealth();
        playerStats[1] = getPlayerHealthMax();
        playerStats[2] = getPlayerMana();
        playerStats[3] = getPlayerManaMax();
        return playerStats;
    }//End getPlayerStats
    
    
    public int getSpellEffect(int spellNumber){
        int spellEffect;
        if(spellNumber == 3){
            spellEffect = mySpells.getFrostboltDamage();
        }
        else if(spellNumber == 4){
            spellEffect = mySpells.getIceblockHealing();
        }
        else if(spellNumber == 5){
            spellEffect = mySpells.getIcelanceDamage();
        }
        else if(spellNumber == 6){
            spellEffect = mySpells.getShatterDamage();
        }
        else{
            return 0;
        }
        
        return spellEffect;
    }//End getSpellEffects
    
    //End of gets.
    //Start of setters
    
    public void setCoords(int newCoords){
        this.playerCoords = newCoords;
    }
    
    public void setPlayerHealthMax(int newHealthMax){
        this.playerHealthMax = newHealthMax;    
    }//End setPlayerHealthMax
    
    public void setPlayerManaMax(int newManaMax){
        this.playerManaMax = newManaMax;
    }//End setPlayerManaMax
    
    public void setPlayerMana(int newMana){
        this.playerMana = newMana;
        if(this.playerMana > getPlayerManaMax()){
            this.playerMana = playerManaMax;
        }
    }//End setPlayerMana
    
    public void setPlayerDamage(int newMin, int newMax){
        this.playerMinDamage = newMin;
        this.playerMaxDamage = newMax;
    }//End setPlayerDamage
    
    public void setPlayerHealth(int newHealth){
        this.playerHealth = newHealth;
        if(this.playerHealth > getPlayerHealthMax()){
            this.playerHealth = getPlayerHealthMax();
        }
    }//End setPlayerHealth
    
    public void setPlayerExp(int newExp){
        this.playerExp = newExp;
        levelUp();
    }//End setPlayerExp
    
    public void setPlayerRestArea(int newRestArea){
        this.playerRestArea = newRestArea;
    }//End newRestArea
    
    public void setEvoHeal(int newEvoHeal){
        this.evoHeal = newEvoHeal;
    }//End setEvoHeal
    
    public void setEvoCD(int newEvoCD){
        this.evoCD = newEvoCD;
        if(this.evoCD > 5){
            this.evoCD = 5;
        }
    }//End setEvoCD
    
    public void setWeapon(String newWeapon){
        this.weaponName = newWeapon;
    }
    
    //End of setters.
    
    //Takes input to deduct from health, returns overkill.
    public int takeHealth(int healthTaken){
        int overkill = 0;
        this.playerHealth -= healthTaken;
        if(this.playerHealth < 0){
            overkill = 0 - this.playerHealth;
        }
        return overkill;
    }//End of takeHealth
    
    //Takes input to add to health.
    public void giveHealth(int healthGiven){
        this.playerHealth += healthGiven;
        if(this.playerHealth > getPlayerHealthMax()){
            this.playerHealth = getPlayerHealthMax();
        }
    }//end of giveHealth
    
    public void takeMana(int manaTaken){
        this.playerMana -= manaTaken;
        if(this.playerMana < 0){
            this.playerMana = 0;
        }
    }//end of takeMana
    
    public void giveMana(int manaGiven){
        this.playerMana += manaGiven;
        if(this.playerMana > getPlayerManaMax()){
            this.playerMana = getPlayerManaMax();
        }
    }//end of giveMana
    
    public void giveHealthMax(int healthMaxGiven){
        this.playerHealthMax += healthMaxGiven;
        this.playerHealth += healthMaxGiven;
    }//end giveHealthMax
    
    public void giveManaMax(int manaMaxGiven){
        this.playerManaMax += manaMaxGiven;
    }//end giveManaMax
    
    public void giveExp(int expGiven){
        this.playerExp += expGiven;
        levelUp();
    }//end giveExp
    
    private void levelUp(){
        int levelTester = getPlayerExp() / 100 + 1;
        int oldLevel = getPlayerLevel();
        int levelReward = 50;
        
        if(this.playerLevel < levelTester){
            this.playerLevel = levelTester;
            levelReward = 50 * (this.playerLevel - oldLevel);
            System.out.println("Congratulations on leveling up to level " + this.playerLevel + ", " + getName() + "! Your max health has increased by " + levelReward + "!");
            giveHealthMax(levelReward);
        }
        else if(this.playerLevel > levelTester){
            this.playerLevel = levelTester;
            levelReward = 50 * (this.playerLevel - oldLevel);
            System.out.println(getName() + " has lost a level. Max health reduced by " + levelReward + ".");
            giveHealthMax(levelReward);
        }
    }//end levelUp
    
    public void printSB(){
        mySpells.printSB();
    }
    
    public void goToRestArea(){
        this.playerCoords = getPlayerRestArea();
        setPlayerHealth(getPlayerHealthMax());
    }

    public void addSpellEffect(int spellNumber, int spellDamage){
        mySpells.addEffect(spellNumber, spellDamage);
    }
    
    public void addSpellEffect(int spellDamage){
        for(int i = 3; i < 7; i++){
            mySpells.addEffect(i, spellDamage);
        }
    }
    
    
    
    private static int attackDamage(int minAttack, int maxAttack){
        int attackDif = 0;
        int attackHit = 0;
        attackDif = (maxAttack - minAttack);
        attackHit = gen.nextInt(attackDif);
        attackHit += minAttack;
        return(attackHit);
    }//end of random method
    
}//End class