# main build indirect heap method. adjusted to support arrays
# that are indexed at 0 instead of 1, like those in Java.
def buildIndirectHeap(key, outof, into, n):
    start = (n - 2)/2   # start at the last parent node
    while(start >= 0):
        siftdown(key, outof, into, start, n-1)
        start -= 1

        
# sift a node down a binary tree while preserving a heap 
# structure in O(lg n) time.  modified to work with indirect 
# max heaps.
def siftdown(key, outof, into, root, n):
    while(2*root + 1 <= n):
        child = 2*root + 1
        
        # if there is a right child and it is bigger than the left, move `child`
        if(child + 1 <= n and key[outof[child + 1] > key[outof[child]]):
            child = child + 1
        
        # check if child is greater than parent. if true,
        # swap them and sift the parent down.
        if(key[outof[child]] > key[outof[root]]):
            swap(outof, root, child)
            swap(into, outof[root], outof[child])
            root = child # update current node to continue loop
        else:
            break   # no need to swap anything; exit loop

            
# replace a value in the heap with a greater value.
# runs in O(lg n) time.
def increasePatchValue(key, into, outof, root, newval):
    key[i] = newval
    c = into[root] # child node 
    p = c/2        # parent node
    # traverse the remainer of the tree
    while(p >= 0):
        if(key[outof[p]] >= newval):
            break   # no need to swap so break loop.
        
        # swap p and c and sift up
        outof[c]       = outof[p]
        into[outof[c]] = c
        
        # jump up to parent and continue iterating
        c = p
        p = c/2
    
    # update position of `newval` in the heap
    outof[c]   = root
    into[root] = c


# replace a value in the heap with a lower value.
# runs in O(lg n) time.
def decreasePatchValue(key, outof, into, i, n, newval):
    key[i] = newval
    start  = into[i]    # node index that key[i] is located at
    siftdown(key, outof, into, start, n-1)
    
    
# swap two values in an array.            
def swap(A, i, j):
    temp = A[i]
    A[i] = A[j]
    A[j] = temp
    
    
# get index of `key` that contains the max value
# of the heap. runs in O(1) time
def getMaxPwrInd(outof):
    return outof[0]