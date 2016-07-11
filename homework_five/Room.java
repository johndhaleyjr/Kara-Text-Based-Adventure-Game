/**
 * Class that creates the map of the dungeon.
 * 
 * Author: John Haley
 * Last Revised: 9 July 2016
 * Assignment: Homework Five
 * Class: Room
**/

public class Room{
    EventType eventType;
    
    private int roomNumber;
    private String roomDescription;
    
    private String[] room = new String[36];
    private int[][] exits = new int[36][4];
    private int[] returnExits = new int[4];
    private boolean[][] tileEvent = new boolean[35][4];
    private char north;
    private char south;
    private char east;
    private char west;
    
    public Room(char keyNorth, char keySouth, char keyEast, char keyWest){
        this.eventType = EventType.NONE;
        this.north = keyNorth;
        this.south = keySouth;
        this.east = keyEast;
        this.west = keyWest;//Island city party it up!
        this.room = mapsFill();
        this.exits = exitsFill();
        tileEventFill();
    }
    
    //Start gets
    public String getRoom(int playerCoords){
        return this.room[playerCoords];
    }//end getRoom
    
    public int[] getExits(int playerCoords){
        for(int i = 0; i < 4; i++){
            this.returnExits[i] = this.exits[playerCoords][i];
        }
        return this.returnExits;
     }//End getExits
    
    public int getExits(int playerCoords, int index){
        return this.exits[playerCoords][index];
    }
     
    public EventType getEvent(int playerCoords){
        if(this.tileEvent[playerCoords][0] == true){
            this.eventType = EventType.COMBAT;
        }
        else if(this.tileEvent[playerCoords][1] == true){
            this.eventType = EventType.TREASURE;
        }
        else if(this.tileEvent[playerCoords][2] == true){
            this.eventType = EventType.UPGRADE;
        }
        else if(this.tileEvent[playerCoords][3] == true){
            this.eventType = EventType.RESTAREA;
        }
        else{
            this.eventType = EventType.NONE;
        }
        return this.eventType;
    }
    
    //End gets
    
    public void endEvent(int playerCoords, int index){
        this.tileEvent[playerCoords][index] = false;
    }
    
    public int direction(int playerCoords, char choiceChar){
        if(choiceChar == north){
            playerCoords = getExits(playerCoords, 0);
        }
        else if(choiceChar == south){
            playerCoords = this.getExits(playerCoords, 1);
        }
        else if(choiceChar == east){
            playerCoords = this.getExits(playerCoords, 2);
        }
        else if(choiceChar == west){
            playerCoords = this.getExits(playerCoords, 3);
        }
    
        else if((playerCoords == 36)||(choiceChar == 'q')){
            System.out.println("You leave the tower of Karazhan.");
            System.exit(0);
        }
        
        return playerCoords;
    }
    
    
    private void tileEventFill(){
        
        //Set coordinates for [x][1] Monsters, [x][2] Treasures, [x][3] Spells, & [x][4] Rest Areas.
        //[x][0] Monsters
        this.tileEvent[2][0] = true;
        this.tileEvent[3][0] = true;
        this.tileEvent[8][0] = true;
        this.tileEvent[10][0] = true;
        this.tileEvent[16][0] = true;
        this.tileEvent[21][0] = true;
        this.tileEvent[22][0] = true;
        this.tileEvent[26][0] = true;
        this.tileEvent[27][0] = true;
        this.tileEvent[30][0] = true;
        this.tileEvent[34][0] = true;
        
        //[x][1] Treasures
        this.tileEvent[4][1] = true;
        this.tileEvent[17][1] = true;
        this.tileEvent[23][1] = true;
        this.tileEvent[28][1] = true;
        
        //[x][2] MagicBook
        this.tileEvent[9][2] = true;
        
        //[x][3]RestAreas
        this.tileEvent[29][3] = true;
    }
    
