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
        try{
            // start reading the input txt file
            String  fname = "Assignment03.txt";
            Scanner scnr  = new Scanner(new File(fname));
            int numpatch  = scnr.nextInt();
            int numit     = scnr.nextInt();
            
            // create array of patches
            Patch[] patches = new Patch[numpatch];
            
            // loop over patches and gather info
            for(int i = 0; i < numpatch; i++){
                // assign variables
                patches[i] = new Patch(scnr.nextInt(),      // patch id
                                       scnr.nextFloat(),    // emission pwr
                                       scnr.nextFloat(),    // reflectance
                                       scnr.nextInt()       // num vis patch
                                       );
                                       
                // loop over num visible patches
                for(int j = 0; j < patches[i].numVisPatch; j++){
                    // update patch objects
                    patches[i].visIds[j]    = scnr.nextInt();
                    patches[i].formfacts[j] = scnr.nextFloat();
                }
                // findme: DEBUG
                patches[i].printInfo();
            }
        }
        catch(Exception ioe){
            System.out.println("Error: " + ioe.getMessage());
        }
    }
}