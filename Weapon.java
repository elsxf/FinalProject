public class Weapon{
    private double speedMod;//multiplied by mob speed to get turns needed for attack
    private int numDice;// the 2 in 2d8+3
    private int dice;//the 8 in 2d8+3
    private int damMod;//the 3 in 2d8+3
    private int hitMod;//to hit bonus/peanalty
    private String[] flags;
    public Weapon(double speedMod, int numDice, int dice, int damMod, int hitMod,String[] flags){
        this.speedMod=speedMod;
        this.numDice = numDice;
        this.dice=dice;
        this.damMod=damMod;
        this.flags=flags;
        this.hitMod=hitMod;
    }
    public double getSpeedMod(){
        return(this.speedMod);
    }
    public int getNumDice(){
        return(this.numDice);
    }
    public int getDice(){
        return(this.dice);
    }
    public int getDamMod(){
        return(this.damMod);
    }
    public int gethitMod(){
        return(this.hitMod);
    }
    public boolean testFlag(String test){//returns true if flag is not present
        return(java.util.Arrays.asList(this.flags).indexOf(test)==-1);
    }

}