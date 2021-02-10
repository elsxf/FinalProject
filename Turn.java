import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Turn{
    public static long g_turn=0;
     
    
    public static void nextTurn(){
        //wakeup and action handling code goes here.
        
        g_turn++;
    }
}