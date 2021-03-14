import java.util.*;

public class Brain
{
    private int steps;
    private Mob mob;
    public Brain(int steps, Mob mob){
        this.steps=1;
        this.mob=mob;
    }
    
    public int[] getMove(int[][] targets){//relative coords
        double minDist=Double.MAX_VALUE;
        int[] minPoint = new int[]{0,0};//closest target point
        for(int[] point : targets){//computes closest target square
            double dist = Math.pow(Math.pow(point[0],2)+Math.pow(point[1], 2), .5);
            if(dist<minDist){
                minPoint=point;
                minDist=dist;
                
            }
        }
        int[] target = minPoint; 
        //System.out.println(minPoint[0]+" "+minPoint[1]);
        
        //begin ai, minpoint is target square
        if(minPoint[0]==0&&minPoint[1]==0){//if on tile, move randomly(wander)
            //System.out.println("wander");
            mob.setTarget(mob.getX()+Sight.randint(-2,1),mob.getY()+Sight.randint(-2,1));
            return(new int[]{Sight.randint(-2,1),Sight.randint(-2,1)}); 
        }
        ArrayList<int[]> testPoints = new ArrayList<int[]>();
        for(int i = -1;i<=1;i++){
            for(int j = -1;j<=1;j++){
                Tile tile = null;
                try{
                    tile = this.mob.getMap().tileMap.get(mob.getX()+i).get(mob.getY()+j);
                }
                catch(Exception e){
                    
                    continue;
                }
                //System.out.println("tile x:"+tile.getX()+",tile y:"+tile.getY()+" "+i+" "+j+tile.testFlag("[IMPASSABLE]"));
                if(tile.testFlag("[IMPASSABLE]")){
                    testPoints.add(new int[]{i,j});
                }
            }
        }
        //System.out.println("mob coord "+mob.getX()+" "+mob.getY());
        /*System.out.print("[");
        for(int[] i: testPoints){
            System.out.print("[");
            for(int j:i){
                System.out.print(j+", ");
            }
            System.out.print("]");
        }
        System.out.println("]");*/
        //todo: add addiitional steps code
        
        
        minDist=Double.MAX_VALUE;
        minPoint = new int[]{0,0};
        for(int[] point: testPoints){
            double dist=Math.pow(Math.pow(point[0]-target[0],2)+Math.pow(point[1]-target[1], 2), .5);//distance between point being tested and target point
            if(dist<minDist){
                minDist = dist;
                minPoint = point;
                //System.out.println(minPoint[0]+", "+minPoint[1]);
            }
        }
        
        return(new int[]{minPoint[0], minPoint[1]});
        
        
    }
}
