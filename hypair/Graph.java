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
    
    // returns the node named `name`.
    public Node getNode(String name)
    {
        for(Node node : nodes)
        {
            if(node.name == name){ return node; }
        }
        // node doesn't exist.
        throw new RuntimeException("No node in graph with name `" + name + "`."); 
    }
    
    // adds an edge to the graph, but referenced by vertex ids
    public void addEdge(int srcId, int destId, int weight)
    {
        Node src  = getNode(srcId);
        Node dest = getNode(destId);
        Edge edge = new Edge(src, dest, weight);
        
        // update edge list
        edges.add(edge);
        numEdges++;
        
        // add adjacent nodes
        nodes[src.id].addAdjNode(nodes[dest.id]);
    }
    
    // adds an edge to the graph
    public void addEdge(Node src, Node dest, int weight)
    {
        Edge edge = new Edge(src, dest, weight);
        
        // update edge list
        edges.add(edge);
        numEdges++;
        
        // add adjacent nodes
        nodes[src.id].addAdjNode(nodes[dest.id]);
    }
    
    // returns weight associated with the queried edge
    // weight(u, v) in the textbook.
    public int getEdgeWeight(Node src, Node dest)
    {
        for(Edge edge : edges)
        {
            if(edge.src == src && edge.dest == dest){ return edge.weight; }
        }
        // edge doesn't exist
        throw new RuntimeException("No edge between " + src.name + " and " + dest.name);
    }
    
    // returns a disjoint set data structure representing the connected components of the graph.
    public DisjointSet getConnectedComponents()
    {
        DisjointSet connComps = new DisjointSet(numNodes);
        boolean[]     visited = new boolean[numNodes];
        
        // initialize the set
        for(int i = 0; i < numNodes; i++){ connComps.makeset(getNode(i).id); }
        // find connected components
        for(int i = 0; i < numNodes; i++)
        {
            if(!visited[i]){ findConnectedNodes(visited, i, connComps); }
        }
        return connComps;
    }
    
    // keeps track of connected nodes via a disjoint set.
    // modified depth first search adapted from Johnsonbaugh & Schaefer 2003.
    private void findConnectedNodes(boolean[] visited, int startId, DisjointSet set)
    {
        visited[startId] = true;
        Node curNode     = nodes[startId];
        
        // check unvisited adjacent nodes and recurse
        for(Node adjNode : curNode.adjNodes)
        {
            int vertexId = adjNode.id;
            if(!visited[vertexId])
            {
                set.union(startId, vertexId);
                findConnectedNodes(visited, vertexId, set);
            }
        }
    }
    
    // returns the shortest path in an unweighted graph.
    // modification of breadth first search.
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
    
    // returns the shortest path in a weighted graph between two nodes.
    // modified version of Dijkstra's algorithm.
    private Map<Node, Node> getWeightedPath(Node start, Node end)
    {
        Map<Node, Node>     prev  = new HashMap<Node, Node>();
        PriorityQueue<Node> heap  = new PriorityQueue<Node>(numNodes);
        Set<Node>           visit = new HashSet<Node>();
        
        // set all distances to infinity
        for(Node node : nodes){ node.dist = Integer.MAX_VALUE; }
        
        // initialize algorithm
        Node current = start;
        current.dist = 0;
        heap.add(current);
        
        // main loop over connected nodes
        while(!heap.isEmpty())
        {
            current = heap.poll(); // get the node with minimum distance
            visit.add(current);
            // terminate if destination is reached
            if(current.equals(end)){ break; }
            // loop over adjacent nodes
            for(Node adjNode : current.adjNodes)
            {
                int dist = current.dist + getEdgeWeight(current, adjNode);
                // found shorter distance
                if(!visit.contains(adjNode) && dist < adjNode.dist)
                {
                    adjNode.dist = dist;
                    prev.put(adjNode, current);
                    heap.add(adjNode);
                }
            }
        }
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
        if(pathType == "unweighted"){ prev = getUWPath(start, end); }
        else{ prev = getWeightedPath(start, end); }
        
        // print the path and the distance
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
class Node implements Comparable<Node>
{
    LinkedList<Node> adjNodes;
    String name;
    int id;
    int dist;   // used in shortest weighted path
       
    public Node(String name, int id)
    {
        this.name = name;
        this.id   = id;
        adjNodes  = new LinkedList<Node>();
    }
    
    // add adjacent node to adjacency list
    public void addAdjNode(Node adjNode){ adjNodes.add(adjNode); }
    
    // comparison for priority queue
    public int compareTo(Node other){ return Integer.compare(dist, other.dist); }
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