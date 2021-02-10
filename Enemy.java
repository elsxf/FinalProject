import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Enemy extends Mob
{
    public Enemy(int x, int y, Map map){
        super(x,y,0,13,Sprites.ENEMY_SP,map);
        this.setNextAction(new String[]{"move", "0", "0"});
    }

}
