public class Attack_A extends Action_A{
    public Attack_A(Mob m, int relx, int rely){
        super.relX=relx;
        super.relY=rely;
        super.mob=m;
    }
    public void doo(){//do is a restriceted word
        Weapon using = mob.getWeapon();
        Tile attacked = mob.getMap().tileMap.get(this.relX+mob.getX()).get(this.relY+mob.getY());
        if(attacked.getMob()==null){
            //System.out.println(mob+ " attacks but hits only air");
            UI.log(mob+ " attacks but hits only air");
            attacked.setHitFlash(Sprites.MISSFLASH_SP);
            mob.giveEXP("m. attack", 1);
            return;
        }
        if(attacked.getMob().getFaction().equals(mob.getFaction())){
            return;
        }
        else{
            int damage = Sight.dice(using.getNumDice(),using.getDice(),using.getDamMod())-attacked.getMob().getSkills().get("m. block")[0];
            attacked.getMob().giveEXP("m. block", (int)(10*Sight.skillCalc(mob.getSkills().get("m. attack")[0],attacked.getMob().getSkills().get("m. block")[0])));
            //System.out.println(attacked.getMob());0
            mob.giveEXP("m. attack", (int)(10*Sight.skillCalc(attacked.getMob().getSkills().get("m. block")[0], mob.getSkills().get("m. attack")[0])));
            UI.log(mob+" attacks "+attacked.getMob()+" for "+damage+" damage");
            attacked.getMob().setHealth(attacked.getMob().getHealth()[0]-damage);             
            attacked.setHitFlash(Sprites.HITFLASH_SP);
        }
    }
}