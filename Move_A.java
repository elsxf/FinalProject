public class Move_A extends Action_A{
    public Move_A(Mob m, int relx, int rely){
        super.relX=relx;
        super.relY=rely;
        super.mob=m;
        super.actionCost = mob.getInfo().getSpeed();
        if(relX!=0&&relY!=0){
            super.actionCost=(long)(mob.getInfo().getSpeed()*Math.pow(2,.5));
        }
    }
    public void doo(){//do is a restriceted word
        //System.out.println(mob + " " + mob.getMap().tileMap.get(mob.getX()).get(mob.getY()).getInfo());
        if(!mob.getMap().tileMap.get(mob.getX()+this.relX).get(mob.getY()+this.relY).getInfo().testFlag("[IMPASSABLE]")){
            return;
        }
        if(mob.getMap().tileMap.get(mob.getX()+this.relX).get(mob.getY()+this.relY).getInfo().getMob()==null){
            mob.setCoord(mob.getX()+this.relX,mob.getY()+this.relY);
            return;
        }
        Attack_A a= new Attack_A(this.mob,this.relX,this.relY);
        a.actionCost = 0;
        a.doo();
    }
}