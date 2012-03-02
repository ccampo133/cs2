// Chris Campo
// 3/1/2012
// DisjointSet.java

// disjoint set data structure.
// algortithms adopted from Johnsonbaugh & Schaefer 2003
public class DisjointSet
{
    public int[] parent;
    public int[] height;
    
    // creates a set of size `n`
    public DisjointSet(int n)
    {
        parent = new int[n];
        height = new int[n];
    }
    
    // initializes the sets
    public void makeset(int i)
    {
        parent[i] = i;
        height[i] = 0;
    }
    
    // returns the root of the tree to which `i` belongs.
    public int findset(int i)
    {
        while(i != parent[i]){ i = parent[i]; }
        return i;
    }
    
    // constructs a tree that represents a union of the sets of which `i` 
    // and `j` belong.
    public void union(int i, int j)
    {
        mergeTrees(findset(i), findset(j));
    }
 
    // merge two trees rooted at `i` and `j` by height.
    private void mergeTrees(int i, int j)
    {
        if(height[i] < height[j]){ parent[i] = j; }
        else if(height[i] > height[j]){ parent[j] = i; }
        else
        {
            parent[i]  = j;
            height[j] += 1;
        }
    }
}