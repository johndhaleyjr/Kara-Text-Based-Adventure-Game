/**
 * Class that creates a .
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Player
**/

public class Player extends CharTemplate{
    private Spellbook mySpells;
    
    private String Name = "you";
    private int damage;
    private int maxDamage;
    private int minDamage;
    private int evoHeal;
    private int exp;
    private int level;
    private int restArea;
    private int coords;
    private int evoCD;
    
    private String weaponName;
    private int[] Stats = new int[4];
    
    public Player(String name){
        super();
        this.setName(name);
        this.setHealthMax(250);
        this.setHealth(250);
        this.setManaMax(300);
        this.setMana(300);
        this.setDamageMin(10);
        this.setDamageMax(50);
        this.coords = 0;
        this.exp = 0;
        this.level = 1;
        this.restArea = 0;
        this.evoHeal = 30;
        this.evoCD = 0;
        this.mySpells = new Spellbook();
        this.weaponName = "Wand";
    }//end Constructor
    
    //Start of gets:
    
    public int getCoords(){
        return this.coords;   
    }
    
    public int getExp(){
        return this.exp;
    }//End getExp
    
    public int getLevel(){
        return this.level;
    }//End getLevel
    
    public String getWeapon(){
        return this.weaponName;
    }//End getWeapon
    
    public int getRestArea(){
        return this.restArea;
    }//End getRestArea
    
    public int getEvoHeal(){
        return this.evoHeal;
    }//End getEvoHeal
    
    public int getEvoCD(){
        return this.evoCD;
    }//End getEvoCD
    
    public int[] getStats(){
        Stats[0] = getHealth();
        Stats[1] = getHealthMax();
        Stats[2] = getMana();
        Stats[3] = getManaMax();
        return Stats;
    }//End getStats
    
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
        this.coords = newCoords;
    }//End setCoords
    
    public void setExp(int newExp){
        this.exp = newExp;
        levelUp();
    }//End setExp
    
    public void setRestArea(int newRestArea){
        this.restArea = newRestArea;
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
    }//End setWeapon
    
    //End of setters.

    public void giveExp(int expGiven){
        this.exp += expGiven;
        levelUp();
    }//end giveExp
    
    private void levelUp(){
        int levelTester = getExp() / 100 + 1;
        int oldLevel = getLevel();
        int levelReward = 50;
        
        if(this.level < levelTester){
            this.level = levelTester;
            levelReward = 50 * (this.level - oldLevel);
            System.out.println("Congratulations on leveling up to level " + this.level + ", " + getName() + "! Your max health has increased by " + levelReward + "!");
            giveHealthMax(levelReward);
        }
        else if(this.level > levelTester){
            this.level = levelTester;
            levelReward = 50 * (this.level - oldLevel);
            System.out.println(getName() + " has lost a level. Max health reduced by " + levelReward + ".");
            giveHealthMax(levelReward);
        }
    }//end levelUp
    
    public void printSB(){
        mySpells.printSB();
    }
    
    public void goToRestArea(){
        this.coords = getRestArea();
        setHealth(getHealthMax());
    }

    public void addSpellEffect(int spellNumber, int spellDamage){
        mySpells.addEffect(spellNumber, spellDamage);
    }
    
    public void addSpellEffect(int spellDamage){
        for(int i = 3; i < 7; i++){
            mySpells.addEffect(i, spellDamage);
        }
    }
    
}//End class