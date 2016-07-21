/**
 * Class that creates a monster.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Ghost
**/

public class ServantSpecter extends Ghost{
    public ServantSpecter(){
        super();
        setMonsterID(6);
        setName("Servant Specter");
        setHealthMax(1500);
        setHealth(this.getHealthMax());
        setDamage(25, 75);
        setSkillLine(0);
    }
}//End class