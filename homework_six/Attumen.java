/**
 * Class that creates a monster.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Attumen
**/

public class Attumen extends Monster{
    public Attumen(){
        super();
        setMonsterID(1);
        setName("Attumen the Huntsman");
        setHealthMax(400);
        setHealth(this.getHealthMax());
        setDamage(20, 40);
        setSkillLine(100);
    }
    
    //Attack
    @Override
    public int attack(Status playerStatus){
        setSkillMonsterDamage(0);
        System.out.println(getPoly());
        //If monster HP over 0,
        if(getHealth() > 0){
            //If player is attackable,
            if(((playerStatus == Status.ABLE)||(playerStatus == Status.CASTING))&&(getIsInterrupt() == false)){
                //Try to attack
                engage(playerStatus);
                return getSkillMonsterDamage();
            }
            //Don't attack
            noEngage(playerStatus);
                
            if(getMonsterCD() > 0){
                setMonsterCD(getMonsterCD() - 1);
            }
        }
        return 0;
    }//End attack
    
    public void engage(Status playerStatus){
        if(getPoly() <= 0){
            if(tryAttack() == false){
                special();
            }
            checkSpecial();
        }else{
            noEngage(playerStatus);
        }
    }//End engage
    
    public void checkSpecial(){
        if(getPoly() <= 0){
            if(getChannelingSpecial() == false){
                setSpecialAttack(bigAttack());
            }
        }
    }//End checkSpecial
    
    public boolean tryAttack(){
        if(getChannelingSpecial() == false){
            setSkillMonsterDamage(getDamage());
            System.out.printf("%s attacks you for %d damage!\n\n\n", getName(), getSkillMonsterDamage());
            return false;
        }
        else if(getChannelingSpecial() == true){
            setSkillMonsterDamage(200);
            System.out.printf("%s strikes you down with the help of his steed for %d damage!\n\n\n", getName(), getSkillMonsterDamage());
            return true;
        }
        else{
            return false;
        }
    }//End tryAttack
    
    public void special(){
        if((getChannelingSpecial() == true)&&(getMonsterCD() == 0)){
            setSkillMonsterDamage(200);
            System.out.printf("%s strikes you down with the help of his steed, dealing %d damage!\n\n\n", getName(), getSkillMonsterDamage());
            setChannelingSpecial(false);
            setSpecialAttack(false);
        }
        //Big Attack
        else if(getSpecialAttack() == true){
            setChannelingSpecial(true);
            setSpecialAttack(false);
            System.out.printf("%s is getting ready for a big attack.\n\n\n", getName());
        }
    }
    
    public void noEngage(Status playerStatus){
        if(this.getPoly() > 0){
            System.out.printf("%s was polymorphed and could not attack. Baaa!\n\n\n", getName());
            setPoly(getPoly() - 1);
            setChannelingSpecial(false);
        }
        
        else if(getIsInterrupt() == true){
            System.out.printf("%s could not attack.\n\n\n", getName());
            setIsInterrupt(false);
        }
        
        //If player is iceblocked, do not hit.
        else if(playerStatus == Status.ICEBLOCK){
        System.out.printf("%s tried to attack you, but you were protected in ice.\n\n\n", getName());
        }
    }//End noEngage
}//End class
    