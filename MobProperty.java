import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MobProperty extends Property
{
    private Mob mob;
    private int id;
    private int speed;
    private ImageIcon image;
    private String name;
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
    private Weapon nullWeapon;
    private String faction;
    private String[] flags;
    private double healRate;
    private long healTimer;
    private HashMap<String, int[]> skills;
    public MobProperty(int id, int speed, int MaxHealth, ImageIcon image, String faction, String[] flags, Weapon nullWeapon, String name, int[] skills){
        this.id = id;
        this.name = name;
        this.maxHealth=MaxHealth;
        this.health=MaxHealth;
        this.maxOrgan=Math.max(1,MaxHealth/5);
        this.organ=maxOrgan;
        this.maxBlood = MaxHealth;
        this.blood = maxBlood;
        this.target[0]=0;//these two will be changed later
        this.target[1]=0;
        this.speed=speed;
        this.image=image;
        this.flags=flags;
        this.brain = new Brain(1, mob);
        this.healRate = 10;
        this.healTimer=Turn.g_turn;
        this.faction = faction;
        this.nullWeapon = nullWeapon;
        this.skills = new HashMap<String, int[]>(){
            {
                put("m. attack", new int[]{skills[0],0});
                put("m. block", new int[]{skills[1],0});
                put("dodge", new int[]{skills[2],0});
            }
        };
        
    }
    public MobProperty(MobProperty to){
        this.id = to.getId();
        this.name = to.name;
        this.maxHealth=to.getMaxHealth();
        this.health=this.maxHealth;
        this.maxOrgan=Math.max(1,this.maxHealth/5);
        this.organ=to.maxOrgan;
        this.maxBlood = this.maxHealth;
        this.blood = maxBlood;
        this.target[0]=0;//these two will be changed later
        this.target[1]=0;
        this.speed=to.getSpeed();
        this.image=to.getImage();
        this.flags=to.getFlags();
        this.brain = new Brain(1, mob);
        this.healRate = 10;
        this.healTimer=Turn.g_turn;
        this.faction = to.getFaction();
        this.nullWeapon = to.getWeapon();
        this.skills = Sight.copySkills(to.getSkills());
        
    }
    public void setMob(Mob m){
        this.mob = m;
        this.brain = new Brain(1, this.mob);
        this.target[0]=this.mob.getX();
        this.target[1]=this.mob.getY();
    }
    public Mob getMob(){
        return this.mob;
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
        return(this.mob.getMap());
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
    public String getFaction(){
        return this.faction;
    }
    public void setNextAction(Action_A action){
        this.nextAction = action;
        this.wakeupTurn = Turn.g_turn+this.nextAction.actionCost;
    }
    public long getWakeupTurn(){
        return(this.wakeupTurn);
    }
    public void setSkill(String skill, int level){
        this.skills.get(skill)[0]=level;
    }
    public boolean testFlag(String test){//returns true if flag is not present
        return(java.util.Arrays.asList(this.flags).indexOf(test)==-1);
    }
    public Weapon getWeapon(){
        if(this.weapon==null){
            return(this.nullWeapon);
        }
        return(this.weapon);
    }
    public void setWeapon(Weapon weapon){
        this.weapon=weapon;
    }
    public void setNullWeapon(Weapon weapon){
        this.nullWeapon=weapon;
    }
    public int[] getHealth(){
        return(new int[]{this.health, this.organ, this.blood});
    }
    public int getMaxHealth(){
        return(this.maxHealth);
    }
    public String[] getFlags(){
        return this.flags;
    }
    public String toString(){
        return this.name;
    }
    public void setHealth(int health){
        this.health=Math.min(health,this.maxHealth);
        this.healTimer = this.healTimer+(long)(100*this.healRate);
        if(this.health<=0){
            this.health=0;
            this.die();
        }
    }
    public void setMexHealth(int health){
        this.maxHealth=health;
    }
    public void giveEXP(String skill, int ammount){
        skills.get(skill)[1]+=ammount;
        //System.out.println(skills.get(skill)[1]);
        if(skills.get(skill)[0]+1<=skills.get(skill)[1]/100){
            skills.get(skill)[0]+=1;
            UI.log(this+" "+skill+" reached level "+skills.get(skill)[0]);
        }
    }
    public HashMap<String, int[]> getSkills(){
        return(this.skills);
    }
    public void doNextAction(){//parses nextAction string and executes it, action strings should follw format: "[function name] [arg0] [arg1]" etc e.x: "move", "-1", "0"
        this.nextAction.doo();
        if(this.healTimer<=Turn.g_turn){
            this.setHealth(this.getHealth()[0]+2);
        }
    }
    public Action_A getNextAction(){
        return this.nextAction;
    }
    public void think(){//starting here(think->move->donextaction) keep in relative coords
        int[] movement = this.brain.getMove(new int[][]{{this.target[0]-this.mob.getX(),this.target[1]-this.mob.getY()}});
        this.mob.move(movement[0],movement[1]);
    }
    public void die(){
        if(!testFlag("[PLAYER]")){
            UI.log("YOU DIE");
            UI.log("[Q]uit\t[R]ESTART");
            UI.gameState = "gameOver";
            return;
        }
        this.mob.getMap().mobList.remove(this.mob);
        this.mob.getMap().tileMap.get(this.mob.getX()).get(this.mob.getY()).getInfo().setMob(null);
        this.setNextAction(new Action_A());
        UI.log(this+ " dies");
    }
}
