import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Map//each map is a massive 2d arraylist filled with tiles, storing the data of one dungeon level
{
    public static ArrayList<ArrayList<Tile>> tileMap = new ArrayList<ArrayList<Tile>>();
    public static ArrayList<Mob> mobList= new ArrayList<Mob>();//ALL mobs(not player) go here(can make it map specific it it becomes a problem)
    public Map()
    {
        for(int i = 0;i<20;i++){
            tileMap.add(new ArrayList<Tile>());
            for(int j = 0;j<20;j++){
                if(i==0||i==19||j==0||j==19||(i==10&&j==10)/*||(i%2==0&&j%2==0)*/){
                    tileMap.get(i).add(new WallTile(i,j));
                    continue;
                }
                
                tileMap.get(i).add(new FloorTile(i,j));
            }
        }
        
    }
}
