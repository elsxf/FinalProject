public class Attack_A extends Action_A{
    public Attack_A(Mob m, int relx, int rely){
        super.relX=relx;
        super.relY=rely;
        super.mob=m;
        super.actionCost=(long)(Math.max(1,mob.getInfo().getSpeed()*mob.getInfo().getWeapon().getSpeedMod()/Sight.minMax(1,3,mob.getInfo().getSkills().get("m. attack")[0])));
    }
    public void doo(){//do is a restriceted word
        MobProperty info = mob.getInfo();
        Weapon using = info.getWeapon();
        Tile attacked = mob.getMap().tileMap.get(this.relX+mob.getX()).get(this.relY+mob.getY());
        if(attacked.getInfo().getMob()==null){
            //System.out.println(mob+ " attacks but hits only air");
            /*UI.log(mob+ " attacks but hits only air");
            attacked.setHitFlash(Sprites.MISSFLASH_SP);
            mob.giveEXP("m. attack", 1);
            */
           Move_A a = new Move_A(mob, relX, relY);
           a.actionCost-=super.actionCost;//move action with cost of attack action already paid
            info.setNextAction(a);
            return;
        }
        if(!attacked.getInfo().getMob().getInfo().getFaction().equals(info.getFaction())){
            //see if attack hits
            //System.out.println(mob.getInfo().getHealth()[0]);
            int toHit = Sight.dice(3,6,0)+info.getWeapon().getHitMod()+info.getSkills().get("m. attack")[0]
            -attacked.getInfo().getMob().getInfo().getSkills().get("dodge")[0]*2;
            attacked.getInfo().getMob().getInfo().giveEXP("dodge", (int)(Sight.skillCalc(toHit-10)));
            info.giveEXP("m. attack", (int)(Sight.skillCalc(10-toHit)));//m. attack is exercised 2 times, only give it 1l2 xp each time
            if(!(toHit>=10)){//miss case
                UI.log(mob+ " attacks but hits only air");
                attacked.getInfo().setHitFlash(Sprites.MISSFLASH_SP);
                return;
            }
            //deal damage if atack his
            int damage = Math.max(1,Sight.dice(using.getNumDice(),using.getDice(),using.getDamMod())-attacked.getInfo().getMob().getInfo().getSkills().get("m. block")[0]);
            attacked.getInfo().getMob().getInfo().giveEXP("m. block", (int)(Sight.skillCalc(damage)));
            info.giveEXP("m. attack", (int)(Sight.skillCalc(attacked.getInfo().getMob().getInfo().getSkills().get("m. block")[0]-info.getSkills().get("m. attack")[0])));//m. attack is exercised 2 times, only give it 1l2 xp each time
            UI.log(mob+" attacks "+attacked.getInfo().getMob()+" for "+damage+" damage");
            attacked.getInfo().getMob().getInfo().setHealth(attacked.getInfo().getMob().getInfo().getHealth()[0]-damage);             
            attacked.getInfo().setHitFlash(Sprites.HITFLASH_SP);
        }
    }
}