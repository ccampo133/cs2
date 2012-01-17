// Chris Campo
// COP 3503C - 0001
// HW 1 - Array Reversal
// 1/16/2012

import java.util.*;

public class ArrayReverse 
{
    public static void main(String[] args)
    {    
        int n     = 10;           // array size
        int[] arr = new int[n];   // array to be filled w/ random numbers
        
        System.out.println("Chris Campo (ch632561): COP 3503 - Assignment 1");
        
        populateArray(arr);
        
        System.out.print("Input: \t");
        printArray(arr);

        reverseArray(arr);
        
        System.out.print("Output: \t");
        printArray(arr);
    }
    
    // populates array with n random numbers
    public static void populateArray(int[] arr)
    {
        int bound     = 100;  // generate nums between 0 and 99
        Random numGen = new Random();
        
        for(int i = 0; i < arr.length; i++){
            arr[i] = numGen.nextInt(bound);
        }
    }
    
    // reverses elements of given array
    private static void reverseArray(int[] arr)
    {
        int ibeg = 0;
        int iend = arr.length - 1;
        while(ibeg < iend){
            swap(arr, ibeg, iend);
            ibeg++;
            iend--;
        }
    }

    private static void swap(int[] arr, int ibeg, int iend)
    {
        int tmp   = arr[ibeg];
        arr[ibeg] = arr[iend];
        arr[iend] = tmp;
    }
    
    // prints elements of array
    private static void printArray(int[] arr)
    {
        System.out.println(Arrays.toString(arr));
    }   
}