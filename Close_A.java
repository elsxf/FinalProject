public class Close_A extends Action_A{
    public Close_A(Mob m, int relx, int rely){
        super.relX=relx;
        super.relY=rely;
        super.mob=m;
        super.actionCost=m.getInfo().getSpeed()/2;
    }
    public void doo(){//do is a restriceted word
        if(mob.getMap().tileMap.get(mob.getX()+this.relX).get(mob.getY()+this.relY).getInfo().getMob()==null){
            try{
                mob.getMap().tileMap.get(mob.getX()+this.relX).get(mob.getY()+this.relY).getInfo().close();
            }
            catch(Exception e){
                UI.log("can't close that");
            }
        }
        else{
            UI.log(mob.getMap().tileMap.get(mob.getX()+this.relX).get(mob.getY()+this.relY).getInfo()+" can't be closed with something in the way");
        }
    }
}