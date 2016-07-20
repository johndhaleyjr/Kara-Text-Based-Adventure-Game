/**
 * Class that creates spells.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Spellbook
**/


public class Spellbook{
    private int frostboltDamage;
    private int iceblockHealing;
    private int icelanceDamage;
    private int shatterDamage;
    
    private int counterspellManaCost;
    private int polymorphManaCost;
    private int frostboltManaCost;
    private int iceblockManaCost;
    private int icelanceManaCost;
    private int shatterManaCost;
    
    private String counterspell;
    private String polymorph;
    private String frostbolt;
    private String iceblock;
    private String icelance;
    private String shatter;
    
    public Spellbook(){
        this.frostboltDamage = 30;
        this.iceblockHealing = 50;
        this.icelanceDamage = 60;
        this.shatterDamage = 85;
        
        this.counterspell = "Counterspell";
        this.polymorph = "Polymorph";
        this.frostbolt = "Frostbolt";
        this.iceblock = "Ice Block";
        this.icelance = "Ice Lance";
        this.shatter = "Shatter!";
        
        this.counterspellManaCost = 0;
        this.polymorphManaCost = 60;
        this.frostboltManaCost = 120;
        this.iceblockManaCost = 180;
        this.icelanceManaCost = 240;
        this.shatterManaCost = 300;
    }
    
    //Start getters.
    
    public int getFrostboltDamage(){
        return this.frostboltDamage;
    }//End getFrostboltDamage
    
    public int getIceblockHealing(){
        return this.iceblockHealing;
    }//End getIceblockHealing
    
    public int getIcelanceDamage(){
        return this.icelanceDamage;
    }//End getIcelanceDamage
    
    public int getShatterDamage(){
        return this.shatterDamage;
    }//End getShatterDamage
    
    public String getCounterspell(){
        return this.counterspell;
    }//End getCounterspell
    
    public String getPolymorph(){
        return this.polymorph;
    }//End getPolymorph
    
    public String getFrostbolt(){
        return this.frostbolt;
    }//End getFrostbolt
    
    public String getIceblock(){
        return this.iceblock;
    }//End getIceblock
    
    public String getIcelance(){
        return this.icelance;
    }//End getIcelance
    
    public String getShatter(){
        return this.shatter;
    }//End getShatter
    
    public int getCounterspellManaCost(){
        return this.counterspellManaCost;
    }//End getCounterspellMC
    
    public int getPolymorphManaCost(){
        return this.polymorphManaCost;
    }//End getPolymorphMC
    
    public int getFrostboltManaCost(){
        return this.frostboltManaCost;
    }//End getFrostboltMC
    
    public int getIceblockManaCost(){
        return this.iceblockManaCost;
    }//End getIceblockMC
    
    public int getIcelanceManaCost(){
        return this.icelanceManaCost;
    }//End getIcelanceMC
    
    public int getShatterManaCost(){
        return this.shatterManaCost;
    }//End getShatter
    
    //End of getters.
    //Start setters.
    
    public void setFrostboltDamage(int newDamage){
        this.frostboltDamage = newDamage;
    }//End setFrostboltDamage
    
    public void setIceblockHealing(int newHealing){
        this.iceblockHealing = newHealing;
    }//End setIceblockHealing
    
    public void setIcelanceDamage(int newDamage){
        this.icelanceDamage = newDamage;
    }//End setIcelanceDamage
    
    public void setShatterDamage(int newDamage){
        this.shatterDamage = newDamage;
    }//End setShatterDamage
    
    //End setters.
    
    public void addEffect(int spellNumber, int spellDamage){
        if(spellNumber == 3){
            this.frostboltDamage += spellDamage;
        }
        if(spellNumber == 4){
            this.iceblockHealing += spellDamage;//Healing instead of damage.
        }
        if(spellNumber == 5){
            this.icelanceDamage += spellDamage;
        }
        if(spellNumber == 6){
            this.shatterDamage += spellDamage;
        }
    }//end addEffect
    
