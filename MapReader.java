import java.io.*;  
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MapReader{

    public static ArrayList<ArrayList<Tile>> readFile(String fileName, Map map, int Ox, int Oy){
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
                Enemy e= null;
                switch(form[0]){
                    case "1":
                        e = new Enemy(Integer.valueOf(form[1])+Ox*11, Integer.valueOf(form[2])+Oy*11, map);
                        //map.mobList.add(e);
                        //e.giveMap();
                        break;
                }
                
            }
            break;
        }
        if(data.equals("[TILES]")){
            continue;
        }
        //System.out.println(data);
        for(int j = 0; j<data.length();j++){
            Tile t=null;
            switch(data.charAt(j)){
                case '0':
                    t = new FloorTile(j,i);
                    break;
                case '1':
                    t = new WallTile(j,i);
                    break;
                case '2':
                    t = new DoorTile(j,i);
                    break;
            }
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
