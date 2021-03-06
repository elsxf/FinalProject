public class Attack_A extends Action_A{
    public Attack_A(Mob m, int relx, int rely){
        super.relX=relx;
        super.relY=rely;
        super.mob=m;
    }
    public void doo(){//do is a restriceted word
        Weapon using = mob.getWeapon();
        int damage = Sight.dice(using.getNumDice(),using.getDice(),using.getDamMod());
        Tile attacked = mob.getMap().tileMap.get(this.relX+mob.getX()).get(this.relY+mob.getY());
        if(attacked.getMob()==null){
            System.out.println(mob+ " attacks but hits only air");
            UI.log(mob+ " attacks but hits only air");
            attacked.setHitFlash(Sprites.MISSFLASH_SP);
        }
        else{
            System.out.println(attacked.getMob());
            UI.log(mob+" attacks "+attacked.getMob()+" for "+damage+" damage");
            attacked.getMob().setHealth(attacked.getMob().getHealth()[0]-damage);             
            attacked.setHitFlash(Sprites.HITFLASH_SP);
        }
    }
}