    public void printSB(){
        System.out.printf("You open your spellbook and search through the spells:\n");
        System.out.printf("   1.) %s (All mana)\n", getCounterspell(), getCounterspellManaCost());
        System.out.printf("   2.) %s (%d mana)\n", getPolymorph(), getPolymorphManaCost());
        System.out.printf("   3.) %s (%d mana)\n", getFrostbolt(), getFrostboltManaCost());
        System.out.printf("   4.) %s (%d mana)\n", getIceblock(), getIceblockManaCost());
        System.out.printf("   5.) %s (%d mana)\n", getIcelance(), getIcelanceManaCost());
        System.out.printf("   6.) %s (%d mana)\n", getShatter(), getShatterManaCost());
        System.out.printf("   7.) Close the spellbook.\n");
    }//End of printSB
    
    
}




/*
System.out.println();
        
        //If the enemy skillLine is 5, and the cooldown is up, the player will not be able to cast.
        if((skillLine != 5)||((skillLine == 5)&&(monsterCD == 0))){
            if(choice == 1){
                System.out.printf("You counterspelled %s, but it drains all of your mana!\n\n", monsterName);
                isInterrupt = true;
                bigHit = false;       //Interrupt stops bigHit
                channelingHit = false;
                playerMana = 0;
            }else if(choice == 2){
                if(playerMana >= 60){
                    System.out.printf("You polymorph %s!\n\n", monsterName);
                    poly = polyRan(); //Randomizes a number between 1-3.
                    playerMana -= 60;
                    bigHit = false;
                    channelingHit = false;
                }else{
                        System.out.printf("You tried to polymorph %s, but you ran out of mana!\n\n", monsterName);
                        playerMana = 0;
                    }
            }else if(choice == 3){
                if(playerMana >= 120){
                    System.out.printf("You start charging your Frostbolt.\n\n");
                    playerStatus = PlayerStatus.CASTING;//Will not attack next round.
                    playerMana -= 120;
                }
                else{
                    System.out.printf("You tried to charge your Frostbolt, but you ran out of mana!\n\n");
                    playerMana = 0;
                }
                
            }else if(choice == 4){
                if(playerMana >= 180){
                    System.out.printf("You have entered Ice Block, and cannot be damaged.\n\n");
                    playerStatus = PlayerStatus.ICEBLOCKED;//Will not attack next round.
                    playerMana -=180;
                    bigHit = false;//Ice Block stops big hit.
                    //If enemy is not ghost, stop big attack
                    if(skillLine != 1){
                        channelingHit = false;
                    }
                }else{
                    System.out.printf("You tried to use Ice Block, but you ran out of mana!\n\n");
                    playerMana = 0;
                }
                
            }else if(choice == 5){
                if(playerMana >= 240){ 
                    if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                        System.out.printf("You instantly cast Ice Lance on %s, dealing %d damage!\n\n", monsterName, spellEffect[2]);
                        monsterHealth -= 60;
                        playerMana -= 240;
                    }
                }else{
                    System.out.printf("You tried to Ice Lance %s, but you ran out of mana!\n\n", monsterName);
                    playerMana = 0;
                }
                
            }else if(choice == 6){
                if(playerMana >= 300){
                    if((skillLine != 0)||((skillLine == 0)&&(channelingHit == false))){
                        System.out.printf("You freeze %s, and then shatter them, dealing %d damage!\n\n", monsterName, spellEffect[3]);
                        monsterHealth -= 85;
                        playerMana -= 300;
                    }
                }else{
                    System.out.printf("You tried to freeze %s, but you ran out of mana!\n\n", monsterName);
                    playerMana = 0;
                }
                    
            }else if(choice == 7){
                playerStatus = PlayerStatus.GOBACK;
                    
            }else if((choice < 1)||(choice > 7)){ //Could make this line a do while.
                System.out.printf("That is not a valid choice!\n\n");
                playerStatus = PlayerStatus.GOBACK;
            }
            if((skillLine == 0)&&(channelingHit == true)){
                System.out.printf("You couldn't hit a target in the etheral.\n\n");
            }
        }else{
            System.out.printf("You are silenced, and thus could not attack!\n\n");
        }
        return playerStatus;
    }//end of spellChoice
    */