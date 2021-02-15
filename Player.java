import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Player extends Mob
{
    public Player(int x, int y, Map map){
        super(x,y,0,10,25,Sprites.PLAYER_SP,new String("player"),new String[]{},map);
    }

}
