/**
 * Class that creates a monster.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Monster
**/
import java.util.Random;

public abstract class Monster extends CharTemplate{
    private int skillLine;
    private int monsterID;
    private int skillMonsterDamage;
    private int monsterCD;
    private int poly;
    private boolean isInterrupt;
    private boolean specialAttack;
    private boolean channelingSpecial;
    private Random gen;
    
    private int[] monsterStats = new int[3];
    
    public Monster(){
        super();
        this.gen = new Random();
        setName("Target Dummy");
    }//End constructor
    
    //Start of getters
    
    public int getSkillLine(){
        return this.skillLine;
    }//End getSkillLine
    
    public int getMonsterCD(){
        return this.monsterCD;
    }//End getMonsterCD
    
    public int getSkillMonsterDamage(){
        return this.skillMonsterDamage;
    }//End getSkillMonsterDamage
    
    public int getMonsterID(){
        return this.monsterID;
    }//End getMonsterID
    
    public int getPoly(){
        return this.poly;
    }//End getPoly
    
    public boolean getIsInterrupt(){
        return this.isInterrupt;    
    }//End getIsInterrupt
    
    public boolean getSpecialAttack(){
        return this.specialAttack;
    }//End getSpecialAttack
    
    public boolean getChannelingSpecial(){
        return this.channelingSpecial;
    }//End getChannelingSpecial
    
    
    public int[] sendMonsterStats(){
        monsterStats[0] = this.getHealth();
        monsterStats[1] = this.getHealthMax();
        monsterStats[2] = this.getSkillLine();
        return monsterStats;
    }//End sendMonsterStats
    
    //End of getters
    public void setSkillLine(int skillLine){
        this.skillLine = skillLine;
    }//End setSkillLine
    
    public void setPoly(){
        this.poly = this.polyRan();
    }//End setPoly
    
    public void setPoly(int poly){
        this.poly = poly;
    }//End setPoly(int)
    
    public void setMonsterCD(int monsterCD){
        this.monsterCD = monsterCD;
    }//End setMonsterCD
    
    public void setMonsterID(int monsterID){
        this.monsterID = monsterID;
    }//End setMonsterID
    
    public void setSkillMonsterDamage(int skillMonsterDamage){
        this.skillMonsterDamage = skillMonsterDamage;
    }//End setSkillMonsterDamage
    
    public void setIsInterrupt(boolean isInterrupt){
        this.isInterrupt = isInterrupt;
    }//End setIsInterrupt
    
    public void setSpecialAttack(boolean specialAttack){
        this.specialAttack = specialAttack;
    }//End setSpecialAttack
    
    public void setChannelingSpecial(boolean channelingSpecial){
        this.channelingSpecial = channelingSpecial;
    }//End setChannelingSpecial
    
    //End of setters
    
    public abstract int attack(Status playerStatus);
    
    public int polyRan(){
        int polyCount = this.gen.nextInt(3);
        return(polyCount + 1);
    }//end of polyRan;
    
    public boolean bigAttack(){
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
    

}//End class