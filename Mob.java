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
    private Action_A nextAction;
    private long wakeupTurn=0;
    private int[] target=new int[2];//x coord of the tile the mob wants to reach
    private Brain brain;
    private int maxHealth;
    private int health;
    private int maxOrgan;
    private int organ;
    private int maxBlood;
    private int blood;
    private Weapon weapon;
    private String faction;
    private String[] flags;
    public Mob(int x, int y, int id, int speed, int MaxHealth, ImageIcon image, String faction, String[] flags, Map map){
        this.id = id;
        this.x=x;
        this.y=y;
        this.maxHealth=MaxHealth;
        this.health=MaxHealth;
        this.maxOrgan=Math.max(1,MaxHealth/5);
        this.organ=maxOrgan;
        this.maxBlood = MaxHealth;
        this.blood = maxBlood;
        this.target[0]=x;
        this.target[1]=y;
        this.speed=speed;
        this.image=image;
        this.flags=flags;
        this.map=map;
        this.map.tileMap.get(this.x).get(this.y).setMob(this);
        this.brain = new Brain(1, this);
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
        return(this.target[0]);
    }
    public int getTargetY(){
        return(this.target[1]);
    }
    public void setTarget(int x, int y){
        this.target[0]=x;//target is in absolute coords
        this.target[1]=y;
    }
    public void setCoord(int x, int y){
        this.map.tileMap.get(this.x).get(this.y).setMob(null);
        this.x=x;
        this.y=y;
        this.map.tileMap.get(this.x).get(this.y).setMob(this);
    }
    public void setNextAction(Action_A action){
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
        if(this.weapon==null){
            return(WeaponList.Fist_W);
        }
        return(this.weapon);
    }
    public void setWeapon(Weapon weapon){
        this.weapon=weapon;
    }
    public int[] getHealth(){
        return(new int[]{this.health, this.organ, this.blood});
    }
    public int getMaxHealth(){
        return(this.maxHealth);
    }
    public void setHealth(int health){
        this.health=Math.min(health,this.maxHealth);
        if(this.health<=0){
            this.health=0;
            this.die();
        }
    }
    public void setMexHealth(int health){
        this.maxHealth=health;
    }
    public void doNextAction(){//parses nextAction string and executes it, action strings should follw format: "[function name] [arg0] [arg1]" etc e.x: "move", "-1", "0"
        this.nextAction.doo();
    }
    public Action_A getNextAction(){
        return this.nextAction;
    }
    
    public void move(int relX, int relY){//move 1 tile, does nothing if this would move mob thru impassable terrain
        //System.out.println(this+" "+this.x+" "+this.y+" "+this.map.tileMap.get(this.x).get(this.y).getMob());
        
        if(relX==0&&relY==0){
            this.setNextAction(new Action_A());
            this.setWakeupTurn(Turn.g_turn+5);
        }
        else if(this.map.tileMap.get(this.x+relX).get(this.y+relY).testFlag("[IMPASSABLE]")){
            if(this.map.tileMap.get(this.x+relX).get(this.y+relY).getMob()==null){//if not ipassable and not wait
                this.setNextAction(new Move_A(this, relX, relY));
                this.setWakeupTurn(Turn.g_turn+this.speed);
                if(relX!=0&&relY!=0){
                    this.setWakeupTurn(Turn.g_turn+(long)(this.speed*Math.pow(2,.5)));
                }
            }
            else{//if mob present
                this.setNextAction(new Attack_A(this, relX, relY));//x and y of tile being attacked(actually attack mob on that tile
                this.setWakeupTurn(Turn.g_turn+(long)(this.speed*this.getWeapon().getSpeedMod()));
            }
        }
        else if(!this.map.tileMap.get(this.x+relX).get(this.y+relY).testFlag("[OPENABLE]")){
            this.setNextAction(new Open_A(this, relX, relY));
            this.setWakeupTurn(Turn.g_turn+(long)(this.speed));
            //System.out.println(this+" open");
        }
        else{//if wait(no moving or trying to move into impassable tile
            this.setNextAction(new Action_A());
            this.setWakeupTurn(Turn.g_turn+1);
            //System.out.println(this+"bumps into a wall");
            UI.log(this+" bumps into a wall");
        }
    }
    public void think(){//starting here(think->move->donextaction) keep in relative coords
        int[] movement = this.brain.getMove(new int[][]{{this.target[0]-this.x,this.target[1]-this.y}});
        this.move(movement[0],movement[1]);
    }
    public void die(){
        this.map.mobList.remove(this);
        this.map.tileMap.get(this.x).get(this.y).setMob(null);
        UI.log(this+ " dies");
    }
}
