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
        // read data file
        try
        {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
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
                for(int j = 0; j < 3; j++){ scnr.next(); } // ignores flight nums and times
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
                        
            // use disjoint set data structure to represent connected cities
            DisjointSet set = new DisjointSet(numCities);
            
            // initialize the set
            for(int i = 0; i < numCities; i++)
            {
                set.makeset(flightMap.getNode(i).id);
            }
            
            // find connected components
            boolean[] seen  = new boolean[numCities];
            for(int i = 0; i < numCities; i++)
            {
                if(!seen[i]){ flightMap.getConnectedNodes(seen, i, set); }
            }
            
            // check queries
            while(scnr.hasNextLine())
            {
                String city1 = scnr.next();
                String city2 = scnr.next();
                System.out.println(city1 + " " + city2);
                
                // check if there is a path in O(lg n) time
                if(set.findset(map.get(city1)) == set.findset(map.get(city2)))
                {                    
                    // get minimum hop flight
                    System.out.print("Minimum Hop Flight: ");
                    flightMap.printPath(map.get(city1), map.get(city2), "unweighted");
                    
                    // get minimum cost flight
                    System.out.print("Minimum Cost Flight: ");
                    flightMap.printPath(map.get(city1), map.get(city2), "weighted");
                }
                else{ System.out.println("No path\n"); }
            }
        }
        catch(Exception ioe){ System.out.println("Error: " + ioe.getMessage()); }
    }
}