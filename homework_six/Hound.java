/**
 * Class that creates a monster.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Hound
**/

public class Hound extends Monster{
    public Hound(){
        super();
        setMonsterID(4);
        setName("Hound");
        setHealthMax(300);
        setHealth(this.getHealthMax());
        setDamage(20, 35);
        setSkillLine(0);
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
        System.out.printf("%s scratches you for %d damage!\n\n\n", getName(), getSkillMonsterDamage());
        if(getChannelingSpecial() == true){
            System.out.printf("%s's pups come charging in!\n", getName());
            int pups = polyRan();
            int pupDamage;
            for(int i = 0; i < pups; i++){
                pupDamage = attackDamage(2, 7);
                System.out.printf("A pup bites you for %d damage!\n", pupDamage);
                setSkillMonsterDamage(getSkillMonsterDamage() + pupDamage);
                setChannelingSpecial(false);
            }
        }
        return false;
    }//End tryAttack
    
    public void special(){
        //Big Attack
        if(getSpecialAttack() == true){
            setChannelingSpecial(true);
            setSpecialAttack(false);
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