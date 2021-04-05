import java.io.*;  
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MapReader{

    public static ArrayList<ArrayList<Tile>> readFile(String fileName, Room room, int Ox, int Oy){
    ArrayList<ArrayList<Tile>> m = new ArrayList<ArrayList<Tile>>();
    try {
      File mapFile = new File(fileName);
      Scanner reader = new Scanner(mapFile);
      int i = 0;
      while (reader.hasNextLine()) {
        String data = reader.nextLine();
        if(data.equals("[MOBS]")){
            while (reader.hasNextLine()) {
                data = reader.nextLine();
                String[] form = data.split(" ");//format
                int[] result = new int[form.length];
                for(int x = 0; x < form.length; x++){
                    result[x] = Integer.parseInt(form[x]);
                }
                //System.out.println("___________");
                for(int x = 0; x<room.getRotate(); x++){ 
                    //System.out.println(result[1]+" "+result[2]);
                    int p1 = result[1]-5;
                    int p2 = result[2]-5;
                    result[1] = -p2+5;
                    result[2] = p1+5;
                }
                MobProperty po =  new MobProperty(MobList.ALLMOBS[result[0]]);
                Mob e = new Mob(result[1]+Ox*11, result[2]+Oy*11, room.getMap(),po);
                //System.out.println("read: "+e.getInfo());
            }
            break;
        }
        if(data.equals("[SYMBOL]")){
            room.setSymbol(reader.nextLine().charAt(room.getRotate()));
            continue;
        }
        if(data.equals("[TILES]")){
            continue;
        }
        //System.out.println(data);
        for(int j = 0; j<data.length();j++){
            TileProperty p = new TileProperty(TileList.ALLTILES[Character.getNumericValue(data.charAt(j))]);
            Tile t=new Tile(j,i,p);
            //System.out.println(t);
            if(t==null){
                //System.out.println(i+" "+j);
            }
            try{
                m.get(j).add(t);
            }
            catch(Exception e){
                m.add(new ArrayList<Tile>());
                m.get(j).add(t);
            }
        }
        i++;
      }
      reader.close();
    }
      catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    /*for(int i = 0; i<m.tileMap.size();i++){
        for(Tile t : m.tileMap.get(i)){
            System.out.println(t);
        }
    }*/
    
    return m;
  }
}
