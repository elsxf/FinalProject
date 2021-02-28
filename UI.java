import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class UI{
    public static ArrayList<String> log = new ArrayList<String>();
    
    public static String status(int hp, int hpMax, Weapon weapon){
        return(
        "HP: "+hp+"/"+hpMax+"\t"+"weapon:"+weapon
        );
    }
    
    public static String console(){
        String result = "";
        for(int i = Math.max(log.size()-20,0);i<log.size();i++){
            result+=log.get(i)+"\n";
        }
        return(result);
    }
}