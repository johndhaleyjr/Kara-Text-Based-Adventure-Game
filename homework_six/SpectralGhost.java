/**
 * Class that creates a monster.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Ghost
**/

public class SpectralGhost extends Ghost{
    public SpectralGhost(){
        super();
        setMonsterID(2);
        setName("Spectral Ghost");
        setHealthMax(250);
        setHealth(this.getHealthMax());
        setDamage(15, 30);
        setSkillLine(0);
    }
}//End class