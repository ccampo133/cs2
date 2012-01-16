// Chris Campo
// HW 1 - Array Reversal
// 1/16/2012

import java.util.*;

public class ArrayReverse {

    public static void main(String[] args)
    {
        int n         = 10;           // array size
        int[] randarr = new int[n];   // array to be filled w/ random numbers
        
        populateArray(randarr, n);
        
        System.out.println(Arrays.toString(randarr));
    }
    
    // populates array with n random numbers
    public static void populateArray(int[] arr, int n)
    {
        int bound     = 100;  // generate nums between 0 and 99
        Random numGen = new Random();
        
        for(int i = 0; i < n; i++){
            arr[i] = numGen.nextInt(bound);
        }
        
        //return arr;
    }
    
    // reverses elements of given array
    private void reverseArray(){
    }
    
    // prints elements of array
    private void printArray(){
    }
}