// Chris Campo
// COP 3503C - 0001
// HW 3 - Binary Max Heap
// 2/08/2012

import java.util.*;
import java.io.*;

public class Assignment03
{
    public static void main(String[] args)
    {
        // initialize all data 
        try{
            // start reading the input txt file
            String  fname = "Assignment03.txt";
            Scanner scnr  = new Scanner(new File(fname));
            int numpatch  = scnr.nextInt();
            int numit     = scnr.nextInt();
            
            // create array of patches
            Patch[] patch = new Patch[numpatch];
            
            // loop over patches and gather info
            for(int i = 0; i < numpatch; i++){
                // assign variables
                patch[i] = new Patch(scnr.nextInt(),      // patch id
                                     scnr.nextFloat(),    // emission pwr
                                     scnr.nextFloat(),    // reflectance
                                     scnr.nextInt()       // num vis patch
                                     );
                                       
                // loop over num visible patches
                for(int j = 0; j < patch[i].numVisPatch; j++){
                    // update patch objects
                    patch[i].visIds[j]    = scnr.nextInt();
                    patch[i].formfacts[j] = scnr.nextFloat();
                }
                // findme: DEBUG
                patch[i].printInfo();
            }
            
            // start main shooting algorithm
            float[] pwrVals = new float[numpatch];
            for(int i = 0; i < numpatch; i++){
                patch[i].unshotPower = patch[i].emissionPower;
                patch[i].totalPower  = patch[i].unshotPower;
                pwrVals[i]           = patch[i].unshotPower;
            }
            
            // initialize indirect heap helper arrays
            int[] outof = new int[pwrVals.length];
            int[] into  = new int[pwrVals.length];
            for(int i = 0; i < outof.length; i++){
                outof[i] = i;
                into[i]  = i;
            }
           
            // initialize indirect heap
            buildIndirectHeap(pwrVals, outof, into, pwrVals2.length);
            
            // start iterative solution
            int converged = 0;
            while(converged != numit){
            }
        }
        catch(Exception ioe){
            System.out.println("Error: " + ioe.getMessage());
        }
    }
    
    // ######################
    // # BEGIN HEAP METHODS #
    // ######################
 
    // standard heapify method
    public static void buildIndirectHeap(float[] key, int[] outof, int[] into, int n)
    {
        int start = (n - 2)/2;
        while(start >= 0){
            siftdown(key, outof, into, start, n - 1);
            start--;
        }
    }
  
    public static void siftdown(float[] key, int[] outof, int[] into, int i, int n)
    {
        int temp = outof[i];
        
        // use 2*i + 1 since array starts at 0, not 1
        while(2*i + 1 <= n){
            int child = 2*i + 1;
            if(child+1 <= n && key[outof[child+1]] > key[outof[child]]){
                child = child + 1;
            }
            // move child up?
            if(key[outof[child]] > key[outof[i]]){
                outof[i]       = outof[child];
                into[outof[i]] = i;
            }
            else{break;}
            i = child;
        }
        outof[i]   = temp;
        into[temp] = i;
    }
}