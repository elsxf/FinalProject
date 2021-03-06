import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class DoorTile extends Tile
{
    private int x;
    private int y;
    private int id=1;
    
    public DoorTile(int x, int y)
    {
        super(x,y,1,Sprites.DOORC_SP,new String[]{"[IMPASSABLE]", "[SIGHT_BLOCKER]", "[OPENABLE]"}, "Closed Door");
        this.x=x;
        this.y=y;
        //System.out.println(this.getImage());
    }
    
    public void open(){
        super.setFlags(new String[]{"[CLOSEABLE]"});
        super.setImage(Sprites.DOORO_SP);
        super.setName("Open Door");
    }
    
    public void close(){
        super.setFlags(new String[]{"[IMPASSABLE]", "[SIGHT_BLOCKER]", "[OPENABLE]"});
        super.setImage(Sprites.DOORC_SP);
        super.setName("Closed Door");
    }

}
