import java.util.*;

public class GraphTraversal
{
    // breadth first graph traversal using adjacency lists.
    // adapted from Johnsonbaugh & Schaefer 2003
    public static void breadthFirst(AdjacencyList[] adj, int start)
    {
        boolean[]      visited = new boolean[adj.length]; // defaults to false
        Queue<Integer> q       = new LinkedList<Integer>();
        
        visited[start] = true;
        q.add(start);
        
        System.out.println(start + 1);
         
        while(!q.isEmpty())
        {
            // current vertex and adjacent nodes
            int           vertex   = q.poll();
            AdjacencyList adjnodes = adj[vertex];
            Node          listnode = adjnodes.head;
            
            // loop over adjacent nodes in list
            while(listnode != null)
            {
                int vert = listnode.vertex;
                // visit all unvisted nodes and iterate along
                if(!visited[vert])
                {
                    visited[vert] = true;
                    q.add(vert);
                    
                    // do anything here; print for now
                    System.out.println(vert + 1);
                }
                listnode = listnode.next;
            }
        }
    }

    // depth first graph traversal using adjacency lists.
    // adapted from Johnsonbaugh & Schaefer 2003
    public static void depthFirst(AdjacencyList[] adj, int start)
    {
        boolean[]   visited = new boolean[adj.length]; // defaults to false
        Stack<Node> s       = new Stack<Node>();
        
        visited[start]         = true;        
        AdjacencyList adjnodes = adj[start];
        Node          listnode = adjnodes.head;
        s.push(listnode);
        
        System.out.println(start + 1);
        
        while(!s.empty())
        {
            listnode = s.pop();
            
            // loop over adjacent nodes in list
            while(listnode != null)
            {
                int vertex = listnode.vertex;
                // visit all unvisited nodes and iterate
                if(!visited[vertex])
                {
                    s.push(listnode.next);
                    visited[vertex] = true;
                    adjnodes        = adj[vertex];
                    listnode        = adjnodes.head;
                    
                    // do anything here; print for now
                    System.out.println(vertex + 1);
                }
                else{ listnode = listnode.next; }
            }
        }
    }
    
    // calculates the and returns shortest distances and paths from vertex 
    // `start` to all other vertices on an unweighted graph.
    public static void pathUW(AdjacencyList[] adj, int start, int[] dist, int[] prev)
    {
        int            n = adj.length;
        Queue<Integer> q = new LinkedList<Integer>();
        
        // initialize length's to -1
        for(int i = 0; i < n; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        
        dist[start] = 0;
        q.add(start);
        
        while(!q.isEmpty())
        {
            int           vertex   = q.poll();
            AdjacencyList adjnodes = adj[vertex];
            Node          listnode = adjnodes.head;
            while(listnode != null)
            {
                int vert = listnode.vertex;
                if(dist[vert] == Integer.MAX_VALUE)
                {
                    dist[vert] = dist[vertex] + 1;
                    prev[vert] = vertex;
                    q.add(vert);
                }
                listnode = listnode.next;
            }
        }
    }
    
    // prints the path from vertexId `start` to vertexId `end`
    public static void printPath(int[] prev, int start, int end)
    {
        //final ArrayList<Integer> path = new ArrayList<Integer>();
        System.out.printf("The shortest path from %d to %d is: ", start, end);
        int    tmp  = end;
        String path = "";
        // loop while path is defined
        while(prev[tmp] != -1)
        {
            //path.add(0, tmp);
            path = " -> " + tmp + path;
            tmp  = prev[tmp];
        }
        //path.add(0, tmp);
        path = tmp + path;
        System.out.print(path + "\n");
    }
}