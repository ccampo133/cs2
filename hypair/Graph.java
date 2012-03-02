// Chris Campo
// 3/1/2012
// Graph.java

import java.util.*;

// weighted directed graph data structure consiting of nodes and edges.
public class Graph
{
    public Node[] nodes;
    public LinkedList<Edge> edges;
    public int numEdges;
    public int numNodes;
    
    // create empty graph
    public Graph(int n)
    {
        numNodes = n;
        numEdges = 0;
        nodes    = new Node[numNodes];
        edges    = new LinkedList<Edge>();
    }
    
    // adds a new node to the graph
    public void addNode(String name, int id){ nodes[id] = new Node(name, id); }
    
    // adds an edge to the graph
    public void addEdge(String src, String dest, int weight, HashMap<String, Integer> map)
    {
        int  srcId  = map.get(src);
        int  destId = map.get(dest);        
        Edge edge   = new Edge(nodes[srcId], nodes[destId], weight);
        
        // update edge list
        edges.add(edge);
        numEdges++;
        
        // add adjacent nodes
        nodes[srcId].addAdjNode(nodes[destId]);
    }
    
    // returns weight associated with the queried edge
    // weight(u, v) in the textbook.
    public int getEdgeWeight(int idSrc, int idDest)
    {
        for(Edge edge : edges)
        {
            if(edge.src.id == idSrc && edge.dest.id == idDest)
            { 
                return edge.weight;
            }
        }
        return -1;  // edge doesn't exist
    }
    
    // returns the node located at vertex `id`.
    public Node getNode(int id)
    {
        return nodes[id];
    }

    // prints a representation of the graph.
    public void printGraph()
    {
        for(int i = 0; i < numNodes; i++)
        {
            Node curNode = nodes[i];
            System.out.print(curNode.name + ": adjacent to...\n");
            for(int j = 0; j < curNode.adjNodes.size(); j++)
            {
                System.out.print(curNode.adjNodes.get(j).name + "  ");
            }
            System.out.println("\n");
        }
    }
    
    // breadth first traversal adapted from Johnsonbaugh & Schaefer 2003.
    public void breadthFirst(int startId)
    {
        boolean[] visited = new boolean[nodes.length];
        Queue<Node> q     = new LinkedList<Node>();
        Node startNode    = nodes[startId];
        visited[startId]  = true;
        q.add(startNode);
        System.out.println(startNode.name);
        
        // loop through all connected nodes
        while(!q.isEmpty())
        {
            Node curNode = q.poll();
            for(Node adjNode : curNode.adjNodes)
            {
                int vertexId = adjNode.id;
                if(!visited[vertexId])
                {
                    visited[vertexId] = true;
                    q.add(adjNode);
                    System.out.println(adjNode.name);
                }
            }
        }
    }
  
    // keeps track of connected nodes via a disjoint set.
    // modded depth first traversal adapted from Johnsonbaugh & Schaefer 2003.
    public void getConnectedNodes(boolean[] visited, int startId, DisjointSet set)
    {
        visited[startId] = true;
        Node curNode     = nodes[startId];
        
        for(Node adjNode : curNode.adjNodes)
        {
            int vertexId = adjNode.id;
            if(!visited[vertexId])
            {
                set.union(startId, vertexId);
                getConnectedNodes(visited, vertexId, set);
            }
        }
    }
    
    // returns the shortest path in an unweighted graph
    // modification of breadth first traversal
    public void unweightedPath(int[] dist, int[] prev, int startId)
    {
        int         n = nodes.length;
        Queue<Node> q = new LinkedList<Node>();
        
        // initialize length's and previous
        for(int i = 0; i < n; i++)
        {
            dist[i] = Integer.MAX_VALUE;    // infinity
            prev[i] = -1;
        }
        
        Node startNode = nodes[startId];
        dist[startId]  = 0;
        q.add(startNode);
        
        // loop over connected nodes
        while(!q.isEmpty())
        {
            Node curNode = q.poll();
            for(Node adjNode : curNode.adjNodes)
            {
                int vertex = adjNode.id;
                if(dist[vertex] == Integer.MAX_VALUE)
                {
                    dist[vertex] = dist[curNode.id] + getEdgeWeight(curNode.id, vertex);
                    prev[vertex] = curNode.id;
                    q.add(adjNode);
                }
            }
        }
    }
    
    // prints the path through a graph and the associated cost
    public void printPath(int[] dist, int[] prev, int endId)
    {
        int cost    = 0;
        int tmp     = endId;
        String path = "";
        
        while(prev[tmp] != -1)
        {
            path = " to " + getNode(tmp).name + path;
            tmp  = prev[tmp];
        }
        
        path = getNode(tmp).name + path;
        cost = dist[endId];
        System.out.printf(path + " %d\n", cost); 
    } 
}

// class for a node (vertex) of the graph
class Node
{
    public String name;
    int id;
    public LinkedList<Node> adjNodes;
       
    public Node(String name, int id)
    {
        this.name = name;
        this.id   = id;
        adjNodes  = new LinkedList<Node>();
    }
    
    // add adjacent node to adjacency list
    public void addAdjNode(Node adjNode)
    {
        adjNodes.add(adjNode);
    }
}
    
// class for an edge on the graph
class Edge
{
    Node src;
    Node dest;
    int weight;
    
    public Edge(Node src, Node dest, int weight)
    {
        this.src    = src;
        this.dest   = dest;
        this.weight = weight;
    }
}