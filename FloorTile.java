import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class FloorTile extends Tile
{
    private int x;
    private int y;
    private int id=0;
    private ImageIcon image;
    private String[] flags;
    public FloorTile( int x, int y)
    {
        super(x,y,3,Sprites.FLOORTILE1_SP,new String[]{});
        this.x=x;
        this.y=y;
        if((int)(Math.random()*2)==0){
            this.setImage(Sprites.FLOORTILE1_SP);
        }
        else{
            this.setImage(Sprites.FLOORTILE2_SP);
        }
    }

}
