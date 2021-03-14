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
    public static int dice(int numDice, int dice, int mod){
        int result = 0;
        for(int i = 0;i<numDice;i++){
            result+=randint(1,dice);
        }
        return(result+mod);
    }
    public static String toBars(int num, int max,int total){//expresses num/max as [total] bars
        String result = "";
        double wMax = (double)(max/(total*2));
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
    public static int[] toDirection(int key){//converts numpad to coords ex: 9 to +1,-1
        int xm = 0;
        int ym = 0;
        if(key>=KeyEvent.VK_NUMPAD7&&key<=KeyEvent.VK_NUMPAD9){
            ym=-1;
        }
        if(key>=KeyEvent.VK_NUMPAD1&&key<=KeyEvent.VK_NUMPAD3){
            ym=1;
        }
        switch(key){
            case(KeyEvent.VK_NUMPAD7):
            case(KeyEvent.VK_NUMPAD4):
            case(KeyEvent.VK_NUMPAD1):
                xm=-1;
                break;
            case(KeyEvent.VK_NUMPAD9):
            case(KeyEvent.VK_NUMPAD6):
            case(KeyEvent.VK_NUMPAD3):
                xm=1;
                break;

        }
        return(new int[]{xm, ym});
    }
    public static double skillCalc(int a, int b){
        return( Math.pow(2,a-b));
    }
    public static String minCharE(String base, int num){
        for(int i = 0; base.length()<num; base+=" "){}
        return base;
    }
    public static String minCharB(String base, int num){
        for(int i = 0; base.length()<num; base=" "+base){}
        return base;
    }
}
