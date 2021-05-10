import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
    
public class Sight
{
    public Sight(){
        int[][] bob = this.lineTest(4,0);
        for(int[] i:bob){
                System.out.print(i[0]+","+i[1]);
            System.out.println(" ");
        }
        int[] joe = new int[]{3,4};
        joe=rot90(joe,1);
        for(int i:joe){
            System.out.println(i);
        }
    }
    public static int[][] lineTest(int wr, int hr){//one of these numbers must be 4 or -4
        boolean wIs4=true;
        int[][] results = new int[5][];
        results[0]=new int[]{0,0};
        int w = Math.abs(wr);
        int h = Math.abs(hr);
        if(h==4&&w!=4){
            wIs4=false;//also false if both are 4, this should be fine
        }
        if(wIs4){//width is 4, iterate aonlg x axis, calculate height
            for(int i = 1;i<=4;i++){
                results[i]=new int[]{i,(int)((double)(h)/(double)(w)*(double)(i)+.3)};//height at distance rounded to nearest whole number
            }
        }
        if(!wIs4){
            for(int i = 1;i<=4;i++){//height is 4, iterate along y axis, calculate width
                results[i]=new int[]{(int)((double)(w)/(double)(h)*(double)(i)+.3),i};//height at distance rounded to nearest whole number
            }    
        }
        if(wr<0){
            for(int[] i:results){
                i[0]=-i[0];
            }
        }
        if(hr<0){
            for(int[] i:results){
                i[1]=-i[1];
            }
        }
        return(results);
    }

    public static int[] rot90(int[] toRotate, int num){//rotate point toRotate num times ccwise90
        for(int i = 0;i<num;i++){
            toRotate=new int[]{toRotate[1],-toRotate[0]};
        }
        return(toRotate);
    }
    public static int randint(int a, int b){
        return((int)(Math.random()*(b-a+1)+a));
    }
    public static int minMax(int min, int max, int num){
        return(Math.max(min, Math.min(max, num)));
    }
    public static int dice(int numDice, int dice, int mod){
        int result = 0;
        for(int i = 0;i<numDice;i++){
            result+=randint(1,dice);
        }
        return(result+mod);
    }
    public static String toBars(int num, int max,int total){//expresses num/max as [total] bars
        String result = "";
        double wMax = ((double)max/(total*2));
        for(int i = 0;i<total*2;i+=2){
            if(num>=wMax*(i+1)){
                result+="|";
                continue;
            }
            if(num>wMax*i){
                result+="\\";
                continue;
            }
            result+=".";
        }
        return(result);
    }
    public static boolean isDirection(int key){
        if(key==KeyBindings.k_nw||
            key==KeyBindings.k_n||
            key==KeyBindings.k_ne||
            key==KeyBindings.k_w||
            key==KeyBindings.k_W||
            key==KeyBindings.k_e||
            key==KeyBindings.k_sw||
            key==KeyBindings.k_s||
            key==KeyBindings.k_se){
            return true;}
        return false;
    }
    public static int[] toDirection(int key){//converts numpad to coords ex: 9 to +1,-1
        int xm = 0;
        int ym = 0;
        switch(key){
            case(KeyBindings.k_nw):
            case(KeyBindings.k_n):
            case(KeyBindings.k_ne):
                ym=-1;
                break;
            case(KeyBindings.k_sw):
            case(KeyBindings.k_s):
            case(KeyBindings.k_se):
                ym=1;
                break;

        }
        switch(key){
            case(KeyBindings.k_nw):
            case(KeyBindings.k_w):
            case(KeyBindings.k_sw):
                xm=-1;
                break;
            case(KeyBindings.k_ne):
            case(KeyBindings.k_e):
            case(KeyBindings.k_se):
                xm=1;
                break;

        }
        return(new int[]{xm, ym});
    }
    public static double skillCalc(int a){
        return( Math.max(1,a));
    }
    public static String minCharE(String base, int num){
        for(int i = 0; base.length()<num; base+=" "){}
        return base;
    }
    public static String minCharB(String base, int num){
        for(int i = 0; base.length()<num; base=" "+base){}
        return base;
    }
    public static HashMap<String, int[]> copySkills(HashMap<String, int[]> original){
        HashMap<String, int[]> copy = new HashMap<String, int[]>();
        for (HashMap.Entry<String, int[]> entry : original.entrySet()){
            copy.put(entry.getKey(), new int[]{entry.getValue()[0], entry.getValue()[1]});
        }
        return copy;
}
}
