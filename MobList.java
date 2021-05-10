public class MobList{
    public static final MobProperty[] ALLMOBS = new MobProperty[]{
       new MobProperty(0,100,25,Sprites.PLAYER_SP,new String("player"),new String[]{"[PLAYER]"}, WeaponList.Fist_W, "you", new int[]{0,0,0}),
       new MobProperty(1,130,5,Sprites.ENEMY1_SP,new String("hostile"),new String[]{},WeaponList.Claws_W, "the mutant", new int[]{0,0,1}),
       new MobProperty(2,90,10,Sprites.ENEMY3_SP,new String("hostile"),new String[]{},WeaponList.Claws_W, "the boss", new int[]{2,2,2})
    };
}