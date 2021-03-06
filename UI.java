import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class UI{
    private static ArrayList<String> log = new ArrayList<String>();
    public static String gameState = "view";//what decides whihc panel is drawn
    
    private static Player p;
    private static Map m;
    //layer for tiles
    private static int tilelayer=5;
    //layer for fields(acid, fire)
    private static int fieldlayer=4;
    //layer for furniture
    private static int furniturelayer=3;
    //layer for items
    private static int itemlayer=2;
    //layer for mobs(player, enemies)
    private static int moblayer=1;
    //layer for effects(hitflash, icons)
    private static int effectlayer = 0;
    
    //<viewpanel>
    private static JLabel[][] viewTiles = new JLabel[9][];//array of jlabels to store tile images
    private static JLabel[][] viewMobs = new JLabel[9][];//array of jlabels to store tile mobs
    private static JLabel[][] viewEffects = new JLabel[9][];
    private static JLabel[] textArray = new JLabel[5];
    private static JLabel[] barsArray = new JLabel[5];
    private static JLabel[] otherArray = new JLabel[4];
    private static JPanel statusText = new JPanel();
    private static JPanel statusBars = new JPanel();
    private static JPanel statusOther = new JPanel();
    private static JTextArea console = new JTextArea("console");
    private static GridBagConstraints c;
    public static JLayeredPane viewPanel = new JLayeredPane();
    //</viewpanel>
    //gameoverPanel>
    public static JPanel gameoverPanel = new JPanel();
    private static JTextArea gameOverText = new JTextArea("GAME OVER\n[Q]uit\t[R]ESTART");

    public static void status(int hp, int hpMax, Weapon weapon){
        barsArray[0].setText(Sight.toBars(hp,hpMax,5));
        if((double)(hp)/hpMax>=.75){
            barsArray[0].setForeground(Color.green);
        }
        else if((double)(hp)/hpMax<=.25){
            barsArray[0].setForeground(Color.red);
        }
        else{
            barsArray[0].setForeground(Color.yellow);
        }
    }
    
    public static String console(){
        String result = "";
        for(int i = Math.max(log.size()-20,0);i<log.size();i++){
            result+=log.get(i)+"\n";
        }
        return(result);
    }
     public static void log(String message){
         if(gameState!="gameOver"){
             log.add(message);
            }
        }
    
    public static void setMap(Map map){
        m=map;
    }
    public static void setPlayer(Player player){
        p=player;
    }
    
    public static void makeViewPanel(){
        viewPanel.setLayout(new GridBagLayout());//each screen should be 9x9 tiles with player in center
        
        statusText.setBackground(Color.black);
        statusText.setLayout(new GridBagLayout());
        statusBars.setBackground(Color.black);
        statusBars.setLayout(new GridBagLayout());
        statusOther.setBackground(Color.black);
        statusOther.setLayout(new GridBagLayout());
        
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
                viewTiles[i][j]=new JLabel();
                viewMobs[i][j]=new JLabel();
                viewEffects[i][j]=new JLabel();
                c.gridy=j;
                viewPanel.add(viewTiles[i][j],c,tilelayer);
                viewPanel.add(viewMobs[i][j],c,moblayer);
                viewPanel.add(viewEffects[i][j],c,effectlayer);
            }
        }
        String[] text = new String[]{"health","organs"," blood","hunger","thirst"};
        for(int i = 0;i<text.length;i++){
            c.gridy=i;
            JLabel j = new JLabel(text[i]+":");
            j.setForeground(Color.white);
            j.setFont(new Font("Monospaced",0,15));
            statusText.add(j,c);
            textArray[i]=j;
            //bars jlabels
            JLabel j2 = new JLabel("=====");
            j2.setForeground(Color.green);
            j2.setFont(new Font("Monospaced",0,15));
            statusBars.add(j2, c);
            barsArray[i]=j2;
            
        }
        
        
        c.gridx=9;
        c.gridy=0;
        c.gridwidth=1;
        c.gridheight=3;
        viewPanel.add(statusText,c);
        c.gridx=10;
        viewPanel.add(statusBars,c);
        c.gridx=11;
        viewPanel.add(statusOther,c);
        c.gridwidth=3;
        c.gridy=3;
        c.gridheight=6;
        viewPanel.add(console,c);
        c.gridy=8;
        c.gridheight=1;
        //viewPanel.add(newLine,c);
        c.gridwidth=1;
        c.gridheight=1;
    }
    public static void drawViewPanel(){
        ArrayList<int[]> points = new ArrayList<int[]>();
        Tile theTile;
        int radius = 4;
        points.add(new int[]{radius,0});
        points.add(new int[]{-radius,0});
        points.add(new int[]{0,radius});
        points.add(new int[]{0,-radius});
        for(int i = 1;i<=radius;i++){
              points.add(new int[]{radius,i});
              points.add(new int[]{-radius,i});
              points.add(new int[]{radius,-i});
              points.add(new int[]{-radius,-i});
              points.add(new int[]{i,radius});
              points.add(new int[]{i,-radius});
              points.add(new int[]{-i,radius});
              points.add(new int[]{-i,-radius});
                }
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                viewTiles[i][j].setIcon(null);
                viewMobs[i][j].setIcon(null);
                viewEffects[i][j].setIcon(null);
            }
        }
        //points now contains every point on outer ring of viewtiles
        for(int[] i : points){
            int[][] testPoints=Sight.lineTest(i[0],i[1]);//every point along line to outer point
            boolean drawBlank = false;//when true, j loop will draw blank tiles instead of actual tiles
            for(int[] j : testPoints){
                
                try{
                    theTile = m.tileMap.get(p.getX()+j[0]).get(p.getY()+j[1]);
                }
                catch(Exception e){
                    //continue;
                    theTile=null;
                    drawBlank=true;
                }
                if(drawBlank){//draws blank, nothing else drawn, dont draw blank if another line has drawn the tile
                    if(viewTiles[4+j[0]][4+j[1]].getIcon()==null){
                        viewTiles[4+j[0]][4+j[1]].setIcon(Sprites.BLANK_SP);
                        viewMobs[4+j[0]][4+j[1]].setIcon(null);
                        viewEffects[4+j[0]][4+j[1]].setIcon(null);
                    }
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
                if(theTile.getHitFlash()!=null){//check if tile has a hitflash
                     viewEffects[4+j[0]][4+j[1]].setIcon(theTile.getHitFlash());
                }
                else{
                    viewEffects[4+j[0]][4+j[1]].setIcon(null);
                }
            }
        }
        viewTiles[4][4].setIcon(m.tileMap.get(p.getX()).get(p.getY()).getImage());
        status(p.getHealth()[0],p.getMaxHealth(),p.getWeapon());
        console.setText(console());
        for(ArrayList<Tile> l: m.tileMap){
                for(Tile t:l){
                    t.setHitFlashTimer(t.getHitFlashTimer()-1);
                    if(t.getHitFlashTimer()<=0){
                        t.setHitFlash(null);
                    }
                }
            }
    }
    public static JLayeredPane getViewPanel(){
        return(viewPanel);       
    }
    public static void hitVoid(){
        for(ArrayList<Tile> l: m.tileMap){
                for(Tile t:l){
                    t.setHitFlash(null);
                }
            }
    }
    public static void makeGameOverPanel(){
        gameoverPanel.setLayout(new BorderLayout());
        gameoverPanel.setBackground(Color.black);
        gameoverPanel.add(gameOverText, BorderLayout.CENTER);
        gameOverText.setForeground(Color.white);
        gameOverText.setEditable(false);
        gameOverText.setBackground(Color.black);
        gameOverText.setFont(new Font("Monospaced",0,25));        
    }
    public static JPanel getGameOverPanel(){
        return(gameoverPanel);
    }
    
}