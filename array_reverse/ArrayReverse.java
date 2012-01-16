// Chris Campo
// HW 1 - Array Reversal
// 1/16/2012

import java.util.*;

public class ArrayReverse {

    public static void main(String[] args)
    {
        int n         = 10;           // array size
        int[] randarr = new int[n];   // array to be filled w/ random numbers
        
        populateArray(randarr);
        
        System.out.println(Arrays.toString(randarr));
        
        reverseArray(randarr);
        
        System.out.println(Arrays.toString(randarr));

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
        // make a copy to hold the original values
        int[] temparr = new int[arr.length];
        System.arraycopy(arr, 0, temparr, 0, arr.length);
        
        // loops from back to front to reverse the array
        for(int i = arr.length; i > 0; i--){
            arr[arr.length-i] = temparr[i-1];
        }
    }
    
    // prints elements of array
    private static void printArray()
    {
    }
}