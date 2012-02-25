// Chris Campo
// COP 3503C - 0001
// HW 4 - Airline Reservation System
// 2/23/2012

import java.util.*;
import java.io.*;

public class Assignment04
{
    public static void main(String[] args)
    {
        HashMap map = new HashMap();
        System.out.print(map.get("orlando"));
        // read data file
        try
        {
            String  fname  = "Assignment04.txt";
            Scanner scnr   = new Scanner(new File(fname));
            int numcities  = scnr.nextInt();
            int numflights = scnr.nextInt();
            
            // gather flight information
            for(int i = 0; i < numflights; i++)
            {
                System.out.print(scnr.next() + " ");
                System.out.print(scnr.next() + " ");
                System.out.print(scnr.nextInt() + " ");
                System.out.print(scnr.nextInt() + " ");
                System.out.println(scnr.nextInt() + " ");
            }
        }
        catch(Exception ioe)
        {
            System.out.println("Error: " + ioe.getMessage());
        }
    }
}