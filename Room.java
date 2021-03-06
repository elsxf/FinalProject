import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Room{
    
    private ArrayList<ArrayList<Tile>> tiles;
    
    public Room(String file, int sides){//#of sides with passageways
        this.tiles = MapReader.readFile(file);
    }
    
    public ArrayList<ArrayList<Tile>> getTiles(int rotate){
        ArrayList<ArrayList<Tile>> result = new ArrayList<ArrayList<Tile>>();
        return this.tiles;
    }
}