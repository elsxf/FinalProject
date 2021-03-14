import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Room{
    
    private ArrayList<ArrayList<Tile>> tiles;
    private int sides;
    private Map map;
    private int Ox;
    private int Oy;
    public Room(String file, int sides, Map map, int Ox, int Oy){// # of sides with passageways
        this.tiles = MapReader.readFile(file, map, Ox, Oy);
        this.sides=sides;
        this.map = map;
    }
    
    public ArrayList<ArrayList<Tile>> getTiles(int rotate){
        ArrayList<ArrayList<Tile>> result = (ArrayList<ArrayList<Tile>>)this.tiles.clone();
        for(int i = 0; i<rotate;i++){
            result = this.rotate(result);
        }
        return result;
    }
    
    public ArrayList<ArrayList<Tile>> rotate(ArrayList<ArrayList<Tile>> toRotate){
        ArrayList<ArrayList<Tile>> newArray = new ArrayList<ArrayList<Tile>>();

      for(int i = 0; i<toRotate.size();i++){
        newArray.add(new ArrayList<Tile>());
      }

      for(int j = 0; j<toRotate.size(); j++){
        for(int i = 0;i<toRotate.get(0).size();i++){
          newArray.get((toRotate.size()-1)-i).add(toRotate.get(j).get(i));
        }
      }
      return newArray;
    }
    
    public int getSides(){
        return this.sides;
    }
}