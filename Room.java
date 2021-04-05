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
    private int rotate;
    private char symbol;
    public Room(String file, int sides, Map map, int Ox, int Oy, int rotate){// # of sides with passageways
        this.sides=sides;
        this.map = map;
        this.rotate = rotate;
        this.tiles = MapReader.readFile(file, this, Ox, Oy);
    }
    
    public ArrayList<ArrayList<Tile>> getTiles(){
        ArrayList<ArrayList<Tile>> result = (ArrayList<ArrayList<Tile>>)this.tiles.clone();
        for(int i = 0; i<this.rotate;i++){
            result = this.rotate(result);
        }
        return result;
    }
    
    public char getSymbol(){
        return this.symbol;
    }
    
    public void setSymbol(char symbol){
        this.symbol = symbol;
    }
    
    public Map getMap(){
        return this.map;
    }
    
    public int getRotate(){
        return this.rotate;
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