    private String[] mapsFill(){
        room[0]= String.format("You enter Karazhan, able to smell the fel corruption in the air. Will you walk north into the hallway, or exit to the south? <%c><%c>", north, south);
        room[1] = String.format("You stand in the opening hallway. You see what looks like an indoor stable to the west. \nThere's a dark dungeon to the east, and a staircase to the north. You can exit to the south. <%c><%c><%c><%c>", north, south, east, west);
        room[2] = String.format("In the stables, you see ghosts that are tending to undead horses. You can head westward deeper into the stables. \nThere also seems to be a hallway heading northward, and the eastern hallway leading back to the entrance. <%c><%c><%c>", north, east, west);
        room[3] = String.format("The blood of Attumen's undead horse smells terrible. \nYou can head either west, further into the stables, south to a staircase, or head east, back to the trisection. <%c><%c><%c>", south, east, west);
        room[4] = String.format("You reach the end of the stables, but nothing's really here. \nReady to head back eastward? <%c>", east);
        room[5] = String.format("You climb the stairs, but the end of the stairway seems to be gated. Head back down? <%c>", north);
        room[6] = String.format("You enter the hallway. Northward, you see a small room covered with bookshelves. The stables are to the south. <%c><%c>", north, south);
        room[7] = String.format("");
        room[8] = String.format("There are bookcases all around. You can visit the bookcases to the north, to the east, or head down the hallway to the stables. <%c><%c><%c>", north, south, east);
        room[9] = String.format("You find a book of good use to you. You can search the other bookcases to the east, or head southwards back to the stables. <%c><%c>", south, east);
        room[10] = String.format("You find more books that are in an unfamiliar language. You can find more books to the west, east, and south. <%c><%c><%c>", south, east, west);
        room[11] = String.format("You see no books of interest here. You can find more books to the west and to the south. <%c><%c>", south, west);
        room[12] = String.format("You see no books of interest here. You can find more books to the west and to the north. <%c><%c>", north, west);
        room[13] = String.format("You find books on fundamental spells you have already learned from the Kirin Tor. \nYou can find more books to the north and to the east, or head to the stables to the west. <%c><%c><%c>", north, east, west);
        room[14] = String.format("You climb the stairs, but the room is gated off. You notice spirits dancing inside, however. Head back down? <%c>", south);
        room[15] = String.format("You head into the dark dungeon. It's dark and hard to see. Spiderwebs fill the ceilings, walls, and floors. \nYou make out an opening to the north and east. You can head west to the main hallway. <%c><%c><%c>", north, east, west);
        room[16] = String.format("The floor is wet and moldy. You can go southward, or head back towards the main hallway to the west. <%c><%c>", south, west);
        room[17] = String.format("Other than the wand, there's nothing of value here. Head north? <%c>", north);
        room[18] = String.format("You find yourself at another hallway. Head east, deeper into the dungeon, or south, heading back to the main hall? <%c><%c>", south, east);
        room[19] = String.format("");
        room[20] = String.format("You're halfway down the hall, but hear something hissing. There are two rooms to the north and south. You can also keep going east or west. <%c><%c><%c><%c>", north, south, east, west);
        room[21] = String.format("In the room, you find nothing but spiderwebs and eggs on empty bookcases. You'd be better off not touching the eggs. Head out? <%c>", south);
        room[22] = String.format("The room's a bit bigger than you thought. Head further down south, or head back north? <%c><%c>", north, south);
        room[23] = String.format("It's pitch black, but you've also reached a dead end. Head back north? <%c>", north);
        room[24] = String.format("");
        room[25] = String.format("You reach the end of the dungeon's hallway. Northward, there seems to be a completely dark hallway. \nThere are two rooms to the south and east. Westward lies back to the main hallway. <%c><%c><%c><%c>", north, south, east, west);
        room[26] = String.format("Besides the blood and odor of the bats, nothing seems to be here. Head out? <%c>", north);
        room[27] = String.format("This is where you defeated Shadikith. Head east, or westward back to the main hallway? <%c><%c>", east, west);
        room[28] = String.format("You see a bed to the east. Do you want to head to it, or head back east heading towards the main hallway? <%c><%c>", east, west);
        room[29] = String.format("You find a bed. It's in amazingly good condition, considering of the palce that it's in. You rest up. Want to head out? <%c>", west);
        room[30] = String.format("You can see nothing at all. Head north or south? <%c><%c>", north, south);
        room[31] = String.format("You hear faint murmurs, but cannot see anything. Head north or south? <%c><%c>", north, south);
        room[32] = String.format("You take another step, but you can't see anything. Head north or south? <%c><%c>", north, south);
        room[33] = String.format("You take another step, but you can't see anything. Head north or south? <%c><%c>", north, south);
        room[34] = String.format("You reach a wall, but the hallway continues. Head east or south? <%c><%c>", south, east);
        room[35] = String.format("You find a staircase and slowly walk up. It seems like the top is gated off though. Head back west? <%c>", west);
        
        return room;
    }
    
