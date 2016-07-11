/**
 * Class that creates a monster.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Monster
**/
import java.util.Random;

public class Monster{
    static Random gen = new Random();
     
    private int monsterHealthMax; //Max HP
    private int monsterDamage;
    private int monsterMax;
    private int monsterMin;
    private int monsterHealth;
    private int skillLine;
    private int monsterID;
    private String monsterName;
    private int[] monsterStats = new int[3];
    
    public Monster(int playerCoords){
        findEnemy(playerCoords);
    }//End constructor
    
    //Start of getters
    
    public int getMonsterHealthMax(){
        return this.monsterHealthMax;
    }//End getMonsterHealthMax
    
    public int getMonsterHealth(){
        return this.monsterHealth;
    }//End getMonsterHealth
    
    public int getMonsterDamage(){
        return attackDamage(this.monsterMin, this.monsterMax);
    }//End getMonsterDamage
    
    public int getSkillLine(){
        return this.skillLine;
    }//End getSkillLine
    
    public String getMonsterName(){
        return this.monsterName;
    }//End getMonsterName
    
    public int[] sendMonsterStats(){
        monsterStats[0] = getMonsterHealth();
        monsterStats[1] = getMonsterHealthMax();
        monsterStats[2] = getSkillLine();
        return monsterStats;
    }//End sendMonsterStats

    //End of getters
    
    public int takeHealth(int healthTaken){
        int overkill = 0;
        this.monsterHealth -= healthTaken;
        if(this.monsterHealth < 0){
            overkill = 0 - this.monsterHealth;
        }
        return overkill;
    }//End takeHealth
    
    public void giveHealth(int healthGiven){
        this.monsterHealth += healthGiven;
        if(this.monsterHealth > getMonsterHealthMax()){
            this.monsterHealth = getMonsterHealthMax();
        }
    }//End giveHealth
    
    private void findEnemy(int playerCoords){
        if(playerCoords == 2){
            this.monsterID = 0;
            this.monsterHealthMax = 200;
            this.monsterHealth = getMonsterHealthMax();
            this.monsterDamage = 20;
            this.monsterMin = 10;
            this.monsterMax = 30;
            this.skillLine = 0;
        }
        else if(playerCoords == 3){
            this.monsterID = 1;
            this.monsterHealthMax = 400;
            this.monsterHealth = getMonsterHealthMax();
            this.monsterDamage = 30;
            this.monsterMin = 20;
            this.monsterMax = 40;
            this.skillLine = 100;
        }
        else if((playerCoords == 8)||(playerCoords == 10)){
            this.monsterID = 2;
            this.monsterHealthMax = 250;
            this.monsterHealth = getMonsterHealthMax();
            this.monsterDamage = 25;
            this.monsterMin = 15;
            this.monsterMax = 30;
            this.skillLine = 0;
        }
        else if((playerCoords == 16)||(playerCoords == 21)||(playerCoords == 22)){
            this.monsterID = 3;
            this.monsterHealthMax = 250;
            this.monsterHealth = getMonsterHealthMax();
            this.monsterDamage = 25;
            this.monsterMin = 15;
            this.monsterMax = 32;
            this.skillLine = 1;
        }
        else if((playerCoords == 26)||(playerCoords == 30)){
            this.monsterID = 4;
            this.monsterHealthMax = 300;
            this.monsterHealth = getMonsterHealthMax();
            this.monsterDamage = 30;
            this.monsterMin = 20;
            this.monsterMax = 35;
            this.skillLine = 2;
        }
        else if(playerCoords == 27){
            this.monsterID = 5;
            this.monsterHealthMax = 450;
            this.monsterHealth = getMonsterHealthMax();
            this.monsterDamage = 35;
            this.monsterMin = 30;
            this.monsterMax = 50;
            this.skillLine = 101;
        }
        else if(playerCoords == 34){
            this.monsterID = 6;
            this.monsterHealthMax = 1500;
            this.monsterHealth = getMonsterHealthMax();
            this.monsterDamage = 50;
            this.monsterMin = 25;
            this.monsterMax = 75;
            this.skillLine = 1;
        }
        else{
            this.monsterID = 6;
            this.monsterHealthMax = 1500;
            this.monsterHealth = getMonsterHealthMax();
            this.monsterDamage = 50;
            this.monsterMin = 25;
            this.monsterMax = 75;
            this.skillLine = 1;
        }
        findEnemyName(this.monsterID);
    }//end findEnemy
    
    private void findEnemyName(int monsterID){
        if(monsterID == 0){
            this.monsterName = "Spectral Ghost";
        }
        if(monsterID == 1){
            this.monsterName = "Attumen the Huntsman";
        }
        if(monsterID == 2){
            this.monsterName = "Ghost";
        }
        if(monsterID == 3){
            this.monsterName = "Spider";
        }
        if(monsterID == 4){
            this.monsterName = "Hound";
        }
        if(monsterID == 5){
            this.monsterName = "Shadikith the Glider";
        }
        if(monsterID == 6){
            this.monsterName = "Servant Specter";
        }
    }//end findEnemyName;
    
    public static int attackDamage(int minAttack, int maxAttack){
        int attackDif = 0;
        int attackHit = 0;
        attackDif = (maxAttack - minAttack);
        attackHit = gen.nextInt(attackDif);
        attackHit += minAttack;
        return(attackHit);
    }//end of random method


}//End class