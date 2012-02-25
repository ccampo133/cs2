class Node
{
    public int vertex;
    public Node next;
    public Node(int ver){ vertex = ver; }
}

public class AdjacencyList
{
    public  Node head;
    private Node tail;
    
    public AdjacencyList()
    { 
        head = null;
        tail = head;
    }
    
    public boolean isEmpty(){ return head == null; }
    
    public void add(int vertex)
    {
        Node newNode = new Node(vertex);
        // list is empty; append becomes add to front
        if(head == null)
        {
            head = newNode;
            tail = newNode;
        }
        // append to tail
        else
        {
            tail.next = newNode;
            tail      = newNode;
        }
    }
    
    public void printList()
    {
        Node temp = head;
        System.out.print("[");
        while(temp != null)
        {
            System.out.print(" ");
            System.out.print(temp.vertex);
            temp = temp.next;
        }
        System.out.print(" ]");
    }
}