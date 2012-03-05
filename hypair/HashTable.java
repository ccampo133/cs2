// Chris Campo
// 03/05/2012
// HashTable.java

public class HashTable
{
    int size;
    Entry[] bucket;
    
    public HashTable(int n)
    {
        size   = 5*n; // big size = fewer collisions = faster
        bucket = new Entry[size];
    }
    
    // add an entry to the table
    public void put(String key, int val)
    {
        int hash = hashFunc(key);
        // bucket is filled; linearly probe
        while(bucket[hash] != null)
        {
            // this entry already exists; update it
            if(bucket[hash].key.equals(key))
            {
                bucket[hash].val = val;
                return;
            }
            hash = (hash + 1) % size; 
        }
        bucket[hash] = new Entry(key, val);
    }
    
    // retrieve an entry from the table
    public int get(String key)
    {
        int hash = hashFunc(key);
        if(bucket[hash] == null){ return -1; } // hasn't been mapped yet
        else
        {
            // probe until the right entry is found
            while(!bucket[hash].key.equals(key))
            {
                hash = (hash + 1) % size;
                if(bucket[hash] == null){ return -1; } // doesn't exist 
            }
            return bucket[hash].val;
        }
    }
    
    // hash function
    private int hashFunc(String key) 
    { 
        int hashVal = 0;
        for(int i = 0; i < key.length(); i++)
        {
            hashVal = 37 * hashVal + key.charAt(i);
        }
        
        hashVal %= size;
        if(hashVal < 0){ hashVal += size; }
        return hashVal;
    }
}

// entries into the hash table
class Entry
{
    String key;
    int    val;
    
    public Entry(String key, int val)
    {
        this.key = key;
        this.val = val;
    }
}