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
                //patch[i].printInfo();
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
            buildIndirectHeap(pwrVals, outof, into, pwrVals.length);
            
            // start iterative solution
            int converged = 0;
            while(converged != numit){
                // find patch k with max unshot pwr in O(1)
                int k = getMaxPwrInd(outof);
                System.out.printf("k = %d\n", k);
                
                // shoot unshotPower to all adjacent patches
                for(int j = 0; j < patch[k].visIds.length; j++){
                    int i = patch[k].visIds[j]-1;
                    patch[i].totalPower  += patch[i].reflectance*
                                            patch[k].formfacts[j]*
                                            patch[k].unshotPower;
                                               
                    patch[i].unshotPower += patch[i].reflectance*
                                            patch[k].formfacts[j]*
                                            patch[k].unshotPower;
                    // restore heap property
                    increasePatchValue(pwrVals, into, outof, i, patch[i].unshotPower);
                    //System.out.println("INCREASED");
                    //System.out.println("=========");
                    //System.out.println("pwrVals: " + Arrays.toString(pwrVals));
                    //System.out.println("into: " + Arrays.toString(into));
                    //System.out.println("outof: " + Arrays.toString(outof) + "\n");
                }
                patch[k].unshotPower = 0;
                // restore heap property
                //pwrVals[k] = 0;
                //decreasePatchValue(pwrVals, into, outof, k, 0);
                //increasePatchValue(pwrVals, into, outof, k, patch[k].unshotPower);
                //System.out.println("DECREASED");
                //System.out.println("=========");
                //System.out.println("pwrVals: " + Arrays.toString(pwrVals));
                //System.out.println("into: " + Arrays.toString(into));
                //System.out.println("outof: " + Arrays.toString(outof) + "\n");

                converged++;
                for(int i = 0; i < numpatch; i++){
                    System.out.printf("ID = %d  TotPwr = %f\n", 
                                      patch[i].patchId,
                                      patch[i].totalPower);
                }
            }
        }
        catch(Exception ioe){
            System.out.println("Error: " + ioe.getMessage());
        }
        
        float[] foo = {66, 12, 312, 25, 8, 109, 7, 18};
        int[] ifoo = {1, 7, 0, 6, 4, 2, 5, 3};
        int[] ofoo = {2, 0, 5, 7, 4, 6, 3, 1};
        float bar = 10;
        
        decreasePatchValue(foo, ifoo, ofoo, 0, bar);
        System.out.println(Arrays.toString(foo));
        System.out.println(Arrays.toString(ifoo));
        System.out.println(Arrays.toString(ofoo));
        
        
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
    
    // modified siftdown to work for indirect heaps
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
    
    // returns patch index with the most power in O(1) time
    public static int getMaxPwrInd(int[] outof)
    {
        return outof[0];
    }
    
    // increases value in heap and preserves structure
    public static void increasePatchValue(float[] key, int[] into, int[] outof,
                                          int i, float newval)
    {
        key[i] = newval;
        int c  = into[i];    // child node
        int p  = c/2;        // parent node
        
        // loop until we hit the top of the heap
        while(p >= 0){
            if(key[outof[p]] >= newval){
                break;
            }
            // swap parent and child and iterate
            outof[c] = outof[p];
            into[outof[c]] = c;
            c = p;
            p = c/2;
        }
        // update final values
        outof[c] = i;
        into[i]  = c;
    }
    
    // decreases value in heap and preserves structure
    public static void decreasePatchValue(float[] key, int[] into, int[] outof,
                                          int i, float newval)
    {
        key[i] = newval;
        int p  = into[i];   // index of parent node
        int c  = 2*p + 1;   // index of child node
        
        // loop until we run off the heap
        while(c <= key.length - 1){
            if(key[outof[c]] <= newval){
                break;
            }
            // swap parent and child and iterate
            outof[p] = outof[c];
            into[outof[p]] = p;
            p = c;
            c = 2*p + 1;
        }
        // update final values
        outof[p] = i;
        into[i]  = p;
    }
}