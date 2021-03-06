import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Tile
{
    private int x;
    private int y;
    private int id;
    private ImageIcon image;
    private String[] flags;
    private String name;
    private Mob mob;
    private ImageIcon hitFlash=null;
    private int hitFlashTimer=0;
    
    public Tile(int x, int y, int id, ImageIcon image, String[] flags, String name){
        this.x=x;
        this.y=y;
        this.id=id;
        this.image=image;
        this.flags=flags;
        this.name=name;
    }
    
    public int getX(){
        return(this.x);
    }
    public int getY(){
        return(this.y);
    }
    public int getId(){
        return(this.id);
    }
    public ImageIcon getImage(){
        //System.out.println(this.image);
        return(this.image);
    }
    public void setImage(ImageIcon image){
        this.image = image;
    }
    public String[] getFlags(){
        return(this.flags);
    }
    public void setFlags(String[] flags){
        this.flags = flags;
    }
    public void setMob(Mob mob){
        this.mob=mob;
    }
    public Mob getMob(){
        return(this.mob);
    }
    public void setHitFlash(ImageIcon hitFlash){
        this.hitFlash = hitFlash;
        this.hitFlashTimer=5;
    }
    public ImageIcon getHitFlash(){
        return(this.hitFlash);
    }
    public int getHitFlashTimer(){
        return(this.hitFlashTimer);
    }
    public void setHitFlashTimer(int set){
        this.hitFlashTimer = Math.max(0,set);
    }
    public boolean testFlag(String test){//returns true if flag is not present
        return(java.util.Arrays.asList(this.flags).indexOf(test)==-1);
    }
    public void setName(String name){
        this.name = name;
    }
    public String toString(){
        return this.name;
    }
    public void open(){
        UI.log("the "+this+" can't be opened");
    } 
    public void close(){
        UI.log("the "+this+" can't be closed");
    }
}
