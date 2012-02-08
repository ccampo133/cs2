import java.util.*;

public class Patch{

    // intrinsic variables
    public int   patchId;
    public float emissionPower;
    public float reflectance;
    public int   numVisPatch;
    public float unshotPower;
    public float totalPower;
    public int[] visIds;
    public float[] formfacts;
    
    public Patch(int id, float epwr, float reflect, int nvispatch){
        patchId       = id;
        emissionPower = epwr;
        reflectance   = reflect;
        numVisPatch   = nvispatch;
        
        // array to hold adajcent patch IDs and formfactors
        visIds    = new int[numVisPatch];
        formfacts = new float[numVisPatch];
    }
    
    public void printInfo(){
        System.out.printf("patchId = %d\n", patchId);
        System.out.printf("emissionPower = %f\n", emissionPower);
        System.out.printf("reflectance = %f\n", reflectance);
        System.out.printf("numVisPatch = %d\n", numVisPatch);
        System.out.printf("visIds = " + Arrays.toString(visIds) + "\n");
        System.out.printf("formfacts = " + Arrays.toString(formfacts) + "\n\n");
    }
}