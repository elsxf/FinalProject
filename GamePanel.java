    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import java.util.*;
    
    public class GamePanel extends JPanel{
        
        Map map1 = new Map();//MapReader.makeMap("./raw/Test_M.txt");
        Player p = new Player(4,4,map1);
        Enemy e = new Enemy(6,6,map1);
        public GamePanel(){
            UI.makeViewPanel();
            UI.makeGameOverPanel();
            UI.setMap(map1);
            UI.setPlayer(p);
            //map1.mobList.add(e);
            setBackground(Color.BLACK);
            addKeyListener(new KeyCheck());
            setLayout(new BorderLayout());
           
        }
        
        
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            this.removeAll();
            /*if(UI.gameState=="gameOver"){
                this.add(UI.getGameOverPanel(), BorderLayout.CENTER);
            }*/
            if(UI.gameState=="view"||UI.gameState=="gameOver"||UI.gameState=="waitForDir"){
                UI.drawViewPanel();
                this.add(UI.getViewPanel(),BorderLayout.CENTER);
            }
                     
            Toolkit.getDefaultToolkit().sync();//magic command makes animation smooth is called after drawing is done
            
            updateUI();//update sprite changes
            
            try{
                Thread.sleep(30);//sets minimum time between redraws
            }
            catch(Exception e){
                System.out.println(e);
            }
            repaint();
            //UI.hitVoid();
    }
        public class KeyCheck extends KeyAdapter {
            public void keyPressed(KeyEvent e){
                int key = e.getKeyCode();
                if(UI.gameState.equals("view")){ 
                    if(key<=KeyEvent.VK_NUMPAD9&&key>=KeyEvent.VK_NUMPAD1){
                        int[] m = Sight.toDirection(key);
                        p.move(m[0],m[1]);
                    }
                    if(key==KeyEvent.VK_C){
                        UI.gameState="waitForDir";
                        UI.log("close where?");
                        p.setNextAction(new Close_A(p,0,0));
                        return;
                    }
                }
                if(UI.gameState.equals("gameOver")){
                    if(key==KeyEvent.VK_Q){
                        System.exit(0);
                    }
                }
                if(UI.gameState.equals("waitForDir")){
                    if(key<=KeyEvent.VK_NUMPAD9&&key>=KeyEvent.VK_NUMPAD1){
                        int[] m = Sight.toDirection(key);
                        p.getNextAction().relX=m[0];
                        p.getNextAction().relY=m[1];
                        p.setWakeupTurn(p.getSpeed()/2+Turn.g_turn);
                    }
                    else{
                        UI.log("can't do that now");
                    }
                    UI.gameState="view";
                }
                if(p.getWakeupTurn()<=Turn.g_turn){
                    repaint();
                    return;
                }
                while(Turn.g_turn<=p.getWakeupTurn()){
                    for(Mob m: map1.mobList){
                        if(m.getWakeupTurn()<=Turn.g_turn){
                            m.doNextAction();
                            m.think();
                        }
                    }
                    Turn.nextTurn();
                }
                //System.out.println(Turn.g_turn);
                p.doNextAction();
            }
        }
           

}

