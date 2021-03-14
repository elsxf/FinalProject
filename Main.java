import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(1510,910);
        GamePanel panel = new GamePanel();
        
        frame.add(panel);
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        frame.setVisible(true);
    }
}
