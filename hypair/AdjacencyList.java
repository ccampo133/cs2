// Chris Campo
// 02/25/2012
// AdjacencyList.java

// Basic implementation of a singly linked-list in Java;
// Only accepts integers at the moment, but could be easily adapted take generics.

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
    
    // initial list is just a null list.
    public AdjacencyList()
    { 
        head = null;
        tail = head;
    }
    
    // returns true if list has zero elements.
    public boolean isEmpty(){ return head == null; }
    
    // appends an element to the end of the list
    public void add(int vertex)
    {
        Node newNode = new Node(vertex);
        // list is empty; append is just add to front
        if(head == null)
        {
            head = newNode;
            tail = newNode;
        }
        // append to tail of list
        else
        {
            tail.next = newNode;
            tail      = newNode;
        }
    }
    
    // for debugging purposes; prints the contents of the list
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