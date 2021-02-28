import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Enemy extends Mob
{
    public Enemy(int x, int y, Map map){
        super(x,y,0,13,5,Sprites.ENEMY_SP,new String("hostile"),new String[]{},map);
        this.setNextAction(new String[]{"wait", "0", "0"});
        super.setWeapon(WeaponList.Claws_W);
    }
    
    public String toString(){
        return("The Mutant");
    }

}
