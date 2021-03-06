public class Open_A extends Action_A{
    public Open_A(Mob m, int relx, int rely){
        super.relX=relx;
        super.relY=rely;
        super.mob=m;
    }
    public void doo(){//do is a restriceted word
        mob.getMap().tileMap.get(mob.getX()+this.relX).get(mob.getY()+this.relY).open();               
    }
}