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
                vertex = listnode.vertex;
                // visit all unvisted nodes and iterate along
                if(!visited[vertex])
                {
                    visited[vertex] = true;
                    q.add(vertex);
                    
                    // do anything here; print for now
                    System.out.println(vertex + 1);
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
}