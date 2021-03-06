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
        try
        {
            String    fname = "Assignment04.txt";
            Scanner   scnr  = new Scanner(new File(fname));
            
            // read initial data from file
            int numCities  = scnr.nextInt();
            int numFlights = scnr.nextInt();
            
            // init graph and hash map
            Graph     flightMap = new Graph(numCities);
            HashTable map       = new HashTable(numCities);
            
            // read input and gather flight information
            int idSoFar = 0;
            for(int i = 0; i < numFlights; i++)
            {
                String city1 = scnr.next();
                String city2 = scnr.next();
                for(int j = 0; j < 3; j++){ scnr.next(); } // ignores flight nums and times
                int cost = scnr.nextInt();
                
                // assign vertices
                if(map.get(city1) == -1)
                {
                    map.put(city1, idSoFar);
                    flightMap.addNode(city1, idSoFar);
                    idSoFar++;
                }
                if(map.get(city2) == -1)
                {
                    map.put(city2, idSoFar);
                    flightMap.addNode(city2, idSoFar);
                    idSoFar++;
                }
            
                // create respective edge
                flightMap.addEdge(map.get(city1), map.get(city2), cost);
            }
            
            // use disjoint set data structure to represent connected cities
            DisjointSet regions = flightMap.getConnectedComponents();
            
            // check queries
            while(scnr.hasNextLine() && scnr.hasNext())
            {
                String city1 = scnr.next();
                String city2 = scnr.next();
                System.out.println("QUERY: " + city1 + " to " + city2);
                
                // check if there is a path between cities
                if(regions.findset(map.get(city1)) == regions.findset(map.get(city2)))
                {                    
                    // get minimum hop flight
                    System.out.print("Minimum Hop Flight: ");
                    flightMap.printPath(map.get(city1), map.get(city2), "unweighted");
                    
                    // get minimum cost flight
                    System.out.print("Minimum Cost Flight: ");
                    flightMap.printPath(map.get(city1), map.get(city2), "weighted");
                    System.out.println("");
                }
                else{ System.out.println("No path\n"); }
            }
        }
        catch(Exception ioe){ System.out.println("Error: " + ioe.getMessage()); }
    }
}