import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Mob
{
    private int x;
    private int y;
    private int id;
    private int speed;
    private ImageIcon image;
    private Map map;
    private String[] nextAction;
    private long wakeupTurn=0;
    private int targetX;//x coord of the tile the mob wants to reach
    private int targetY;//x coord of the tile the mob wants to reach
    private int health;
    private Weapon weapon;
    private String faction;
    private String[] flags;
    public Mob(int x, int y, int id, int speed, int health, ImageIcon image, String faction, String[] flags, Map map){
        this.id = id;
        this.x=x;
        this.y=y;
        this.targetX=x;
        this.targetY=y;
        this.speed=speed;
        this.image=image;
        this.flags=flags;
        this.map=map;
        this.map.tileMap.get(this.x).get(this.y).setMob(this);
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
        return(this.image);
    }
    public int getSpeed(){
        return(this.speed);
    }
    public Map getMap(){
        return(this.map);
    }
    public int getTargetX(){
        return(this.targetX);
    }
    public int getTargetY(){
        return(this.targetY);
    }
    public void setTarget(int x, int y){
        this.targetX=x;
        this.targetY=y;
    }
    public void setCoord(int x, int y){
        this.map.tileMap.get(this.x).get(this.y).setMob(null);
        this.x=x;
        this.y=y;
        this.map.tileMap.get(this.x).get(this.y).setMob(this);
    }
    public void setNextAction(String[] action){
        this.nextAction = action;
    }
    public void setWakeupTurn(long wakeup){
        this.wakeupTurn=wakeup;
    }
    public long getWakeupTurn(){
        return(this.wakeupTurn);
    }
    public boolean testFlag(String test){//returns true if flag is not present
        return(java.util.Arrays.asList(this.flags).indexOf(test)==-1);
    }
    public Weapon getWeapon(){
        return(this.weapon);
    }
    public void setWeapon(Weapon weapon){
        this.weapon=weapon;
    }
    public void doNextAction(){//parses nextAction string and executes it, action strings should follw format: "[function name] [arg0] [arg1]" etc e.x: "move", "-1", "0"
        switch(this.nextAction[0]){
            case("move"):
                if(this.map.tileMap.get(this.x+Integer.parseInt(this.nextAction[1])).get(this.y+Integer.parseInt(this.nextAction[2])).getMob()==null){
                    this.setCoord(Integer.parseInt(this.nextAction[1])+this.x,Integer.parseInt(this.nextAction[2])+this.y);
                    break;
                }
            case("attack"):
                System.out.println(this+" attacks "+this.map.tileMap.get(this.x+Integer.parseInt(this.nextAction[1])).get(this.y+Integer.parseInt(this.nextAction[2])).getMob());
                break;
            case("wait")://do nothing
                //System.out.println(this+" waits");
                break;
                
        }
    }
    
    public void move(int relX, int relY){//move 1 tile, does nothing if this would move mob thru impassable terrain
        //System.out.println(this+" "+this.x+" "+this.y+" "+this.map.tileMap.get(this.x).get(this.y).getMob());
        if(relX==0&&relY==0){
            this.setNextAction(new String[]{"wait"});
            this.setWakeupTurn(Turn.g_turn+5);
        }
        else if(this.map.tileMap.get(this.x+relX).get(this.y+relY).testFlag("[IMPASSABLE]")){
            if(this.map.tileMap.get(this.x+relX).get(this.y+relY).getMob()==null){//if not ipassable and not wait
                this.setNextAction(new String[]{"move", String.valueOf(relX), String.valueOf(relY)});
                this.setWakeupTurn(Turn.g_turn+this.speed);
                if(relX!=0&&relY!=0){
                    this.setWakeupTurn(Turn.g_turn+(long)(this.speed*Math.pow(2,.5)));
                }
            }
            else{//if mob present
                this.setNextAction(new String[]{"attack", String.valueOf(relX), String.valueOf(relY)});//x and y of tile being attacked(actually attack mob on that tile
                this.setWakeupTurn(Turn.g_turn+this.speed);
            }
        }
        else{//if wait(no moving or trying to move into impassable tile
            this.setNextAction(new String[]{"wait"});
            this.setWakeupTurn(Turn.g_turn+1);
            System.out.println(this+"bumps into a wall");
        }
    }
}
