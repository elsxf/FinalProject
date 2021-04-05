import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TileProperty extends Property
{
    private int id;
    private ImageIcon image;
    private String[] flags;
    private String name;
    private Mob mob;
    private ImageIcon hitFlash=null;
    private int hitFlashTimer=0;
    private Transform transform;
    public TileProperty(int id, ImageIcon image, String[] flags, String name){
        this.id=id;
        this.image=image;
        this.flags=flags;
        this.name=name;
    }
    public TileProperty(int id, ImageIcon image, String[] flags, String name, Transform transform){
        this.id=id;
        this.image=image;
        this.flags=flags;
        this.name=name;
        this.transform = transform;
    }
    public TileProperty(TileProperty to){//copy contructor
        this.id=to.getId();
        this.image=to.getImage();
        this.flags=to.getFlags();
        this.name=to.getName();
        if(to.getTransform()!=null){
            this.transform=to.getTransform();
        }
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
    public Transform getTransform(){
        return this.transform;
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
    public String getName(){
        return(this.name);
    }
    public String toString(){
        return this.name;
    }
    public void open(){
        if(this.testFlag("[OPENABLE]")){
            UI.log("the "+this+" can't be opened");
        }
        else{
            this.flags = ((TileProperty)this.transform.state2).getFlags();
            this.image = ((TileProperty)this.transform.state2).getImage();
            this.name = ((TileProperty)this.transform.state2).getName();
        }
    } 
    public void close(){
        if(this.testFlag("[CLOSEABLE]")){
            UI.log("the "+this+" can't be closed");
        }
        else{
            this.flags = ((TileProperty)this.transform.state1).getFlags();
            this.image = ((TileProperty)this.transform.state1).getImage();
            this.name = ((TileProperty)this.transform.state1).getName();
        }
    }
}