    public int[][] exitsFill(){
        this.exits[0] = fillValues(1, 36, 0, 0);
                
        this.exits[1] = fillValues(14, 0, 15, 2);
                
        this.exits[2] = fillValues(6, 2, 1, 3);
                
        this.exits[3] = fillValues(3, 5, 2, 4);
                
        this.exits[4] = fillValues(4, 4, 3, 4);
                
        this.exits[5] = fillValues(3, 5, 5, 5);
                
        this.exits[6] = fillValues(8, 2, 6, 6);
                
        this.exits[7] = fillValues(8, 6, 7, 7);
                
        this.exits[8] = fillValues(9, 6, 13, 8);
                
        this.exits[9] = fillValues(9, 8, 9, 10);
                
        this.exits[10] = fillValues(10, 13, 11, 9);
                
        this.exits[11] = fillValues(11, 12, 11, 10);
                
        this.exits[12] = fillValues(11, 12, 12, 13);
                
        this.exits[13] = fillValues(10, 13, 12, 8);
                
        this.exits[14] = fillValues(14, 1, 14, 14);
                
        this.exits[15] = fillValues(18,15, 16, 1);
                
        this.exits[16] = fillValues(16, 17, 16, 15);
                
        this.exits[17] = fillValues(16, 17, 17, 17);
                
        this.exits[18] = fillValues(18, 15, 20, 18);
                
        this.exits[19] = fillValues(19, 19, 20, 18);
                
        this.exits[20] = fillValues(21, 22, 25, 18);
                
        this.exits[21] = fillValues(21, 20, 21, 21);
                
        this.exits[22] = fillValues(20, 23, 22, 22);
                
        this.exits[23] = fillValues(22, 23, 23, 23);
                
        this.exits[24] = fillValues(24, 24, 25, 20);
                
        this.exits[25] = fillValues(30, 26, 27, 20);
                
        this.exits[26] = fillValues(25, 26, 26, 26);
                
        this.exits[27] = fillValues(27, 27, 28, 25);
                
        this.exits[28] = fillValues(28, 28, 29, 27);
                
        this.exits[29] = fillValues(29, 29, 29, 27);
                
        this.exits[30] = fillValues(31, 25, 30, 30);
                
        this.exits[31] = fillValues(32, 30, 31, 31);
                
        this.exits[32] = fillValues(33, 31, 32, 32);
                
        this.exits[33] = fillValues(34, 32, 33, 33);
                
        this.exits[34] = fillValues(34, 33, 35, 34);
                
        this.exits[35] = fillValues(35, 35, 35, 34);
        
        return this.exits;
    }//End exits
    
    private int[] fillValues(int a, int b, int c, int d){
        int[] valueFiller = new int[4];
        valueFiller[0] = a;
        valueFiller[1] = b;
        valueFiller[2] = c;
        valueFiller[3] = d;
        return valueFiller;
    }
}//End class