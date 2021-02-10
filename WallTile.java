import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class WallTile extends Tile
{
    private int x;
    private int y;
    private int id=1;
    private ImageIcon image;
    private String[] flags;
    
    public WallTile(int x, int y)
    {
        super(x,y,1,Sprites.WALLTILE1_SP,new String[]{"[IMPASSABLE]", "[SIGHT_BLOCKER]"});
        this.x=x;
        this.y=y;
        if((int)(Math.random()*2)==0){
            this.setImage(Sprites.WALLTILE1_SP);
        }
        else{
            this.setImage(Sprites.WALLTILE2_SP);
        }
        //System.out.println(this.getImage());
    }

}
