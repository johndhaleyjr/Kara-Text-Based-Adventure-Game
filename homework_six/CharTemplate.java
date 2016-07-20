/**
 * Class that creates a character template.
 * 
 * Author: John Haley
 * Last Revised: 20 July 2016
 * Assignment: Homework Six
 * Class: CharTemplate
**/
import java.util.Random;

public abstract class CharTemplate{
    private int health;
    private int healthMax;
    private int mana;
    private int manaMax;
    private int damageMin;
    private int damageMax;
    private int damage;
    private String name;
    private Random gen;
    
    public CharTemplate(){
        this.gen = new Random();
    }
    
    public int getHealth(){
        return this.health;
    }
    
    public int getHealthMax(){
        return this.healthMax;
    }
    
    public int getMana(){
        return this.mana;
    }
    
    public int getManaMax(){
        return this.manaMax;
    }
    
    public int getDamageMin(){
        return this.damageMin;
    }
    
    public int getDamageMax(){
        return this.damageMax;
    }
    
    public int getDamage(){
        return attackDamage(this.getDamageMin(), this.getDamageMax());
    }
    
    public String getName(){
        return this.name;
    }
    
    
    public void setHealth(int hp){
        this.health = hp;
    }//End setHealth
    
    public void setHealthMax(int hp){
        this.healthMax = hp;
    }//End setHealthMax
    
    public void setMana(int mp){
        this.mana = mp;
    }//End setMana
    
    public void setManaMax(int mp){
        this.manaMax = mp;
    }//End setManaMax
    
    public void setDamageMin(int min){
        this.health = min;
    }//End setDamageMin
    
    public void setDamageMax(int max){
        this.health = max;
    }//End setDamageMax
    
    public void setDamage(int newMin, int newMax){
        this.damageMin = newMin;
        this.damageMax = newMax;
    }//End setDamage
    
    public void setName(String name){
        this.name = name;
    }//End setName


    public int takeHealth(int healthTaken){
        int overkill = 0;
        this.health -= healthTaken;
        if(this.health < 0){
            overkill = 0 - this.health;
        }
        return overkill;
    }//End of takeHealth
    
    public void giveHealth(int healthGiven){
        this.health += healthGiven;
        if(this.health > getHealthMax()){
            this.health = getHealthMax();
        }
    }//end of giveHealth
    
    public void takeMana(int manaTaken){
        this.mana -= manaTaken;
        if(this.mana < 0){
            this.mana = 0;
        }
    }//end of takeMana
    
    public void giveMana(int manaGiven){
        this.mana += manaGiven;
        if(this.mana > getManaMax()){
            this.mana = getManaMax();
        }
    }//end of giveMana
    
    public void giveHealthMax(int healthMaxGiven){
        this.healthMax += healthMaxGiven;
        this.health += healthMaxGiven;
    }//end giveHealthMax
    
    public void giveManaMax(int manaMaxGiven){
        this.manaMax += manaMaxGiven;
    }//end giveManaMax
    
    
    protected int attackDamage(int minAttack, int maxAttack){
        int attackDif = 0;
        int attackHit = 0;
        attackDif = (maxAttack - minAttack);
        attackHit = gen.nextInt(attackDif);
        attackHit += minAttack;
        return(attackHit);
    }//end of random method
}//End class