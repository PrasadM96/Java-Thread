

/*
    In this case no of rows is 3. So to parallelize matrix mulltiplication we can use 3 threads each for row operation. 
	But when going to find multiplication of larger matrices creating thread for each row is not good. We can use 8 or 4 threads and divide the work load 
    among them. As a example,take a matrix multiplication with 2000 rows. We can use 8 threads 
    and each thread do 125 row operations. Only in this code we need to change is noOfThreads variable value.
	
	Synchronization
	When we start two or more threads within a program, there may be a situation when multiple threads try to access the same resource and 
	finally they can produce unforeseen result due to concurrency issues.
	So there is a need to synchronize the action of multiple threads and make sure that only one thread can access the resource at a given point in time.
	To execute synchronized method thread need to obtain lock on object and only one thread at a time can obtain lock on object.
	
	Cache memory
	This is not a vey cache friendly method. To make this more cache friendly we can use a array which store colmuns of the matrix.
	According to this example we can use arrays A={1,1,1,1,1,1,1,1,1} B={1,1,1}. 
	Each thread get 3 element set of A and do operation with B. 
	Accessing 1D array element is easier than accessing 2D array elements. So this idea will be a more cache firendly method.
	
     
*/

public class Main{ 
	
    public static int [][] a = {{1, 1, 1},
								{1, 1, 1},
								{1, 1, 1}};
    
    public static int [][] b = {{1 },
								{1 },
								{1 }};


    public static void print_matrix(int [][] a) { //printing matrix
	for(int i=0; i < a.length; i++) {
	    for(int j=0; j< a[i].length; j++) {
		System.out.print(a[i][j] + " "); 
            }
	    System.out.println();
	}
    }
    

    public static void main(String [] args) { 
		int noOfThreads=3; //how many threads
		Matrix.partitionSize=a.length/noOfThreads; //obtain partition size -one thread's work load
		Matrix.a=a;  //pass matrix a
		Matrix.b=b;  //pass matrix b
		
        int valid=Matrix.validation(); //check whether multify can be perform or not
        if(valid==1){
			Matrix m[]=new Matrix[noOfThreads]; //creating object array
			
			for(int i=0;i<noOfThreads;i++){
				m[i]=new Matrix(i);
				m[i].start();
			}
			
			try{
				for(int i=0;i<noOfThreads;i++){ //join threads
					 m[i].join();
				}
			}catch(InterruptedException e){
				System.out.println(e);
			}
					
			int [][]c=Matrix.getResult(); //get the result matrix
			long timetaken=Matrix.getTime();
			System.out.println("Time taken to perform the operation with threads "+timetaken+" Nanoseconds");
			print_matrix(c); //print the matrix

			int [][]x = Matrix.multiply(); //calling serial method
			//print_matrix(x); // see if the multipication is correct

     }
    }
                
}