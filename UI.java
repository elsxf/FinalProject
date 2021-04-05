import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class UI{
    private static ArrayList<String> log = new ArrayList<String>();
    public static String gameState = "view";//what decides whihc panel is drawn
    
    private static Mob p;
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
    private static JLabel[] otherArray = new JLabel[5];
    private static JPanel statusText = new JPanel();
    private static JPanel statusBars = new JPanel();
    private static JPanel statusOther = new JPanel();
    private static JTextArea console = new JTextArea("console");
    private static GridBagConstraints c;
    public static JLayeredPane viewPanel = new JLayeredPane();
    //</viewpanel>
    //<inventorypanel>
    public static JPanel charSheet = new JPanel();
    private static JTextArea charSheetSkills = new JTextArea("skills");
    //</inventorypanel>
    //<mapPanel>
    public static JPanel mapPanel = new JPanel();
    public static JTextArea mapText = new JTextArea("map");
    //</mapPanel>

    public static void status(int hp, int hpMax, Weapon weapon){
        barsArray[0].setText(Sight.toBars(hp,hpMax,5));
        if((double)(hp)/hpMax>=.95){
            barsArray[0].setForeground(Color.green);
        }
        else if((double)(hp)/hpMax<=.35){
            barsArray[0].setForeground(Color.red);
        }
        else{
            barsArray[0].setForeground(Color.yellow);
        }
        otherArray[0].setText(" Weapon: "+weapon);
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
    public static void setPlayer(Mob player){
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
            //others jlabels
            JLabel j3 = new JLabel();
            j3.setForeground(Color.white);
            j3.setFont(new Font("Monospaced",0,15));
            statusOther.add(j3,c);
            otherArray[i]=j3;
           
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
                if(drawBlank){//draws blank, nothing else drawn, dont draw blank if another line has drawn the tile
                    if(viewTiles[4+j[0]][4+j[1]].getIcon()==null){
                        viewTiles[4+j[0]][4+j[1]].setIcon(Sprites.BLANK_SP);
                        viewMobs[4+j[0]][4+j[1]].setIcon(null);
                        viewEffects[4+j[0]][4+j[1]].setIcon(null);
                    }
                    continue;
                }
                try{//catch outofbounds
                    theTile = m.tileMap.get(p.getX()+j[0]).get(p.getY()+j[1]);
                    if(theTile==null){//catch nulltile, put room code here
                        viewTiles[4+j[0]][4+j[1]].setIcon(Sprites.BLANK_SP);
                        int tX=p.getX()+j[0];
                        int tY=p.getY()+j[1];
                        int dir=0;
                        if(tX/11>p.getX()/11){
                            dir=1;
                        }
                        if(tX/11<p.getX()/11){
                            dir=3;
                        }
                        if(tY/11>p.getY()/11){
                            dir=2;
                        }
                        m.newRoom(p.getX()/11, p.getY()/11, dir);
                        continue;
                    }
                }
                catch(Exception e){
                    //continue;
                    theTile=null;
                    drawBlank=true;
                    viewTiles[4+j[0]][4+j[1]].setIcon(Sprites.WALLTILE1_SP);//draw walls onto outofbounds tiles to keep player form walking off
                    continue;
                }
                if(!theTile.getInfo().testFlag("[SIGHT_BLOCKER]")){//if tile along line is sight blocker, draw that tile, but dont draw the rest
                    drawBlank=true;
                    //System.out.println("sight blocker detected");
                }
                viewTiles[4+j[0]][4+j[1]].setIcon(theTile.getInfo().getImage());
                if(theTile.getInfo().getMob()!=null){//check if there is mob on tile being drawn                   
                    viewMobs[4+j[0]][4+j[1]].setIcon(theTile.getInfo().getMob().getInfo().getImage());
                    theTile.getInfo().getMob().getInfo().setTarget(p.getX(),p.getY());
                }
                else{
                    viewMobs[4+j[0]][4+j[1]].setIcon(null);
                }
                if(theTile.getInfo().getHitFlash()!=null){//check if tile has a hitflash
                     viewEffects[4+j[0]][4+j[1]].setIcon(theTile.getInfo().getHitFlash());
                }
                else{
                    viewEffects[4+j[0]][4+j[1]].setIcon(null);
                }
            }
        }
        viewTiles[4][4].setIcon(m.tileMap.get(p.getX()).get(p.getY()).getInfo().getImage());
        status(p.getInfo().getHealth()[0],p.getInfo().getMaxHealth(),p.getInfo().getWeapon());
        console.setText(console());
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                TileProperty t;
                try{
                    t = m.tileMap.get(p.getX()+(4-i)).get(p.getY()+(4-j)).getInfo();
                    if(t==null){
                        continue;
                    }
                }
                catch(Exception e){
                    continue;
                }
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
                    t.getInfo().setHitFlash(null);
                }
            }
    }
    public static void makeCharSheetPanel(){
        charSheet.setLayout(new BorderLayout());
        charSheet.setBackground(Color.black);
        charSheet.add(charSheetSkills, BorderLayout.PAGE_START);
        charSheetSkills.setForeground(Color.white);
        charSheetSkills.setEditable(false);
        charSheetSkills.setBackground(Color.black);
        charSheetSkills.setFont(new Font("Monospaced",0,25));        
    }
    public static JPanel getCharSheetPanel(){
        return(charSheet);
    }
    public static void drawCharSheet(){
        String text = "SKILLS:";
        for(HashMap.Entry<String, int[]> entry : p.getInfo().getSkills().entrySet()){
            text+="\n"+Sight.minCharE(entry.getKey(), 10)+entry.getValue()[0]+" ("+Sight.minCharB(String.valueOf(entry.getValue()[1]%100), 2)+"%)";
        }
        charSheetSkills.setText(text);
    }
    public static void makeMapPanel(){
        mapPanel.setBackground(Color.black);
        mapPanel.add(mapText);
        mapText.setForeground(Color.white);
        mapText.setEditable(false);
        mapText.setBackground(Color.black);
        mapText.setFont(new Font("Monospaced",0,25));  
        mapText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }
    public static JPanel getMapPanel(){
        return mapPanel;
    }
    public static void drawMapPanel(){
        String text = "MAP:";
        for(int i = 0; i<m.theRooms[0].length; i++){
            text+="\n";
            for(int j = 0; j<m.theRooms.length; j++){
                if(m.theRooms[j][i]==null){
                    text+=" ";
                    continue;
                }
                text+=m.theRooms[j][i].getSymbol();
            }
        }
        mapText.setText(text);
    }

    
}