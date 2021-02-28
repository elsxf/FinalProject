    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import java.util.*;
    
    public class GamePanel extends JPanel{
        //layer for tiles
        private int tilelayer=5;
        //layer for fields(acid, fire)
        private int fieldlayer=4;
        //layer for furniture
        private int furniturelayer=3;
        //layer for items
        private int itemlayer=2;
        //layer for mobs(player, enemies)
        private int moblayer=1;
        //layer for effects(hitflash, icons)
        private int effectlayer = 0;
        
        JLabel[][] viewTiles = new JLabel[9][];//array of jlabels to store tile images
        JLabel[][] viewMobs = new JLabel[9][];//array of jlabels to store tile mobs
        JLabel[][] viewEffects = new JLabel[9][];
        Map map1 = new Map();
        Player p = new Player(4,4,map1);
        Enemy e = new Enemy(12,12,map1);
        JTextArea status = new JTextArea("status");
        JTextArea console = new JTextArea("console");
        //JLabel newLine = new JLabel("newLIne");
        GridBagConstraints c;//define c here so draw function can use it
        public GamePanel(){
            map1.mobList.add(e);
            setBackground(Color.BLACK);
            addKeyListener(new KeyCheck());
            setLayout(new BorderLayout());
            JLayeredPane viewPanel = new JLayeredPane();
            viewPanel.setLayout(new GridBagLayout());//each screen should be 9x9 tiles with player in center
            add(viewPanel, BorderLayout.CENTER);
            
            status.setForeground(Color.white);
            status.setEditable(false);
            status.setBackground(Color.black);
            status.setMargin(new Insets(1,1,1,1));
            status.setFont(new Font("Monospaced",0,20));
            
            console.setForeground(Color.white);
            console.setEditable(false);
            console.setBackground(Color.black);
            console.setMargin(new Insets(1,1,1,1));
            console.setFont(new Font("Monospaced",0,18));
            
            c = new GridBagConstraints();
                        
            for(int i = 0;i<9;i++){
                viewTiles[i]=(new JLabel[9]);
                viewMobs[i]=(new JLabel[9]);
                viewEffects[i]=(new JLabel[9]);
                c.gridx=i;
                for(int j = 0;j<9;j++){
                    //System.out.println(p.getX()-4+i+" "+(p.getY()-4+j));
                    viewTiles[i][j]=new JLabel(map1.tileMap.get(p.getX()-4+i).get(p.getY()-4+j).getImage());//get tile as x,y's id, uses that to find correct sprite, puts sprite in jlabel
                    viewMobs[i][j]=new JLabel();
                    viewEffects[i][j]=new JLabel();
                    c.gridy=j;
                    viewPanel.add(viewTiles[i][j],c,this.tilelayer);
                    viewPanel.add(viewMobs[i][j],c,this.moblayer);
                    viewPanel.add(viewEffects[i][j],c,this.effectlayer);
                }
            }
            c.gridx=9;
            c.gridy=0;
            c.gridwidth=3;
            c.gridheight=3;
            viewPanel.add(status,c);
            c.gridy=3;
            c.gridheight=6;
            viewPanel.add(console,c);
            c.gridy=8;
            c.gridheight=1;
            //viewPanel.add(newLine,c);
            c.gridwidth=1;
            c.gridheight=1;
       viewMobs[4][4].setIcon(p.getImage());
        }
        
        
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            boolean redraw = false;
  
            
            
            
            ArrayList<int[]> points = new ArrayList<int[]>();
            Tile theTile;
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
                    
                    try{
                        theTile = map1.tileMap.get(p.getX()+j[0]).get(p.getY()+j[1]);
                    }
                    catch(Exception e){
                        //continue;
                        theTile=null;
                    }
                    if(drawBlank){//draws blank, nothing else drawn
                        viewTiles[4+j[0]][4+j[1]].setIcon(Sprites.BLANK_SP);
                        viewMobs[4+j[0]][4+j[1]].setIcon(null);
                        viewEffects[4+j[0]][4+j[1]].setIcon(null);
                        continue;
                    }
                    if(!theTile.testFlag("[SIGHT_BLOCKER]")){//if tile along line is sight blocker, draw that tile, but dont draw the rest
                        drawBlank=true;
                        //System.out.println("sight blocker detected");
                    }
                    viewTiles[4+j[0]][4+j[1]].setIcon(theTile.getImage());
                    if(theTile.getMob()!=null){//check if there is mob on tile being drawn                   
                        viewMobs[4+j[0]][4+j[1]].setIcon(theTile.getMob().getImage());
                        theTile.getMob().setTarget(p.getX(),p.getY());
                    }
                    else{
                        viewMobs[4+j[0]][4+j[1]].setIcon(null);
                    }
                    /*if(theTile.getHitFlash()!=null){//check if tile has a hitflash
                         viewEffects[4+j[0]][4+j[1]].setIcon(theTile.getHitFlash());
                         redraw=true;
                    }
                    else{
                        viewEffects[4+j[0]][4+j[1]].setIcon(null);
                    }*/
                }
            }
            viewTiles[4][4].setIcon(map1.tileMap.get(p.getX()).get(p.getY()).getImage());
            
            
            status.setText(UI.status(p.getHealth(),p.getMaxHealth(),p.getWeapon()));
            console.setText(UI.console());
            
            
            Toolkit.getDefaultToolkit().sync();//magic command makes animation smooth is called after drawing is done
            /*try{
                Thread.sleep(30);//sets minimum time between redraws
            }
            catch(Exception e){
                System.out.println(e);
            }*/
            updateUI();//update sprite changes
            
    }
        public class KeyCheck extends KeyAdapter {
            public void keyPressed(KeyEvent e){
                int key = e.getKeyCode();
                if(key<=KeyEvent.VK_NUMPAD9&&key>=KeyEvent.VK_NUMPAD1){
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
                p.move(xm,ym);
                if(p.getWakeupTurn()==Turn.g_turn){
                    repaint();
                    return;
                }
                while(Turn.g_turn<=p.getWakeupTurn()){
                    for(Mob m: map1.mobList){
                        if(m.getWakeupTurn()<=Turn.g_turn){
                            m.doNextAction();
                            /*xm=0;
                            ym=0;
                            if(m.getX()>m.getTargetX()){
                                xm=-1;
                            }
                            if(m.getX()<m.getTargetX()){
                                xm=1;
                            }
                            if(m.getY()>m.getTargetY()){
                                ym=-1;
                            }
                            if(m.getY()<m.getTargetY()){
                                ym=1;
                            }*/
                            m.think();
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

