
public class Matrix extends Thread {

    public static int[][] a;
    public static int[][] b;
    public static int[][] c;
    private static int Id, start;
    public static int partitionSize;
    public static long total_time = 0;
    private static int x, y, z1, z2;

    public Matrix(int id) {
        this.Id = id;  //get the thread id
    }

 
    //check whether multiplication can be done or not
    public static int validation() {
        x = a.length;
        y = b[0].length;
        z1 = a[0].length;
        z2 = b.length;

        if (z1 != z2) {
            System.out.println("Cannot multiply");
            return 0;
        }
        return 1;
    }

    @Override
    public void run() {
        long t1 = System.nanoTime();//initital time
        c = new int[x][y];
        int i, j, k, s;

        for (i = start; i < (Id + 1) * partitionSize; i++) {
            for (j = 0; j < y; j++) {
                for (s = 0, k = 0; k < z1; k++) {
                    s += a[i][k] * b[k][j];
                }
                c[i][j] = s;
            }
        }

        long t2 = System.nanoTime(); //final time
        calculateTime(t2 - t1); //caculate total time
    }

   

    //serial running method
    public static int[][] multiply() {

        long t1 = System.nanoTime();//initial time

        c = new int[x][y];
        int i, j, k, s;

        start = Id * partitionSize;
        for (i = 0; i < x; i++) {
            for (j = 0; j < y; j++) {
                for (s = 0, k = 0; k < z1; k++) {
                    s += a[i][k] * b[k][j];
                }
                c[i][j] = s;
            }
        }
        long t2 = System.nanoTime();//final time
        System.out.println("Serial Time " + (t2 - t1)); //printing the total time taken
        return c;
    }
    
    //synchronized method to calculte total time
    public synchronized void calculateTime(long time) {
        total_time += time;
    }
    
     //return the result matrix
    public static int[][] getResult() {
        return c;
    }

    //return the time
    public static long getTime() {
        return total_time;
    }

}
