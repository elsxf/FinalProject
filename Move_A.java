public class Move_A extends Action_A{
    public Move_A(Mob m, int relx, int rely){
        super.relX=relx;
        super.relY=rely;
        super.mob=m;
    }
    public void doo(){//do is a restriceted word
        if(!mob.getMap().tileMap.get(mob.getX()+this.relX).get(mob.getY()+this.relY).testFlag("[IMPASSABLE]")){
            return;
        }
        if(mob.getMap().tileMap.get(mob.getX()+this.relX).get(mob.getY()+this.relY).getMob()==null){
            mob.setCoord(mob.getX()+this.relX,mob.getY()+this.relY);
            return;
        }
        Attack_A a= new Attack_A(this.mob,this.relX,this.relY);
        a.doo();
    }
}