// Chris Campo
// COP 3503C - 0001
// HW 2 - Maximum Subsequence Sum
// 1/22/2012

import java.util.*;

public class Assignment02
{
    public static void main(String[] args)
    {
        int n     = 7;
        int[] ij  = new int[2];
        int[] jk  = new int[2];
        int[] lm  = new int[2];
        int[] arr = new int[n]; 
        //int[] arr = {11, 14, -3, 0, 10};
        populateArray(arr);
        System.out.println(Arrays.toString(arr));
        int cat = mssLinear(arr, n, lm);
        System.out.format("%d i = %d  j = %d\n", cat, lm[0], lm[1]);
        int foo = mssQuadratic(arr, n, jk);
        System.out.format("%d i = %d  j = %d\n", foo, jk[0], jk[1]);
        int bar = mssCubic(arr, n, ij);
        System.out.format("%d i = %d  j = %d\n", bar, ij[0], ij[1]);
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
        int j      = 0;
        for(int i = 0; i < N; i++){
            sum += arr[i];
            if(sum > maxsum){ 
                maxsum = sum;
                j += i;
                ij[1] = i;
                ij[0] = j - i;
            }
            else if(sum < 0){
                sum = 0;
                j   = 0;
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
    
    // helper method for mssCubic
    public static int subseqSum(int[] arr, int i, int j)
    {
        int sum = 0;
        for(int k = i; k <= j; k++){
            sum += arr[k];
        }
        return sum;
    }
}