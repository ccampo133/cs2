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

            // findme: DEBUG
            System.out.printf("NUMPATCH = %d   NUMIT = %d\n", numpatch, numit);
        
            // loop over patches and gather info
            for(int i = 0; i < numpatch; i++){
                int   patchId       = scnr.nextInt();
                float emissionPower = scnr.nextFloat();
                float reflectance   = scnr.nextFloat();
                int   numVisPatch   = scnr.nextInt();
                
                // assign variables
                patches[i] = new Patch(patchId, emissionPower, reflectance, numVisPatch);
                                
                // loop over num visible patches
                for(int j = 0; j < numVisPatch; j++){
                    int   visPatchId = scnr.nextInt();
                    float formfactor = scnr.nextFloat();
                    
                    // update patch objects
                    patches[i].visIds[j]    = visPatchId;
                    patches[i].formfacts[j] = formfactor;                    
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