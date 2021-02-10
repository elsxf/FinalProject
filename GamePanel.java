    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import java.util.*;
    
    public class GamePanel extends JLayeredPane{
        //layer 4 for tiles
        //layer 3 for fields(acid, fire)
        //layer 2 for furniture
        //layer 1 for items
        //layer 0 for people(player, enemies)
        
        JLabel[][] viewTiles = new JLabel[9][];//array of jlabels to store tile images
        JLabel[][] viewMobs = new JLabel[9][];//array of jlabels to store tile mobs
        Map map1 = new Map();
        Player p = new Player(4,4,map1);
        Enemy e = new Enemy(12,12,map1);
        GridBagConstraints c;//define c here so draw function can use it
        public GamePanel(){
            map1.mobList.add(e);
            addKeyListener(new KeyCheck());
            setLayout(new GridBagLayout());//each screen should be 9x9 tiles with player in center
            c = new GridBagConstraints();
    
            for(int i = 0;i<9;i++){
                viewTiles[i]=(new JLabel[9]);
                viewMobs[i]=(new JLabel[9]);
                c.gridx=i;
                for(int j = 0;j<9;j++){
                    //System.out.println(p.getX()-4+i+" "+(p.getY()-4+j));
                    viewTiles[i][j]=new JLabel(map1.tileMap.get(p.getX()-4+i).get(p.getY()-4+j).getImage());//get tile as x,y's id, uses that to find correct sprite, puts sprite in jlabel
                    viewMobs[i][j]=new JLabel();
                    c.gridy=j;
                    add(viewTiles[i][j],c,4);
                    add(viewMobs[i][j],c,0);
                }
            }
       viewMobs[4][4].setIcon(p.getImage());
        }
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            setBackground(Color.BLACK);
            
            
            
            ArrayList<int[]> points = new ArrayList<int[]>();
            int radius = 4;
                for(int i = radius;i>0;i--){
                      points.add(new int[]{radius,i});
                      points.add(new int[]{-radius,i});
                      points.add(new int[]{radius,-i});
                      points.add(new int[]{-radius,-i});
                      points.add(new int[]{i,radius});
                      points.add(new int[]{i,-radius});
                      points.add(new int[]{-i,radius});
                      points.add(new int[]{-i,-radius});
                        }
                points.add(new int[]{radius,0});
                points.add(new int[]{-radius,0});
                points.add(new int[]{0,radius});
                points.add(new int[]{0,-radius});
            //points now contains every point on outer ring of viewtiles
            for(int[] i : points){
                int[][] testPoints=Sight.lineTest(i[0],i[1]);//every point along line to outer point
                boolean drawBlank = false;//when true, j loop will draw blank tiles instead of actual tiles
                for(int[] j : testPoints){
                    if(drawBlank){
                        viewTiles[4+j[0]][4+j[1]].setIcon(Sprites.BLANK_SP);
                        viewMobs[4+j[0]][4+j[1]].setIcon(null);
                        continue;
                    }
                    if(!map1.tileMap.get(p.getX()+j[0]).get(p.getY()+j[1]).testFlag("[SIGHT_BLOCKER]")){//if tile along line is sight blocker, draw that tile, but dont draw the rest
                        drawBlank=true;
                        //System.out.println("sight blocker detected");
                    }
                    viewTiles[4+j[0]][4+j[1]].setIcon(map1.tileMap.get(p.getX()+j[0]).get(p.getY()+j[1]).getImage());
                    boolean drewMob=false;
                    for(Mob m : map1.mobList){//draw mobs
                        if(m.getX()==p.getX()+j[0]&&m.getY()==p.getY()+j[1]){
                            viewMobs[4+j[0]][4+j[1]].setIcon(m.getImage());
                            m.setTarget(p.getX(),p.getY());
                            drewMob=true;
                        }
                    }
                    if(!drewMob){
                        viewMobs[4+j[0]][4+j[1]].setIcon(null);
                    }
                }
            }
            viewTiles[4][4].setIcon(map1.tileMap.get(p.getX()).get(p.getY()).getImage());
            
            //draw blank panels over unseen tiles
            
            
            Toolkit.getDefaultToolkit().sync();//magic command makes animation smooth is called after drawing is done
            /*try{
                Thread.sleep(30);//sets minimum time between redraws
            }
            catch(Exception e){
                System.out.println(e);
            }*/
            updateUI();//update sprite changes
            //repaint();//loops
            
            
        }
        public class KeyCheck extends KeyAdapter {
            public void keyPressed(KeyEvent e){
                int key = e.getKeyCode();
                if(key<=KeyEvent.VK_NUMPAD9&&key>=KeyEvent.VK_NUMPAD1){
                    String xm = "0";
                    String ym = "0";
                    if(key>=KeyEvent.VK_NUMPAD7&&key<=KeyEvent.VK_NUMPAD9){
                        ym="-1";
                    }
                    if(key>=KeyEvent.VK_NUMPAD1&&key<=KeyEvent.VK_NUMPAD3){
                        ym="1";
                    }
                    switch(key){
                        case(KeyEvent.VK_NUMPAD7):
                        case(KeyEvent.VK_NUMPAD4):
                        case(KeyEvent.VK_NUMPAD1):
                            xm="-1";
                            break;
                        case(KeyEvent.VK_NUMPAD9):
                        case(KeyEvent.VK_NUMPAD6):
                        case(KeyEvent.VK_NUMPAD3):
                            xm="1";
                            break;
    
                    }
                if(!p.getMap().tileMap.get(p.getX()+Integer.parseInt(xm)).get(p.getY()+Integer.parseInt(ym)).testFlag("[IMPASSABLE]")){
                    //System.out.println("works");
                    return;//breaks out of function and incurs no move cost if player tries to move into impasssable space
                }
                p.setNextAction(new String[]{"move", xm, ym});
                p.setWakeupTurn(Turn.g_turn+p.getSpeed());
                while(Turn.g_turn<=p.getWakeupTurn()){
                    for(Mob m: map1.mobList){
                        if(m.getWakeupTurn()<=Turn.g_turn){
                            m.doNextAction();
                            m.setWakeupTurn(Turn.g_turn+m.getSpeed());
                            System.out.println("monster wakeup:"+m.getWakeupTurn());
                            System.out.println("turn:"+Turn.g_turn);
                            xm="0";
                            ym="0";
                            if(m.getX()>m.getTargetX()){
                                xm="-1";
                            }
                            if(m.getX()<m.getTargetX()){
                                xm="1";
                            }
                            if(m.getY()>m.getTargetY()){
                                ym="-1";
                            }
                            if(m.getY()<m.getTargetY()){
                                ym="1";
                            }
                            m.setNextAction(new String[]{"move", xm, ym});
                        }
                    }
                    Turn.nextTurn();
                }
                //System.out.println(Turn.g_turn);
                p.doNextAction();
                }
            repaint(); 
            }
           

        }
}

