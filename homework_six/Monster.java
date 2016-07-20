/**
 * Class that creates a monster.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Monster
**/
import java.util.Random;

public class Monster extends CharTemplate{
    private int skillLine;
    private int monsterID;
    private int[] monsterStats = new int[3];
    
    public Monster(int playerCoords){
        super();
        findEnemy(playerCoords);
    }//End constructor
    
    //Start of getters
    
    public int getSkillLine(){
        return this.skillLine;
    }//End getSkillLine
    
    public int[] sendMonsterStats(){
        monsterStats[0] = this.getHealth();
        monsterStats[1] = this.getHealthMax();
        monsterStats[2] = this.getSkillLine();
        return monsterStats;
    }//End sendMonsterStats

    //End of getters

    
    private void findEnemy(int playerCoords){
        if(playerCoords == 2){
            this.monsterID = 0;
            this.setHealthMax(200);
            this.setHealth(this.getHealthMax());
            this.setDamage(10, 30);
            this.skillLine = 0;
        }
        else if(playerCoords == 3){
            this.monsterID = 1;
            this.setHealthMax(400);
            this.setHealth(this.getHealthMax());
            this.setDamage(20, 40);
            this.skillLine = 100;
        }
        else if((playerCoords == 8)||(playerCoords == 10)){
            this.monsterID = 2;
            this.setHealthMax(250);
            this.setHealth(this.getHealthMax());
            this.setDamage(15, 30);
            this.skillLine = 0;
        }
        else if((playerCoords == 16)||(playerCoords == 21)||(playerCoords == 22)){
            this.monsterID = 3;
            this.setHealthMax(250);
            this.setHealth(this.getHealthMax());
            this.setDamage(15, 32);
            this.skillLine = 1;
        }
        else if((playerCoords == 26)||(playerCoords == 30)){
            this.monsterID = 4;
            this.setHealthMax(300);
            this.setHealth(this.getHealthMax());
            this.setDamage(20, 35);
            this.skillLine = 2;
        }
        else if(playerCoords == 27){
            this.monsterID = 5;
            this.setHealthMax(450);
            this.setHealth(this.getHealthMax());
            this.setDamage(30, 50);
            this.skillLine = 101;
        }
        else if(playerCoords == 34){
            this.monsterID = 6;
            this.setHealthMax(1500);
            this.setHealth(this.getHealthMax());
            this.setDamage(25, 75);
            this.skillLine = 1;
        }
        else{
            this.monsterID = 6;
            this.setHealthMax(1500);
            this.setHealth(this.getHealthMax());
            this.setDamage(25, 75);
            this.skillLine = 1;
        }
        findEnemyName(this.monsterID);
    }//end findEnemy
    
    private void findEnemyName(int monsterID){
        if(monsterID == 0){
            this.setName("Spectral Ghost");
        }
        if(monsterID == 1){
            this.setName("Attumen the Huntsman");
        }
        if(monsterID == 2){
            this.setName("Ghost");
        }
        if(monsterID == 3){
            this.setName("Spider");
        }
        if(monsterID == 4){
            this.setName("Hound");
        }
        if(monsterID == 5){
            this.setName("Shadikith the Glider");
        }
        if(monsterID == 6){
            this.setName("Servant Specter");
        }
    }//end findEnemyName;

}//End class