// Chris Campo
// 3/1/2012
// Graph.java

import java.util.*;

// weighted directed graph data structure consiting of nodes and edges.
public class Graph
{
    Node[] nodes;
    LinkedList<Edge> edges;
    int numEdges;
    int numNodes;
    
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
    
    // returns the node located at vertex `id`.
    public Node getNode(int id){ return nodes[id]; }
    
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
    public int getEdgeWeight(Node src, Node dest)
    {
        for(Edge edge : edges)
        {
            if(edge.src == src && edge.dest == dest)
            { 
                return edge.weight;
            }
        }
        // edge doesn't exist
        throw new RuntimeException("No edge between " + src.name + " and " + dest.name);
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
    private Map<Node, Node> getUWPath(Node start, Node end)
    {
        Map<Node, Node>  prev  = new HashMap<Node, Node>();
        Queue<Node>      queue = new LinkedList<Node>();
        Set<Node>        visit = new HashSet<Node>(); // keep track of visited nodes
        
        // initialize the search
        Node current = start;
        queue.add(current);
        visit.add(current);
        
        // loop over connected nodes
        while(!queue.isEmpty())
        {
            current = queue.poll();
            // terminate if destination is reached
            if(current.equals(end)){ break; }
            // loop over adjacent nodes
            for(Node adjNode : current.adjNodes)
            {
                if(!visit.contains(adjNode))
                {
                    queue.add(adjNode);
                    visit.add(adjNode);
                    prev.put(adjNode, current);
                }
            }
        }
        return prev;
    }
    
    // returns the shortest path in a weighted graph.
    private Map<Node, Node> getWeightedPath(Node start, Node end)
    {
        Map<Node, Node> prev = new HashMap<Node, Node>();
        System.out.println("WEIGHTED PATH TODO");
        return prev;
    }
    
    // prints the path between two specified node (vertex) ids.
    // can specifiy weighted or unweighted path
    public void printPath(int startId, int endId, String pathType)
    {
        // get the path and distance
        Node start = getNode(startId);
        Node end   = getNode(endId);
        int  dist  = 0;
        
        // get path type
        Map<Node, Node> prev;
        if(pathType == "unweighted")
        {
            prev = getUWPath(start, end);
        }
        else if(pathType == "weighted")
        {
            prev = getWeightedPath(start, end);
            // FINDME: update code
            System.out.println();
            return;
        }
        else
        {
            throw new RuntimeException("Invaild path type.  Either weighted or unweighted");
        }
        
        // print the path
        String path  = "";
        Node   tmp   = end;
        while(prev.get(tmp) != prev.get(start))
        {
            dist += getEdgeWeight(prev.get(tmp), tmp);
            path  = " to " + tmp.name + path;
            tmp   = prev.get(tmp);
        }
        path = tmp.name + path;
        System.out.printf(path + " %d\n", dist);
    }
}

// class for a node (vertex) of the graph
class Node
{
    String name;
    int id;
    LinkedList<Node> adjNodes;
       
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