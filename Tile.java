import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Tile
{
    private int x;
    private int y;
    private TileProperty info;
    
    public Tile(int x, int y, TileProperty info){
        this.x=x;
        this.y=y;
        this.info = info;
    }
    
    public int getX(){
        return(this.x);
    }
    public int getY(){
        return(this.y);
    }
    public TileProperty getInfo(){
        return(this.info);
    }
}
