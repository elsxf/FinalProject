import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Mob
{
    private int x;
    private int y;
    private Map map;
    private MobProperty info;
    public Mob(int x, int y, Map map, MobProperty info){
        this.map=map;
        this.x=x;//+this.map.getStart()[0]*11;
        this.y=y;//+this.map.getStart()[1]*11;
        this.info=info;
        this.info.setMob(this);
        if(this.info.testFlag("[PLAYER]")){
            this.map.mobList.add(this);
        }
        this.info.setNextAction(new Action_A());
    }
    public void giveMap(){//sould only be called by player
       this.map.tileMap.get(this.x).get(this.y).getInfo().setMob(this); 
    }
    public int getX(){
        return(this.x);
    }
    public int getY(){
        return(this.y);
    }
    public Map getMap(){
        return(this.map);
    }
    public MobProperty getInfo(){
        //System.out.println(this.info);
        return(this.info);
    }
    public String toString(){
        return this.info.toString();
    }
    public void setCoord(int x, int y){
        this.map.tileMap.get(this.x).get(this.y).getInfo().setMob(null);
        this.x=x;
        this.y=y;
        this.map.tileMap.get(this.x).get(this.y).getInfo().setMob(this);
    }
    public ArrayList<ArrayList<Integer>> testTileFlag(String test){//returns each tile adjecent to mob that has flag test
        ArrayList<ArrayList<Integer>> results = new ArrayList<ArrayList<Integer>>();
        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                if(!this.map.tileMap.get(this.x+i).get(this.y+j).getInfo().testFlag(test)){
                    results.add(new ArrayList<Integer>(Arrays.asList(i,j)));
                }
            }
        }
        return results;
    }
    public void move(int relX, int relY){//move 1 tile, does nothing if this would move mob thru impassable terrain
        //System.out.println(this+" "+this.x+" "+this.y+" "+this.map.tileMap.get(this.x).get(this.y).getMob());
        Tile tile=null;

        try{
            tile = this.map.tileMap.get(this.x+relX).get(this.y+relY);
        }
        catch(Exception e){
            Action_A a = new Action_A();
            a.actionCost=0;
            info.setNextAction(a);
            //System.out.println(this+"bumps into a wall");
            //UI.log(this+" bumps into a wall");
            return;
        }
        if(this.map.tileMap.get(this.x+relX).get(this.y+relY)==null||(relX==0&&relY==0)){
            info.setNextAction(new Action_A());
        }
        else if(this.map.tileMap.get(this.x+relX).get(this.y+relY).getInfo().testFlag("[IMPASSABLE]")){
            if(this.map.tileMap.get(this.x+relX).get(this.y+relY).getInfo().getMob()==null){//if not ipassable and not wait
                info.setNextAction(new Move_A(this, relX, relY));
            }
            else{//if mob present
                info.setNextAction(new Attack_A(this, relX, relY));//x and y of tile being attacked(actually attack mob on that tile
            }
        }
        else if(!this.map.tileMap.get(this.x+relX).get(this.y+relY).getInfo().testFlag("[OPENABLE]")){
            info.setNextAction(new Open_A(this, relX, relY));
            //System.out.println(this+" open");
        }
        else{//if wait(no moving or trying to move into impassable tile
            Action_A a = new Action_A();
            a.actionCost=0;
            info.setNextAction(a);
            //System.out.println(this+"bumps into a wall");
            //UI.log(this+" bumps into a wall");
        }
    }
}
