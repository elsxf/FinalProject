    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import java.util.*;
    
    public class GamePanel extends JPanel{
        
        Map map1;// = new Map();//MapReader.makeMap("./raw/Test_M.txt");
        Mob p;// = new Mob(5+map1.getStart()[0]*11,5+map1.getStart()[1]*11,map1, new MobProperty(MobList.ALLMOBS[0]));
        int mapIndex;
        Map[] bigMap;
        public GamePanel(){
            UI.makeViewPanel();
            UI.makeCharSheetPanel();
            UI.makeMapPanel();
            UI.makeWinPanel();
            //map1.mobList.add(e);
            setBackground(Color.BLACK);
            addKeyListener(new KeyCheck());
            setLayout(new BorderLayout());
            this.gameStart();
        }
        
        public void gameStart(){
            bigMap = new Map[]{new Map(), new Map()};
            map1 = bigMap[bigMap.length-1];
            mapIndex=bigMap.length-1;
            p = new Mob(5+map1.getStart()[0]*11,5+map1.getStart()[1]*11,map1, new MobProperty(MobList.ALLMOBS[0]));
            UI.setMap(map1);
            UI.setPlayer(p);
            p.giveMap();
            UI.gameState = "view";
            UI.clearLog();
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
            if(UI.gameState.equals("charSheet")){
                UI.drawCharSheet();
                this.add(UI.getCharSheetPanel(), BorderLayout.PAGE_START);//dont know why it works dont know how it works, but game breaks when using borderlayout.center
            }
            if(UI.gameState.equals("map")){
                UI.drawMapPanel();
                this.add(UI.getMapPanel(), BorderLayout.PAGE_START);//dont know why it works dont know how it works, but game breaks when using borderlayout.center
            }
            if(UI.gameState.equals("win")){
                this.add(UI.getWinPanel());
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
                    if(Sight.isDirection(key)){
                        int[] m = Sight.toDirection(key);
                        p.move(m[0],m[1]);
                    }
                    if(key==KeyBindings.k_close){//close command
                        ArrayList<ArrayList<Integer>> poss = p.testTileFlag("[CLOSEABLE]");
                        if(poss.size()==0){//nothing to close
                            UI.log("nothing to close nearby");
                            return;
                        }
                        if(poss.size()>1){//more than one thing to close
                            UI.gameState="waitForDir";
                            UI.log("close where?");
                            p.getInfo().setNextAction(new Close_A(p,0,0));
                            return;
                        }
                        p.getInfo().setNextAction(new Close_A(p,poss.get(0).get(0),poss.get(0).get(1)));
       
                    }
                    if(key==KeyEvent.VK_I){
                        UI.gameState="charSheet";
                        return;
                    }
                    if(key==KeyBindings.k_map){
                        UI.gameState="map";
                        return;
                    }
                    if(key==KeyBindings.k_up){
                        if(!map1.tileMap.get(p.getX()).get(p.getY()).getInfo().testFlag("[STAIRUP]")){
                            if(mapIndex!=0){
                                mapIndex--;
                                map1=bigMap[mapIndex];
                                map1.makeRoom(p.getX()/11,p.getY()/11,"./raw/maps/Start_M.txt", 0);
                                UI.setMap(map1);
                                p.setMap(map1);
                                UI.setPlayer(p);
                                p.giveMap();
                            }
                            else{
                                UI.gameState="win";
                            }
                        }
                        return;
                    }
                }
                
                if(UI.gameState.equals("gameOver")){
                    if(key==KeyEvent.VK_Q){
                        System.exit(0);
                    }
                    if(key==KeyEvent.VK_R){
                        gameStart();
                    }
                }
                
                if(UI.gameState.equals("waitForDir")){
                    if(Sight.isDirection(key)){
                        int[] m = Sight.toDirection(key);
                        if(map1.tileMap.get(p.getX()+m[0]).get(p.getY()+m[1]).getInfo().testFlag("[CLOSEABLE]")){
                            UI.gameState="view";
                            return;
                        }
                        p.getInfo().getNextAction().relX=m[0];
                        p.getInfo().getNextAction().relY=m[1];
                    }
                    else{
                        UI.log("never mind");
                    }
                    UI.gameState="view";
                }
                
                if(UI.gameState=="charSheet"){
                    if(key==KeyBindings.k_info){
                        UI.gameState="view";
                    }
                }
                
                if(UI.gameState=="map"){
                    if(key==KeyBindings.k_map){
                        UI.gameState="view";
                    }
                }
                
                if(p.getInfo().getWakeupTurn()<=Turn.g_turn){//if no action/invalid action inputted
                    return;
                }
                //System.out.println("p "+ p.getX()+ " "+ p.getY());
                //System.out.println("pr "+ p.getX()/11+ " "+ p.getY()/11);
                //System.out.println(map1.getStart()[0]+" "+map1.getStart()[1]);
                while(Turn.g_turn<=p.getInfo().getWakeupTurn()){
                    try{
                        for(Mob m: map1.mobList){
                            if(m.getInfo().getWakeupTurn()<=Turn.g_turn){
                                //System.out.println("getinfo: "+m.getX()+" "+m.getY());
                                m.getInfo().doNextAction();
                                m.getInfo().think();
                                //System.out.println(m.getX()+" "+m.getY());
                            }
                        }
                        Turn.nextTurn();
                    }
                    catch(java.util.ConcurrentModificationException E){
                        continue;//turn not passed, so this sould be fine
                    }
                }
                //System.out.println(Turn.g_turn);
                p.getInfo().doNextAction();
            }
        }
           

}

