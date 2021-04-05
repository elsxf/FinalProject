import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Map//each map is a massive 2d arraylist filled with tiles, storing the data of one dungeon level
{
    public ArrayList<ArrayList<Tile>> tileMap = new ArrayList<ArrayList<Tile>>();
    public ArrayList<Mob> mobList= new ArrayList<Mob>();//ALL mobs(not player) go here(can make it map specific it it becomes a problem)
    private int startX;
    private int startY;
    public Room[][] theRooms;
    public Map(){
    
        /*for(int i = 0;i<20;i++){
            tileMap.add(new ArrayList<Tile>());
            for(int j = 0;j<20;j++){
                if(i==0||i==19||j==0||j==19||(i==10&&j==10)){
                    tileMap.get(i).add(new DoorTile(i,j));
                    continue;
                }
                if(i==9&&j<=10){
                    tileMap.get(i).add(new DoorTile(i,j));
                    continue;
                }
                
                tileMap.get(i).add(new FloorTile(i,j));
                
            }
        }*/
        //theRooms = new Room[Sight.randint(2,5)][Sight.randint(2,5)];
        theRooms = new Room[5+Sight.randint(2,5)][5+Sight.randint(2,5)];
        
        for(int i = 0; i<theRooms.length; i++){
            for(int x = 0; x<11; x++){
                tileMap.add(new ArrayList<Tile>());
                for(int j = 0; j<theRooms[0].length; j++){
                    for(int y = 0; y<11; y++){
                        tileMap.get(i*11+x).add(null);
                    }
            }
            }
        }
        
        this.startX = Sight.randint(0,theRooms.length-1);
        this.startY =  Sight.randint(0,theRooms[0].length-1);
        Room r1 = new Room("./raw/maps/Start_M.txt", 4, this,startX,startY, 0);
        theRooms[startX][startY]=r1;
        ArrayList<ArrayList<Tile>> blah = r1.getTiles();
        for(int i = 0; i<blah.size(); i++){
            for(int j = 0; j<blah.get(i).size(); j++){
                /*System.out.println("tilemap "+tileMap.size()+" "+tileMap.get(startX*11).size());
                System.out.println("start "+startX+" "+startY);
                System.out.println("blah "+blah.size()+" "+blah.get(i).size());
                System.out.println("ij "+i+" "+j);*/
                tileMap.get(i+startX*11).set(j+startY*11, blah.get(i).get(j));
            }
        }
        for(Mob m : this.mobList){
            m.giveMap();
        }
        //this.newRoom(startX,startY,1);
        //blah.addAll(r.getTiles(1));
        //this.tileMap = blah;
    }
    public int[] getStart(){
        return(new int[]{this.startX, this.startY});
    }
    public int[] getRelDir(int dir){
        int relX=0;
        int relY=0;
        switch(dir){
            case 0:
                relY = -1;
                break;
            case 1:
                relX = 1;
                break;
            case 2:
                relY = 1;
                break;
            case 3:
                relX = -1;
                break;
        }
        return new int[]{relX, relY};
    }
    public void makeRoom(int oX, int oY, String room, int dir){
        Room r = new Room(room, 0, this, oX, oY, dir);
        theRooms[oX][oY]=r;
        ArrayList<ArrayList<Tile>> blah = r.getTiles();
        for(int i = 0; i<blah.size(); i++){
            for(int j = 0; j<blah.get(0).size(); j++){
                /*System.out.println("tilemap "+tileMap.size()+" "+tileMap.get(startX*11).size());
                System.out.println("start "+startX+" "+startY);
                System.out.println("blah "+blah.size()+" "+blah.get(0).size());
                System.out.println("ij "+i+" "+j);*/
                tileMap.get(i+oX*11).set(j+oY*11, blah.get(i).get(j));
            }
        }
    }
    public void newRoom(int x, int y, int dir){//x and y of base room
        int[] rels = getRelDir(dir);
        int relX = rels[0];
        int relY = rels[1];
        //System.out.println("newroom:"+relX+" "+relY);
        if(theRooms[x+relX][y+relY]!=null){//dont replace rooms
            return;
        }
        makeRoom(x+relX, y+relY, RoomList.allRooms[Sight.randint(0,RoomList.allRooms.length-1)], dir);
        for(Mob m : this.mobList){
            m.giveMap();
        }
    }
}