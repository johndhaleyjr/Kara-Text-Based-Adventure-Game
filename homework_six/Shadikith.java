/**
 * Class that creates a monster.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Shadikith
**/

public class Shadikith extends Monster{
    public Shadikith(){
        super();
        setMonsterID(5);
        setName("Shadikith the Glider");
        setHealthMax(450);
        setHealth(this.getHealthMax());
        setDamage(30, 50);
        setSkillLine(101);
    }
    
    //Attack
    @Override
    public int attack(Status playerStatus){
        setSkillMonsterDamage(0);
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
        setSkillMonsterDamage(getDamage());
        System.out.printf("%s bites you for %d damage!\n\n\n", getName(), getSkillMonsterDamage());
        return false;
    }//End tryAttack
    
    public void special(){
        if((getChannelingSpecial() == true)&&(getMonsterCD() == 0)){
            System.out.printf("You are no longer silenced!\n\n\n");
            setChannelingSpecial(false);
            setSpecialAttack(false);
        }
        //Big Attack
        else if(getSpecialAttack() == true){
            System.out.printf("%s shrieks at you, silencing you, making you unable to cast spells!\n\n\n", getName());
            setChannelingSpecial(true);
            setSpecialAttack(false);
            setMonsterCD(5);
        }
    }
    
    public void noEngage(Status playerStatus){
        if((getPoly() > 0)&&(this.getHealth() > 0)){
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