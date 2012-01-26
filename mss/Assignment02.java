// Chris Campo
// COP 3503C - 0001
// HW 2 - Maximum Subsequence Sum
// 1/22/2012

import java.util.*;

public class Assignment02
{
    public static void main(String[] args)
    {
        // the sizes of the sequences
        int[] N = {
                   10, 
                   100, 
                   1000, 
                   10000, 
                   100000, 
                   1000000, 
                   10000000,
                   100000000,
                   };
        
        // arrays to store indices because Java is pass by value.
        int[] linij  = new int[2];
        int[] qdij   = new int[2];
        int[] cuij   = new int[2];
        
        System.out.println("Chris Campo (ch632561): COP 3503 - Assignment 2\n");
        
        // loop because we need to calculate for multiple val of N
        for(int i = 0; i < N.length; i++){
            int n      = N[i];
            System.out.printf ("N = %-10d\n", n);
            System.out.println("==============");
            
            int[] arr  = new int[n]; 
            populateArray(arr);
            
            // analysis for linear algorithm
            long tstart1 = System.nanoTime();
            int linsum   = mssLinear(arr, n, linij);
            long dt      = (System.nanoTime() - tstart1);
            double tlin  = (double)dt*1e-6;
            
            System.out.printf("%-12s time (ms) = %-12f sum = %-10d i = %-10d j = %-10d\n", "LINEAR:", tlin, linsum, linij[0], linij[1]);
            
            // can't define these in an if statement, so do it out here.
            int qdsum  = -1;
            double tqd = -1;
              
            // don't run the algorithms for big N (takes too long)
            if (n < 1e6){
                long tstart2 = System.nanoTime();
                qdsum        = mssQuadratic(arr, n, qdij);
                dt           = (System.nanoTime() - tstart2);
                tqd          = (double)dt*1e-6;
                System.out.printf("%-12s time (ms) = %-12f sum = %-10d i = %-10d j = %-10d\n", "QUADRATIC:", tqd, qdsum, qdij[0], qdij[1]);
            }
            else{
                System.out.printf("%-12s time (ms) = %-12s sum = %-10s i = %-10s j = %-10s\n", "QUADRATIC:", "NA", "NA", "NA", "NA");
            }
            
            // define these out here because java is stupid and wont let you define them in an if statement.
            int cusum  = -1;
            double tcu = -1;
            
            // another check to stop running for N >> 1
            if(n < 1e5){    
                long tstart3 = System.nanoTime();
                cusum        = mssCubic(arr, n, cuij);
                dt           = (System.nanoTime() - tstart3);
                tcu          = (double)dt*1e-6;
                System.out.printf("%-12s time (ms) = %-12f sum = %-10d i = %-10d j = %-10d\n", "CUBIC:", tcu, cusum, cuij[0], cuij[1]);
            }
            else{
                System.out.printf("%-12s time (ms) = %-12s sum = %-10s i = %-10s j = %-10s\n", "CUBIC:", "NA", "NA", "NA", "NA");
            }
            System.out.println("");
        }        
    }
       
    // populates array with n random numbers
    public static void populateArray(int[] arr)
    {
        int bound     = 32;  // generate nums between 0 and 99
        Random numGen = new Random();
        
        for(int i = 0; i < arr.length; i++){
            arr[i] = numGen.nextInt(bound) - 15;
        }
    }
    
    // linear maximum subsequence sum (mss) algorithm
    public static int mssLinear(int[] arr, int N, int[] ij)
    {
        int maxsum = 0;
        int sum    = 0;
        int i      = 0;
        for(int j = 0; j < N; j++){
            sum += arr[j];
            if(sum > maxsum){ 
                maxsum = sum;
                ij[0] = i;
                ij[1] = j;
            }
            else if(sum < 0){
                sum = 0;
                i   = j + 1;
            }
        }
        return maxsum;
    }
    
    // quadratic mss algorithm
    public static int mssQuadratic(int[] arr, int N, int[] ij)
    {
        int maxsum = 0;
        for(int i = 0; i < N; i++){
            int sum = 0;
            for(int j = i; j < N; j++){
                sum += arr[j];
                if(sum > maxsum){ 
                    maxsum = sum;
                    ij[0]  = i;
                    ij[1]  = j;
                }
            }
        }
        return maxsum;
    }
    
    // cubic mss algorithm
    public static int mssCubic(int[] arr, int N, int[] ij)
    {
        int maxsum = 0;
        int sum    = 0;
        for(int i = 0; i < N; i++){
            for(int j = i; j < N; j++){
                sum = 0;
                for(int k = i; k <= j; k++){
                    sum += arr[k];
                }
                if(sum > maxsum){
                    maxsum = sum;
                    ij[0] = i;
                    ij[1] = j;
                }
            }
        }
        return maxsum;
    }
    
    // for some reason we had to code this, but never use it? go figure.
    public static void printSequence(int[] arr, int i, int j)
    {
        for(int k = i; k <= j; k++){
            System.out.format("%d ", arr[k]);
        }
    }
}