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
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        // read data file
        try
        {
            String  fname  = "Assignment04.txt";
            Scanner scnr   = new Scanner(new File(fname));
            int numCities  = scnr.nextInt();
            int numFlights = scnr.nextInt();
            
            // init graph
            Graph flightMap = new Graph(numCities);
            
            // gather flight information
            int idSoFar = 0;
            for(int i = 0; i < numFlights; i++)
            {
                String city1 = scnr.next();
                String city2 = scnr.next();
                for(int j = 0; j < 3; j++){ scnr.next(); } // scan and ignore the other vars
                int cost = scnr.nextInt();
                
                // assign vertices
                if(map.get(city1) == null)
                {
                    map.put(city1, idSoFar);
                    flightMap.addNode(city1, idSoFar);
                    idSoFar++;
                }
                if(map.get(city2) == null)
                {
                    map.put(city2, idSoFar);
                    flightMap.addNode(city2, idSoFar);
                    idSoFar++;
                }
            
                // create respective edge
                flightMap.addEdge(city1, city2, cost, map);
            }
            
            int[] path = new int[numCities];
            int[] cost = new int[numCities];
        }
        catch(Exception ioe)
        {
            System.out.println("Error: " + ioe.getMessage());
        }
    }
}