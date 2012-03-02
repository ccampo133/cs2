// Chris Campo
// 3/1/2012
// Graph.java

import java.util.*;

public class Graph
{
    //public HashMap map;
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
    // weight(u, v) in the textbook
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

    // prints a representation of the graph
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
    
    public void breadthFirstSearch(int startId)
    {
        boolean[] visited = new boolean[nodes.length];
        Queue<Node> q     = new LinkedList<Node>();
        Node startNode    = nodes[startId];
        visited[startId]  = true;
        q.add(startNode);
        System.out.println(startNode.name);
        
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
    
    // print names of the adjacent nodes
    public void printAdjNodes()
    {
        for(int i = 0; i < adjNodes.size(); i++)
        {
            Node curNode = adjNodes.get(i);
            System.out.print(curNode.name + " ");
        }
        System.out.print("\n\n");
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