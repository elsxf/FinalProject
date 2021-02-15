

public class Sight
{
    public Sight(){
        int[][] bob = this.lineTest(4,0);
        for(int[] i:bob){
                System.out.print(i[0]+","+i[1]);
            System.out.println(" ");
        }
        int[] joe = new int[]{3,4};
        joe=rot90(joe,1);
        for(int i:joe){
            System.out.println(i);
        }
    }
    public static int[][] lineTest(int wr, int hr){//one of these numbers must be 4 or -4
        boolean wIs4=true;
        int[][] results = new int[4][];
        int w = Math.abs(wr);
        int h = Math.abs(hr);
        if(h==4&&w!=4){
            wIs4=false;//also false if both are 4, this should be fine
        }
        if(wIs4){//width is 4, iterate aonlg x axis, calculate height
            for(int i = 1;i<=4;i++){
                results[i-1]=new int[]{i,(int)((double)(h)/(double)(w)*(double)(i)+.3)};//height at distance rounded to nearest whole number
            }
        }
        if(!wIs4){
            for(int i = 1;i<=4;i++){//height is 4, iterate along y axis, calculate width
                results[i-1]=new int[]{(int)((double)(w)/(double)(h)*(double)(i)+.3),i};//height at distance rounded to nearest whole number
            }    
        }
        if(wr<0){
            for(int[] i:results){
                i[0]=-i[0];
            }
        }
        if(hr<0){
            for(int[] i:results){
                i[1]=-i[1];
            }
        }
        return(results);
    }

    public static int[] rot90(int[] toRotate, int num){//rotate point toRotate num times ccwise90
        for(int i = 0;i<num;i++){
            toRotate=new int[]{toRotate[1],-toRotate[0]};
        }
        return(toRotate);
    }
    public static int randint(int a, int b){
        return((int)(Math.random()*(b-a+1)+a));
    }
    public static int dice(int numDice, int dice, int mod){
        int result = 0;
        for(int i = 0;i<numDice;i++){
            result+=randint(0,dice);
        }
        return(result+mod);
    }
}
