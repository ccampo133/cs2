// Chris Campo
// COP 3503C - 0001
// HW 2 - Maximum Subsequence Sum
// 1/22/2012

import java.util.*;

public class Assignment02
{
    public static void main(String[] args)
    {
        int n     = 5;
        int[] ij  = new int[2];
        //int[] arr = new int[n]; 
        //populateArray(arr);
        int[] arr = {-2, 11, -4, 13, -5, -2};
        int sum = mssLinear(arr, n);
        System.out.format("%d\n", sum);
        int foo = mssQuadratic(arr, n);
        System.out.format("%d\n", foo);
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
    
    public static int mssQuadratic(int[] arr, int N)
    {
        int maxsum = 0;
        for(int i = 0; i < N; i++){
            int sum = 0;
            for(int j = i; j < N; j++){
                sum += arr[j];
                if(sum > maxsum){ maxsum = sum; }
            }
        }
        return maxsum;
    }
    
    // linear maximum subsequence sum (mss) algorithm
    public static int mssLinear(int[] arr, int N)
    {
        int maxsum = 0;
        int sum    = 0;
        for(int i = 0; i < N; i++){
            sum += arr[i];
            if(sum > maxsum){ 
                maxsum = sum;
            }
            else if(sum < 0){
                sum    = 0;
            }
        }
        return maxsum;
    }
}